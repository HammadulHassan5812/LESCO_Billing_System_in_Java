import java.util.Scanner;
import java.io.IOException;
import java.io.*;
import java.util.*;
import  java.io.BufferedWriter;
import  java.io.BufferedReader;

public class Customer
{
   public String cus_id;
   public String CNIC;
   public   String Name;
   public String Phone_no;
   public String Address;
   public Character Cus_type;
   public String Meter_type;
   public String connec_date;
   public int reg_hour_units;
   public int peak_hour_units;
   static String filename;
     public  Customer(String filename1)
     {
         this.filename=filename1;
     }

     public  Customer(){}

     public Customer(String cus_id, String CNIC, String Name, String Address, String Phone_no, char Cus_type, String Meter_type, String connec_date, int reg_hour_units, int peak_hour_units)
     {
      this.cus_id = cus_id;
      this.CNIC = CNIC;
      this.Name = Name;
      this.Address = Address;
      this.Phone_no = Phone_no;
      this.Cus_type = Cus_type;
      this.Meter_type = Meter_type;
      this.connec_date = connec_date;
      this.reg_hour_units = reg_hour_units;
      this.peak_hour_units = peak_hour_units;
     }


 public void displayCustomerInfo() {
  System.out.println("Customer ID: " + cus_id);
  System.out.println("CNIC: " + CNIC);
  System.out.println("Name: " + Name);
  System.out.println("Address: " + Address);
  System.out.println("Phone No: " + Phone_no);
  System.out.println("Customer Type: " + Cus_type);
  System.out.println("Meter Type: " + Meter_type);
  System.out.println("Connection Date: " + connec_date);
  System.out.println("Regular Hour Units: " + reg_hour_units);
  System.out.println("Peak Hour Units: " + peak_hour_units);
  System.out.println();
 }


 public static List<Customer> loadCustomerData() {
  List<Customer> customerList = new ArrayList<>();
  try {
   BufferedReader reader = new BufferedReader(new FileReader(filename));
   String line;

   // Read file line by line
   while ((line = reader.readLine()) != null) {
    // Split line into parts by comma
    String[] parts = line.split(",");
    if (parts.length == 10) {
     String cus_id = parts[0];
     String CNIC = parts[1];
     String Name = parts[2];
     String Address = parts[3];
     String Phone_no = parts[4];
     char Cus_type = parts[5].charAt(0);  // Assuming D or C for Domestic or Commercial
     String Meter_type = parts[6];
     String connec_date = parts[7];
     int reg_hour_units = Integer.parseInt(parts[8]);
     int peak_hour_units = Integer.parseInt(parts[9]);

     // Create new Customer object and add to list
     Customer customer = new Customer(cus_id, CNIC, Name, Address, Phone_no, Cus_type, Meter_type, connec_date, reg_hour_units, peak_hour_units);
     customerList.add(customer);
    }
   }
   reader.close();
  } catch (IOException e) {
   e.printStackTrace();
  }
  return customerList;
 }

    public void view_curr_bill(List<BillingInfo> billingInfos, List<Tax_tariff_info> pricingInfos) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Collect input from the user
        System.out.print("Enter your 4-digit Customer ID: ");
        String customerId = scanner.nextLine();

        System.out.print("Enter your CNIC: ");
        String enteredCNIC = scanner.nextLine();

        System.out.print("Enter your Meter Type (1Phase/3Phase): ");
        String enteredMeterType = scanner.nextLine();

        System.out.print("Enter your current regular meter reading: ");
        int currRegMeterReading = scanner.nextInt();

        int currPeakMeterReading = 0;
        if (enteredMeterType.equalsIgnoreCase("3Phase")) {
            System.out.print("Enter your current peak meter reading: ");
            currPeakMeterReading = scanner.nextInt();
        }

        // Step 2: Find matching customer
        Customer matchedCustomer = null;
        for (Customer customer : loadCustomerData()) {
            if (customer.cus_id.equals(customerId) && customer.CNIC.equals(enteredCNIC)
                    && customer.Meter_type.equalsIgnoreCase(enteredMeterType)) {
                matchedCustomer = customer;
                break;
            }
        }

        if (matchedCustomer == null) {
            System.out.println("Invalid Customer ID, CNIC, or Meter Type.");
            return;
        }

        // Step 3: Find the tariff for the entered meter type
        Tax_tariff_info matchedTariff = null;
        for (Tax_tariff_info tariff : pricingInfos) {
            if (tariff.meterType.equalsIgnoreCase(enteredMeterType)) {
                matchedTariff = tariff;
                break;
            }
        }

        if (matchedTariff == null) {
            System.out.println("No tariff information found for your meter type.");
            return;
        }

        // Step 4: Find the billing information for the matched customer
        BillingInfo matchedBillingInfo = null;
        for (BillingInfo billingInfo : billingInfos) {
            if (billingInfo.cus_id.equals(customerId)) {
                matchedBillingInfo = billingInfo;
                break;
            }
        }

        if (matchedBillingInfo == null) {
            System.out.println("No billing information found for your customer ID.");
            return;
        }

