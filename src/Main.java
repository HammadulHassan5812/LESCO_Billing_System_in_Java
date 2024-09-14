import  java.util.List;

public class Main {

    public static void main(String[] args)
    {
        Employee e = new Employee("C:\\Users\\city\\Desktop\\java\\LESCO_Billing_System_in_Java\\data\\EmployeesData.txt");
        e.load_EmployeeData();
        List<Customer> customers =Customer.loadCustomerData("C:\\Users\\city\\Desktop\\java\\LESCO_Billing_System_in_Java\\data\\Customerinfo.txt");
        List<BillingInfo> billingInfos = BillingInfo.loadBillingData("C:\\Users\\city\\Desktop\\java\\LESCO_Billing_System_in_Java\\data\\Billinginfo.txt");
        List<Tax_tariff_info> pricingInfos = Tax_tariff_info.loadPricingData("C:\\Users\\city\\Desktop\\java\\LESCO_Billing_System_in_Java\\data\\TariffTaxInfo.txt");




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

        // Display the loaded pricing data
//        for (Tax_tariff_info pricingInfo : pricingInfos) {
//            pricingInfo.displayInfo();
//        }



    }
}
