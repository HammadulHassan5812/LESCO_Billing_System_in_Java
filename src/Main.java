

public class Main {

    public static void main(String[] args)
    {
        Employee e = new Employee();
        e.load_EmployeeData("C:\\Users\\city\\Desktop\\java\\LESCO_Billing_System_in_Java\\data\\EmployeesData.txt");

        // Test login
//        if (e.is_Employee()) {
//            System.out.println("Login successful!");
//        } else {
//            System.out.println("Wrong Username or Password");
//        }
//
//        // Test password change
//        e.Change_password();
        e.displayEmployees();


    }
}
