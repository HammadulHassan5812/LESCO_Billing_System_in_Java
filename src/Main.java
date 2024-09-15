import  java.util.List;

public class Main {

    public static void main(String[] args)
    {
        //Loading files data
        Employee e = new Employee("C:\\Users\\city\\Desktop\\java\\LESCO_Billing_System_in_Java\\data\\EmployeesData.txt");
        e.load_EmployeeData();
        List<Customer> customers =Customer.loadCustomerData("C:\\Users\\city\\Desktop\\java\\LESCO_Billing_System_in_Java\\data\\Customerinfo.txt");
        List<BillingInfo> billingInfos = BillingInfo.loadBillingData("C:\\Users\\city\\Desktop\\java\\LESCO_Billing_System_in_Java\\data\\Billinginfo.txt");
        NadraDB ndb1=new NadraDB("C:\\Users\\city\\Desktop\\java\\LESCO_Billing_System_in_Java\\data\\NadraDB.txt");
        List<NadraDB>ndb=NadraDB.Load_NBData();
         Tax_tariff_info t1=new Tax_tariff_info("C:\\Users\\city\\Desktop\\java\\LESCO_Billing_System_in_Java\\data\\TariffTaxInfo.txt");
         List<Tax_tariff_info> pricingInfos = Tax_tariff_info.loadPricingData();











        //view any bill with customer_id
//         BillingInfo b1=new BillingInfo();
//         b1.view_bill(billingInfos);



         //checks_expiry date and update expiry_date of cutomers
//              NadraDB nadraDB=new NadraDB();
//            nadraDB.Check_expiry(ndb,customers);
//            nadraDB.update_expiry_date(ndb,customers);






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