        // Step 5: Calculate electricity cost
        int regularUnitsConsumed = currRegMeterReading - matchedCustomer.reg_hour_units;
        int peakUnitsConsumed = (enteredMeterType.equalsIgnoreCase("3Phase"))
                ? (currPeakMeterReading - matchedCustomer.peak_hour_units)
                : 0;

        int regCost = regularUnitsConsumed * matchedTariff.regularUnitPrice;
        int peakCost = peakUnitsConsumed * (matchedTariff.peakHourUnitPrice != null ? matchedTariff.peakHourUnitPrice : 0);
        int totalElectricityCost = regCost + peakCost;

        // Step 6: Calculate total cost with tax and fixed charges
        int taxAmount = (totalElectricityCost * matchedTariff.taxPercentage) / 100;
        int totalAmountDue = totalElectricityCost + taxAmount + matchedTariff.fixedCharges;

        // Step 7: Display the bill
        System.out.println("\n--- Customer Information ---");
        matchedCustomer.displayCustomerInfo();

        System.out.println("--- Bill Information ---");
        System.out.println("Billing Month: " + matchedBillingInfo.billing_month);
        System.out.println("Current Regular Meter Reading: " + currRegMeterReading);
        if (enteredMeterType.equalsIgnoreCase("3Phase")) {
            System.out.println("Current Peak Meter Reading: " + currPeakMeterReading);
        }
        System.out.println("Electricity Cost (Regular): " + regCost);
        if (enteredMeterType.equalsIgnoreCase("3Phase")) {
            System.out.println("Electricity Cost (Peak): " + peakCost);
        }
        System.out.println("Sales Tax: " + taxAmount);
        System.out.println("Fixed Charges: " + matchedTariff.fixedCharges);
        System.out.println("Total Amount Due: " + totalAmountDue);
        System.out.println("Due Date: " + matchedBillingInfo.due_date);
        System.out.println("Bill Payment Status: " + matchedBillingInfo.bill_paid_status);

