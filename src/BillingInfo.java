import java.util.Scanner;
import java.io.IOException;
import java.io.*;
import java.util.*;


public class BillingInfo
{
    private String cus_id;
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





}
