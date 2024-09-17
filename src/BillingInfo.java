import java.util.Scanner;
import java.io.IOException;
import java.io.*;
import java.util.*;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;


public class BillingInfo
{
    public String cus_id;
    int billing_month;
    int curr_reg_meter;
    int curr_reg_peak;
    String reading_date;
    int cost_of_elec;
    int sales_tax;
    int fixed_charges;
    int total_amnt;
    String due_date;
    String bill_paid_status;
    String payment_date;

public BillingInfo(){}

    public BillingInfo(String cus_id,int billing_m, int curr_reg_meter, int curr_reg_peak, String reading_date,
                       int cost_of_elec, int sales_tax, int fixed_charges, int total_amnt,
                       String due_date, String bill_paid_status, String payment_date) {

        this.cus_id = cus_id;
        this.billing_month=billing_m;
        this.curr_reg_meter = curr_reg_meter;
        this.curr_reg_peak = curr_reg_peak;
        this.reading_date = reading_date;
        this.cost_of_elec = cost_of_elec;
        this.sales_tax = sales_tax;
        this.fixed_charges = fixed_charges;
        this.total_amnt = total_amnt;
        this.due_date = due_date;
        this.bill_paid_status = bill_paid_status;
        this.payment_date = payment_date;
    }

    public void displayBillingInfo() {
        System.out.println("Customer ID: " + cus_id);
        System.out.println("Billing_Month is "+billing_month);
        System.out.println("Current Regular Meter Reading: " + curr_reg_meter);
        System.out.println("Current Peak Meter Reading: " + curr_reg_peak);
        System.out.println("Reading Date: " + reading_date);
        System.out.println("Cost of Electricity: " + cost_of_elec);
        System.out.println("Sales Tax: " + sales_tax);
        System.out.println("Fixed Charges: " + fixed_charges);
        System.out.println("Total Amount: " + total_amnt);
        System.out.println("Due Date: " + due_date);
        System.out.println("Bill Paid Status: " + bill_paid_status);
        System.out.println("Payment Date: " + payment_date);
        System.out.println();
    }


