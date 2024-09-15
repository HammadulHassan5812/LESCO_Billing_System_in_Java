import java.util.Scanner;
import java.io.IOException;
import java.io.*;
import java.util.*;



public class Tax_tariff_info {
    public String meterType;
    public int regularUnitPrice;
    public Integer peakHourUnitPrice; // use Integer to handle null values
    public int taxPercentage;
    public int fixedCharges;
    public static String filename;
    Tax_tariff_info(String fn)
    {
        this.filename=fn;
    }
    Tax_tariff_info(){}

    // Constructor
    public Tax_tariff_info(String meterType, int regularUnitPrice, Integer peakHourUnitPrice,
                            int taxPercentage, int fixedCharges) {
        this.meterType = meterType;
        this.regularUnitPrice = regularUnitPrice;
        this.peakHourUnitPrice = peakHourUnitPrice;
        this.taxPercentage = taxPercentage;
        this.fixedCharges = fixedCharges;
    }

    // Method to display the meter pricing info
    public void displayInfo() {
        System.out.println("Meter Type: " + meterType);
        System.out.println("Regular Unit Price: " + regularUnitPrice);
        System.out.println("Peak Hour Unit Price: " + (peakHourUnitPrice != null ? peakHourUnitPrice : "N/A"));
        System.out.println("Tax Percentage: " + taxPercentage + "%");
        System.out.println("Fixed Charges: " + fixedCharges);
        System.out.println();
    }

    // Getters and Setters for updating fields
    public String getMeterType() { return meterType; }
    public void setMeterType(String meterType) { this.meterType = meterType; }

    public int getRegularUnitPrice() { return regularUnitPrice; }
    public void setRegularUnitPrice(int regularUnitPrice) { this.regularUnitPrice = regularUnitPrice; }

    public Integer getPeakHourUnitPrice() { return peakHourUnitPrice; }
    public void setPeakHourUnitPrice(Integer peakHourUnitPrice) { this.peakHourUnitPrice = peakHourUnitPrice; }

    public int getTaxPercentage() { return taxPercentage; }
    public void setTaxPercentage(int taxPercentage) { this.taxPercentage = taxPercentage; }

    public int getFixedCharges() { return fixedCharges; }
    public void setFixedCharges(int fixedCharges) { this.fixedCharges = fixedCharges; }



    // Method to load pricing data from file
    public static List<Tax_tariff_info> loadPricingData() {
        List<Tax_tariff_info> pricingList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;

            // Read file line by line
            while ((line = reader.readLine()) != null) {
                // Split the line by comma
                String[] parts = line.split(",");

                if (parts.length == 5)
                {
                    String meterType = parts[0].trim();
                    int regularUnitPrice = Integer.parseInt(parts[1].trim());

                    // Handle blank value for peak hour unit price (assign null if it's blank)
                    Integer peakHourUnitPrice = parts[2].trim().isEmpty() ? null : Integer.parseInt(parts[2].trim());

                    int taxPercentage = Integer.parseInt(parts[3].trim());
                    int fixedCharges = Integer.parseInt(parts[4].trim());

                    // Create a MeterPricingInfo object and add to list
                    Tax_tariff_info pricingInfo = new Tax_tariff_info(meterType, regularUnitPrice, peakHourUnitPrice, taxPercentage, fixedCharges);
                    pricingList.add(pricingInfo);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pricingList;
    }


//save file data
    public static void savePricingData( List<Tax_tariff_info> pricingList) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            for (Tax_tariff_info info : pricingList) {
                writer.write(info.getMeterType() + "," + info.getRegularUnitPrice() + "," +
                        (info.getPeakHourUnitPrice() != null ? info.getPeakHourUnitPrice() : "") + "," +
                        info.getTaxPercentage() + "," + info.getFixedCharges());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void updateOrAddData(List<Tax_tariff_info> pricingList) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Would you like to update an existing entry or add a new one? (update/add): ");
        String choice = scanner.nextLine().trim().toLowerCase();

        if (choice.equals("update")) {
            System.out.println("Enter meter type to update: ");
            String meterType = scanner.nextLine().trim();

            // Find the entry by meter type
            for (Tax_tariff_info info : pricingList) {
                if (info.getMeterType().equalsIgnoreCase(meterType)) {
                    System.out.println("Current data for " + meterType + ":");
                    info.displayInfo();

                    // Update values
                    System.out.println("Enter new Regular Unit Price: ");
                    int newRegularPrice = Integer.parseInt(scanner.nextLine().trim());
                    info.setRegularUnitPrice(newRegularPrice);

                    System.out.println("Enter new Peak Hour Unit Price (leave blank if N/A): ");
                    String peakInput = scanner.nextLine().trim();
                    Integer newPeakPrice = peakInput.isEmpty() ? null : Integer.parseInt(peakInput);
                    info.setPeakHourUnitPrice(newPeakPrice);

                    System.out.println("Enter new Tax Percentage: ");
                    int newTaxPercentage = Integer.parseInt(scanner.nextLine().trim());
                    info.setTaxPercentage(newTaxPercentage);

                    System.out.println("Enter new Fixed Charges: ");
                    int newFixedCharges = Integer.parseInt(scanner.nextLine().trim());
                    info.setFixedCharges(newFixedCharges);
                    savePricingData(pricingList);
                    System.out.println("Data updated successfully!");
                    return;
                }
            }
            System.out.println("Meter type not found!");

        } else if (choice.equals("add"))
        {
            if(pricingList.size()==4)
            {
                System.out.println("There are already four rows You should update them ");
                return;
            }
            System.out.println("Enter new Meter Type: ");
            String newMeterType = scanner.nextLine().trim();

            System.out.println("Enter Regular Unit Price: ");
            int newRegularPrice = Integer.parseInt(scanner.nextLine().trim());

            System.out.println("Enter Peak Hour Unit Price (leave blank if N/A): ");
            String peakInput = scanner.nextLine().trim();
            Integer newPeakPrice = peakInput.isEmpty() ? null : Integer.parseInt(peakInput);

            System.out.println("Enter Tax Percentage: ");
            int newTaxPercentage = Integer.parseInt(scanner.nextLine().trim());

            System.out.println("Enter Fixed Charges: ");
            int newFixedCharges = Integer.parseInt(scanner.nextLine().trim());

            // Add new entry to the list
            Tax_tariff_info newInfo = new Tax_tariff_info(newMeterType, newRegularPrice, newPeakPrice, newTaxPercentage, newFixedCharges);
            pricingList.add(newInfo);
            savePricingData(pricingList);
            System.out.println("New entry added successfully!");
        } else {
            System.out.println("Invalid choice! Please choose 'update' or 'add'.");
        }
    }




}






