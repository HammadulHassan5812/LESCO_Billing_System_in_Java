import java.util.Scanner;
import java.io.IOException;
import java.io.*;
import java.util.*;



public class Tax_tariff_info {
    private String meterType;
    private int regularUnitPrice;
    private Integer peakHourUnitPrice; // use Integer to handle null values
    private int taxPercentage;
    private int fixedCharges;

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

    // Method to load pricing data from file
    public static List<Tax_tariff_info> loadPricingData(String filename) {
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


}






