import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import  java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NadraDB
{
    public String CNIC;
    public String issued_date;
    public String expiry_date;
    public static String filename;

    public NadraDB(String filename1)
    {
        this.filename=filename1;
    }
    public  NadraDB(){}
    public NadraDB(String CNIC1,String iss_date,String exp_date)
    {
        this.CNIC=CNIC1;
        this.issued_date=iss_date;
        this.expiry_date=exp_date;
    }

    public static List<NadraDB> Load_NBData() {
        List<NadraDB> NadraDB = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;

            // Read file line by line
            while ((line = reader.readLine()) != null) {
                // Split the line by comma
                String[] parts = line.split(",");

                if (parts.length == 3)
                {
                    String CNIC = parts[0];
                    String iss_date = parts[1];
                    String exp_date=parts[2];
                    // Handle blank value for peak hour unit price (assign null if it's blank)


                    // Create a MeterPricingInfo object and add to list
                    NadraDB ndb = new NadraDB(CNIC,iss_date, exp_date);
                    NadraDB.add(ndb);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NadraDB;
    }


    public  void displayInfo()
    {
        System.out.println("CNIC: "+CNIC);
        System.out.print("Issued Date: "+issued_date+" ");
        System.out.print("Expiry Date: "+expiry_date);
        System.out.println();
    }


    public static void Check_expiry(List<NadraDB> nadraList, List<Customer> customers) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate currentDate = LocalDate.now(); // Get the current date

        for (Customer customer : customers) {
            String customerCNIC = String.valueOf(customer.CNIC); // CNIC from customer

            for (NadraDB ndb : nadraList) {
                String nadraCNIC = ndb.CNIC; // CNIC from NADRA database

                // If CNIC matches
                if (customerCNIC.equals(nadraCNIC)) {
                    // Parse the expiry date from the NADRA database
                    LocalDate expiryDate = LocalDate.parse(ndb.expiry_date, formatter);

                    // Calculate the number of days between the current date and the expiry date
                    long daysUntilExpiry = ChronoUnit.DAYS.between(currentDate, expiryDate);

                    // Check if the expiry is within the next 30 days
                    if (daysUntilExpiry > 0 && daysUntilExpiry <= 30) {
                        System.out.println("Customer ID: " + customer.cus_id + " has a CNIC expiring in " + daysUntilExpiry + " days.");
                    }
                }
            }
        }
    }

    public void update_expiry_date(List<NadraDB> nadraList, List<Customer> customers) {
        Scanner scanner = new Scanner(System.in);

        // Get input from the user
        System.out.print("Enter your CNIC number: ");
        String inputCNIC = scanner.nextLine();

        System.out.print("Enter your Customer number: ");
        String inputCustomerID = scanner.nextLine();
       // scanner.nextLine();  // Consume the newline

        boolean foundCustomer = false;
        boolean foundCNIC = false;

        // Find the customer with matching CNIC and customer number
        for (Customer customer : customers) {
            if (customer.CNIC.equals(inputCNIC) && customer.cus_id.equals( inputCustomerID)){
                foundCustomer = true;
                // Now search for this CNIC in the Nadra database
                for (NadraDB ndb : nadraList) {
                    if (ndb.CNIC.equals(inputCNIC)) {
                        foundCNIC = true;

                        // Prompt the user to enter a new expiry date
                        System.out.print("Enter the new expiry date (dd/MM/yyyy): ");
                        String newExpiryDate = scanner.nextLine();

                        // Validate date format
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        try {
                            LocalDate parsedDate = LocalDate.parse(newExpiryDate, formatter);

                            // Update the expiry date in the NadraDB object
                            ndb.expiry_date = newExpiryDate;
                            System.out.println("Expiry date updated successfully for CNIC: " + inputCNIC);


                            // Save the updated data back to the file
                            saveUpdatedDataToFile(nadraList);
                        } catch (Exception e) {
                            System.out.println("Invalid date format. Please enter the date in dd/MM/yyyy format.");
                        }

                        break;
                    }
                }
                break;
            }
        }

        if (!foundCustomer) {
            System.out.println("Customer number or CNIC not found.");
        } else if (!foundCNIC) {
            System.out.println("CNIC not found in Nadra database.");
        }
    }

    // Method to save the updated data back to the file
    public void saveUpdatedDataToFile(List<NadraDB> nadraList) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            // Write each entry back to the file
            for (NadraDB ndb : nadraList) {
                writer.write(ndb.CNIC + "," + ndb.issued_date + "," + ndb.expiry_date);
                writer.newLine();
            }

            writer.close();
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data to file.");
            e.printStackTrace();
        }
    }
}