    public static List<BillingInfo> loadBillingData(String filename) {
        List<BillingInfo> billingList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;

            // Read file line by line
            while ((line = reader.readLine()) != null) {
                // Remove any extra spaces and split line into parts by comma
                String[] parts = line.split(",");
                if (parts.length == 12) {
                    String cus_id = parts[0];
                    int billing_month=Integer.parseInt(parts[1]);
                    int curr_reg_meter = Integer.parseInt(parts[2]);
                    int curr_reg_peak = Integer.parseInt(parts[3]);
                    String reading_date = parts[4];
                    int cost_of_elec = Integer.parseInt(parts[5]);
                    int sales_tax = Integer.parseInt(parts[6]);
                    int fixed_charges = Integer.parseInt(parts[7]);
                    int total_amnt = Integer.parseInt(parts[8]);
                    String due_date = parts[9];
                    String bill_paid_status = parts[10];
                    String payment_date = parts[11].trim().equals("0") ? "" : parts[11].trim();  // Handle '0' as empty payment date

                    // Create new BillingInfo object and add to list
                    BillingInfo billingInfo = new BillingInfo(cus_id,billing_month, curr_reg_meter, curr_reg_peak, reading_date,
                            cost_of_elec, sales_tax, fixed_charges, total_amnt, due_date, bill_paid_status, payment_date);
                    billingList.add(billingInfo);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return billingList;
    }

    public void Show_Bill_reports(List<BillingInfo> billingInfos)
    {
        int count1=0;
        int count2=0;

        for (BillingInfo billingInfo : billingInfos)
        {

            if(billingInfo.bill_paid_status.equals("paid"))
            {
               count1++;
            }
            else
            {
                count2++;
            }
        }
        System.out.println("The amount of Paid Bills "+count1);
        System.out.println("The amount of Unpaid Bills is "+count2);

    }


    public void view_bill(List<BillingInfo> billingInfos) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for customer ID
        System.out.print("Enter Customer ID to view the bill: ");
        String customerId = scanner.nextLine();

        boolean billFound = false;

        // Iterate through the list of billing info to find the matching customer ID
        for (BillingInfo billingInfo : billingInfos) {
            if (billingInfo.cus_id.equals(customerId)) {
                // Display billing information
                billingInfo.displayBillingInfo();
                billFound = true;
                break;  // Exit the loop once the bill is found and displayed
            }
        }

        if (!billFound) {
            System.out.println("No bill found for Customer ID: " + customerId);
        }
    }


    public void update_status(List<BillingInfo> billingInfos, List<Customer> cusInfos) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for Customer ID
        System.out.print("Enter Customer ID to update bill status: ");
        String customerId = scanner.nextLine();

        boolean billingInfoFound = false;
        boolean customerInfoFound = false;

        // Find the corresponding BillingInfo and update the bill paid status
        for (BillingInfo billingInfo : billingInfos) {
            if (billingInfo.cus_id.equals(customerId)) {
                billingInfoFound = true;

                // Display current bill status
                System.out.println("Current Bill Paid Status: " + billingInfo.bill_paid_status);

                // Update bill status
                if (billingInfo.bill_paid_status.equals("unpaid")) {
                    billingInfo.bill_paid_status = "paid";  // Update to paid
                    System.out.println("Bill status updated to 'paid'.");

                    // Now update the corresponding customer information if customer is domestic
                    for (Customer customer : cusInfos) {
                        if (customer.cus_id.equals(customerId)) {
                            customerInfoFound = true;

                            if (customer.Cus_type == 'D') {  // Check if customer is domestic
                                // Prompt the user to enter the updated regular and peak hour units
                                System.out.print("Enter the updated regular hour units: ");
                                customer.reg_hour_units = scanner.nextInt();

                                if (customer.Meter_type.equalsIgnoreCase("3Phase")) {
                                    System.out.print("Enter the updated peak hour units: ");
                                    customer.peak_hour_units = scanner.nextInt();
                                }

                                System.out.println("Updated regular and peak hour units have been saved for the customer.");
                            }
                            break;
                        }
                    }
                } else {
                    System.out.println("Bill is already marked as paid.");
                }
                break;
            }
        }

        if (!billingInfoFound) {
            System.out.println("No billing information found for Customer ID: " + customerId);
        } else if (!customerInfoFound) {
            System.out.println("No customer information found for Customer ID: " + customerId);
        }

        // Save updated billing information and customer information back to files
        saveBillingData(billingInfos, "C:\\Users\\city\\Desktop\\java\\LESCO_Billing_System_in_Java\\data\\BillingInfo.txt");
        saveCustomerData(cusInfos, "C:\\Users\\city\\Desktop\\java\\LESCO_Billing_System_in_Java\\data\\CustomerInfo.txt");
    }


    // Method to save updated billing information back to the file
    // Method to save updated billing information back to the file
    private void saveBillingData(List<BillingInfo> billingInfos, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            // Write each billing info back to the file
            for (BillingInfo billingInfo : billingInfos) {
                writer.write(billingInfo.cus_id + "," + billingInfo.billing_month + "," + billingInfo.curr_reg_meter + "," + billingInfo.curr_reg_peak + ","
                        + billingInfo.reading_date + "," + billingInfo.cost_of_elec + "," + billingInfo.sales_tax + ","
                        + billingInfo.fixed_charges + "," + billingInfo.total_amnt + "," + billingInfo.due_date + ","
                        + billingInfo.bill_paid_status + "," + (billingInfo.payment_date.equals("") ? "0" : billingInfo.payment_date));
                writer.newLine();
            }

            writer.close();
            System.out.println("Billing data updated and saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error updating billing data.");
        }
    }

    // Method to save updated customer information back to the file
    private void saveCustomerData(List<Customer> cusInfos, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            // Write each customer info back to the file
            for (Customer customer : cusInfos) {
                writer.write(customer.cus_id + "," + customer.CNIC + "," + customer.Name + "," + customer.Address + ","
                        + customer.Phone_no + "," + customer.Cus_type + "," + customer.Meter_type + "," + customer.connec_date + ","
                        + customer.reg_hour_units + "," + customer.peak_hour_units);
                writer.newLine();
            }

            writer.close();
            System.out.println("Customer data updated and saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error updating customer data.");
        }
    }










}
