import java.util.Scanner;
import java.io.IOException;
import java.io.*;
import java.util.*;

public class Customer
{
   public String cus_id;
   public String CNIC;
   private  String Name;
   private String Phone_no;
   private String Address;
   private Character Cus_type;
   private String Meter_type;
   private String connec_date;
   private int reg_hour_units;
   private int peak_hour_units;
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




}
