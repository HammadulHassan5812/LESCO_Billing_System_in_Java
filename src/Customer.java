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


 public static List<Customer> loadCustomerData(String filename) {
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



}
