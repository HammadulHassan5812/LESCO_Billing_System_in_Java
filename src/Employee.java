import java.util.Scanner;
import java.io.IOException;
import java.io.*;
import java.util.*;

public class Employee {
    // username and password storage
    private String filename;
    private Map<String, String> employees = new HashMap<>();

    // Constructor
    Employee(String filename1)
    {
           filename=filename1;
    }

    // Method to load employee data from file
    public void load_EmployeeData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0].trim(); // remove extra spaces
                    String pass = parts[1].trim(); // remove extra spaces
                    employees.put(username, pass);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void displayEmployees() {
        for (Map.Entry<String, String> entry : employees.entrySet()) {
            System.out.println("Username: " + entry.getKey() + ", Password: " + entry.getValue());
        }
    }

    // Method to check if an employee is valid (login)
    public boolean is_Employee() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        return employees.containsKey(username) && employees.get(username).equals(password);
    }

    // Method to change password
    public void Change_password() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter current password: ");
        String password = scanner.nextLine();

        // Check if the current username and password match
        if (employees.containsKey(username) && employees.get(username).equals(password)) {
            System.out.print("Enter new password: ");  // Asking for new password
            String newPassword = scanner.nextLine();

            employees.put(username, newPassword);  // Updating password
            System.out.println("Password updated successfully for user: " + username);
            saveEmployeeData();
        } else {
            System.out.println("You have entered the wrong password.");
        }
    }

    public void saveEmployeeData() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for (Map.Entry<String, String> entry : employees.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