        if (!matchedBillingInfo.payment_date.isEmpty()) {
            System.out.println("Payment Date: " + matchedBillingInfo.payment_date);
        }
        System.out.println();
    }


    public void update_customer_data(List<Customer> cusInfos) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Ask the user for the customer ID to update
        System.out.print("Enter the Customer ID of the customer you want to update: ");
        String customerId = scanner.nextLine();

        // Step 2: Find the customer in the list
        Customer matchedCustomer = null;
        for (Customer customer : cusInfos) {
            if (customer.cus_id.equals(customerId)) {
                matchedCustomer = customer;
                break;
            }
        }

        if (matchedCustomer == null) {
            System.out.println("Customer with the given ID not found.");
            return;
        }

        // Step 3: Ask the user what they want to update
        System.out.println("What would you like to update?");
        System.out.println("1. Customer ID");
        System.out.println("2. Phone Number");
        System.out.println("3. Address");
        System.out.println("4. Regular Hour Units");
        System.out.println("5. Peak Hour Units");
        System.out.print("Enter your choice (1-5): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer

        switch (choice) {
            case 1:
                // Update Customer ID
                System.out.print("Enter new Customer ID: ");
                String newCusId = scanner.nextLine();
                matchedCustomer.cus_id = newCusId;
                break;

            case 2:
                // Update Phone Number
                System.out.print("Enter new Phone Number: ");
                String newPhone = scanner.nextLine();
                matchedCustomer.Phone_no = newPhone;
                break;

            case 3:
                // Update Address
                System.out.print("Enter new Address: ");
                String newAddress = scanner.nextLine();
                matchedCustomer.Address = newAddress;
                break;

            case 4:
                // Update Regular Hour Units
                System.out.print("Enter new Regular Hour Units: ");
                int newRegHours = scanner.nextInt();
                matchedCustomer.reg_hour_units = newRegHours;
                break;

            case 5:
                // Update Peak Hour Units (only for customers with 3-phase meters)
                if (matchedCustomer.Meter_type.equalsIgnoreCase("3Phase")) {
                    System.out.print("Enter new Peak Hour Units: ");
                    int newPeakHours = scanner.nextInt();
                    matchedCustomer.peak_hour_units = newPeakHours;
                } else {
                    System.out.println("This customer does not have a 3-phase meter.");
                }
                break;

            default:
                System.out.println("Invalid choice.");
                return;
        }

        // Step 4: Save the updated customer list back into the file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for (Customer customer : cusInfos) {
                writer.write(customer.cus_id + "," + customer.CNIC + "," + customer.Name + "," + customer.Address + ","
                        + customer.Phone_no + "," + customer.Cus_type + "," + customer.Meter_type + "," + customer.connec_date
                        + "," + customer.reg_hour_units + "," + customer.peak_hour_units);
                writer.newLine();
            }
            writer.close();
            System.out.println("Customer data updated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error updating customer data.");
        }
    }




    public void add_row(List<Customer> cusInfos, List<BillingInfo> billInfos, List<NadraDB> nadraInfos) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Ask for CNIC
        System.out.print("Enter CNIC of the customer: ");
        String cnic = scanner.nextLine();

        // Step 2: Check if the customer already has 3 meters installed
        int meterCount = 0;
        for (Customer customer : cusInfos) {
            if (customer.CNIC.equals(cnic)) {
                meterCount++;
            }
        }

        // If meter limit reached, display message and return
        if (meterCount >= 3) {
            System.out.println("Meter limit reached for this customer.");
            return;
        }

        // Step 3: Ask for customer details
        System.out.println("Enter new meter information for the customer:");

        System.out.print("Enter Customer ID: ");
        String cusId = scanner.nextLine();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();

        System.out.print("Enter Customer Type (D for Domestic, C for Commercial): ");
        char cusType = scanner.nextLine().charAt(0);

        System.out.print("Enter Meter Type (1Phase/3Phase): ");
        String meterType = scanner.nextLine();

        System.out.print("Enter Connection Date (dd/mm/yyyy): ");
        String connecDate = scanner.nextLine();

        System.out.print("Enter Regular Hour Units: ");
        int regHourUnits = scanner.nextInt();

        int peakHourUnits = 0;
        if (meterType.equalsIgnoreCase("3Phase")) {
            System.out.print("Enter Peak Hour Units: ");
            peakHourUnits = scanner.nextInt();
        }

        // Step 4: Create and add the new customer to the list
        Customer newCustomer = new Customer(cusId, cnic, name, address, phone, cusType, meterType, connecDate, regHourUnits, peakHourUnits);
        cusInfos.add(newCustomer);

        // Step 5: Ask for billing information and add to the billing list
        System.out.println("Enter billing information:");

        System.out.print("Enter Billing Month (as an integer, e.g., 1 for January): ");
        int billingMonth = scanner.nextInt();

        System.out.print("Enter Current Regular Meter Reading: ");
        int currRegMeter = scanner.nextInt();

        int currPeakMeter = 0;
        if (meterType.equalsIgnoreCase("3Phase")) {
            System.out.print("Enter Current Peak Meter Reading: ");
            currPeakMeter = scanner.nextInt();
        }

        System.out.print("Enter Reading Date (dd/mm/yyyy): ");
        scanner.nextLine();  // Clear the buffer
        String readingDate = scanner.nextLine();

        System.out.print("Enter Cost of Electricity: ");
        int costOfElec = scanner.nextInt();

        System.out.print("Enter Sales Tax: ");
        int salesTax = scanner.nextInt();

        System.out.print("Enter Fixed Charges: ");
        int fixedCharges = scanner.nextInt();

        int totalAmnt = costOfElec + salesTax + fixedCharges;

        System.out.print("Enter Due Date (dd/mm/yyyy): ");
        scanner.nextLine();  // Clear the buffer
        String dueDate = scanner.nextLine();

        System.out.print("Enter Bill Paid Status (Paid/Unpaid): ");
        String billPaidStatus = scanner.nextLine();

        System.out.print("Enter Payment Date (if paid, otherwise leave blank): ");
        String paymentDate = scanner.nextLine();

        // Step 6: Create and add the new billing info to the list
        BillingInfo newBillingInfo = new BillingInfo(cusId, billingMonth, currRegMeter, currPeakMeter, readingDate, costOfElec, salesTax, fixedCharges, totalAmnt, dueDate, billPaidStatus, paymentDate);
        billInfos.add(newBillingInfo);

        // Step 7: Save the new customer and billing info to their respective files
        try {
            // Save customer info
            BufferedWriter cusWriter = new BufferedWriter(new FileWriter(filename, true)); // Append mode
            cusWriter.write(newCustomer.cus_id + "," + newCustomer.CNIC + "," + newCustomer.Name + "," + newCustomer.Address + ","
                    + newCustomer.Phone_no + "," + newCustomer.Cus_type + "," + newCustomer.Meter_type + "," + newCustomer.connec_date + ","
                    + newCustomer.reg_hour_units + "," + newCustomer.peak_hour_units);
            cusWriter.newLine();
            cusWriter.close();

            // Save billing info
            BufferedWriter billWriter = new BufferedWriter(new FileWriter("C:\\Users\\city\\Desktop\\java\\LESCO_Billing_System_in_Java\\data\\Billinginfo.txt", true));
            // Append mode
            billWriter.newLine();
            billWriter.write(newBillingInfo.cus_id + "," + newBillingInfo.billing_month + "," + newBillingInfo.curr_reg_meter + "," + newBillingInfo.curr_reg_peak + ","
                    + newBillingInfo.reading_date + "," + newBillingInfo.cost_of_elec + "," + newBillingInfo.sales_tax + ","
                    + newBillingInfo.fixed_charges + "," + newBillingInfo.total_amnt + "," + newBillingInfo.due_date + ","
                    + newBillingInfo.bill_paid_status + "," + newBillingInfo.payment_date);
            billWriter.newLine();
            billWriter.close();

            System.out.println("New meter installed and customer/billing information saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving customer or billing data.");
        }
    }






}
