import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NadraDB
{
    private String CNIC;
    String issued_date;
    String expiry_date;


    public NadraDB(String CNIC1,String iss_date,String exp_date)
    {
        this.CNIC=CNIC1;
        this.issued_date=iss_date;
        this.expiry_date=exp_date;
    }

    public static List<NadraDB> Load_NBData(String filename) {
        List<NadraDB> NadraDB = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;

            // Read file line by line
            while ((line = reader.readLine()) != null) {
                // Split the line by comma
                String[] parts = line.split(",");

                if (parts.length == 3)
                {
                    String CNIC = parts[0];
                    String iss_date = parts[1];
                    String exp_date=parts[2];
                    // Handle blank value for peak hour unit price (assign null if it's blank)


                    // Create a MeterPricingInfo object and add to list
                    NadraDB ndb = new NadraDB(CNIC,iss_date, exp_date);
                    NadraDB.add(ndb);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NadraDB;
    }


    public  void displayInfo()
    {
        System.out.println("CNIC: "+CNIC);
        System.out.print("Issued Date: "+issued_date+" ");
        System.out.print("Expiry Date: "+expiry_date);
        System.out.println();
    }






}
