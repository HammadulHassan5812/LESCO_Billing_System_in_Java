import  java.util.List;
import  java.util.Scanner;





public class Main
{



    public static void main(String[] args)
    {
        //Loading files data
        Employee e = new Employee("C:\\Users\\city\\Desktop\\java\\LESCO_Billing_System_in_Java\\data\\EmployeesData.txt");
        e.load_EmployeeData();
        Customer cus=new Customer("C:\\Users\\city\\Desktop\\java\\LESCO_Billing_System_in_Java\\data\\CustomerInfo.txt");
        List<Customer> customers =Customer.loadCustomerData();
        List<BillingInfo> billingInfos = BillingInfo.loadBillingData("C:\\Users\\city\\Desktop\\java\\LESCO_Billing_System_in_Java\\data\\BillingInfo.txt");
        NadraDB ndb1=new NadraDB("C:\\Users\\city\\Desktop\\java\\LESCO_Billing_System_in_Java\\data\\NadraDB.txt");
        List<NadraDB>ndb=NadraDB.Load_NBData();
         Tax_tariff_info t1=new Tax_tariff_info("C:\\Users\\city\\Desktop\\java\\LESCO_Billing_System_in_Java\\data\\TariffTaxInfo.txt");
         List<Tax_tariff_info> pricingInfos = Tax_tariff_info.loadPricingData();





         //Menu
        Scanner s=new Scanner(System.in);


        while (true)
        {
            System.out.println("WELCOME TO LESCO BILLING SYSTEM");
            System.out.println("Enter 1 for Employees");
            System.out.println("Enter 2 for Customers");
            System.out.println("Enter -1 to Exit");

            System.out.println("Enter your choice");
            int ch=s.nextInt();

            if(((ch!=1&&ch!=2)))
            {
                return;
            }
            if(ch==1)
            {

                System.out.println("For Employees");
                System.out.println("1-Change Password");
                System.out.println("2-Update Customer Data");
                System.out.println("3-Add New Meter");
                System.out.println("4-Update entries in the TariffTaxInfo");
                System.out.println("5-View Bill");
                System.out.println("6-Show Paid and UnPaid Bills");
                System.out.println("7-View reports of customers whose CNIC is about to expire");
                System.out.println("\n");


                System.out.println("Enter your choice");
                int i=s.nextInt();

                if(i==1)
                {
                    //         //Test login
                 if (e.is_Employee())
                 {
                       System.out.println("Login successful!");
                         e.Change_password();
                 } else {
                     System.out.println("Wrong Username or Password");
                             }

                }
                if(i==2)
                {
                    Customer cus4=new Customer();
                    //cus3.add_row(customers,billingInfos,ndb);
                     cus4.update_customer_data(customers);  //update customer's data
                    //cus3.view_curr_bill(billingInfos,pricingInfos);  //view curr_bill;


                }
                if(i==3)
                {
                     Customer cus4=new Customer();
                    cus4.add_row(customers,billingInfos,ndb);
                    // cus3.update_customer_data(customers);  //update customer's data
                    //cus3.view_curr_bill(billingInfos,pricingInfos);  //view curr_bill;


                }
                if(i==4)
                {
                    //updating tariff_tax_info
                    Tax_tariff_info t2=new Tax_tariff_info();
                     t2.updateOrAddData(pricingInfos);


                }
                if(i==5)
                {
                    BillingInfo b1=new BillingInfo();
                    // b1.update_status(billingInfos,customers);
                     b1.view_bill(billingInfos);

                }
                if(i==6)
                {
                    BillingInfo b2=new BillingInfo();
                    b2.Show_Bill_reports(billingInfos);

                }
                if(i==7)
                {

                    //checks_expiry date and update expiry_date of cutomers
                      NadraDB nadraDB2=new NadraDB();
                      nadraDB2.Check_expiry(ndb,customers);
                    // nadraDB.update_expiry_date(ndb,customers);


                }
                else
                {
                    System.out.println("Wrong Input");
                }






            }
            if(ch==2)
            {

                System.out.println("For Customers");
                System.out.println("1-View Current Bill");
                System.out.println("2-Update Expiry Date");
                System.out.println();
                System.out.println("Enter your choice");
                int i=s.nextInt();

                if(i==1)
                {

                     Customer cus3=new Customer();
                    //cus3.add_row(customers,billingInfos,ndb);
                    // cus3.update_customer_data(customers);  //update customer's data
                    cus3.view_curr_bill(billingInfos,pricingInfos);  //view curr_bill;

                }
                if(i==2)
                {
                    //checks_expiry date and update expiry_date of cutomers
                     NadraDB nadraDB=new NadraDB();
                    //nadraDB.Check_expiry(ndb,customers);
                     nadraDB.update_expiry_date(ndb,customers);

                }
                else
                {
                    System.out.println("Wrong Input");
                }

            }


        }






















          // Customer cus3=new Customer();
           //cus3.add_row(customers,billingInfos,ndb);
          // cus3.update_customer_data(customers);  //update customer's data
        //cus3.view_curr_bill(billingInfos,pricingInfos);  //view curr_bill;











        //view any bill with customer_id
         //BillingInfo b1=new BillingInfo();
        // b1.update_status(billingInfos,customers);
         // b1.view_bill(billingInfos);



         //checks_expiry date and update expiry_date of cutomers
            //  NadraDB nadraDB=new NadraDB();
           //nadraDB.Check_expiry(ndb,customers);
           // nadraDB.update_expiry_date(ndb,customers);






         //updating tariff_tax_info
//          Tax_tariff_info t2=new Tax_tariff_info();
//          t2.updateOrAddData(pricingInfos);





        //showing bill_reports
//        BillingInfo b2=new BillingInfo();
//        b2.Show_Bill_reports(billingInfos);



//         //Test login
//        if (e.is_Employee()) {
//            System.out.println("Login successful!");
//            e.Change_password();
//        } else {
//            System.out.println("Wrong Username or Password");
//        }


//
        // Display all customer data
//        for (Customer customer : customers) {
//            customer.displayCustomerInfo();
//        }
//
//

//        for (BillingInfo billingInfo : billingInfos) {
//            billingInfo.displayBillingInfo();
//        }

         //Display the loaded pricing data
//        for (Tax_tariff_info pricingInfo : pricingInfos) {
//            pricingInfo.displayInfo();
//        }


        //NadraDB data
//        for (NadraDB nadraDB : ndb) {
//         nadraDB.displayInfo();
//        }


    }
}
