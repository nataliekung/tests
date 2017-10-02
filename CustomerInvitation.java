import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerInvitation {
    private static final String FILE_PATH = "customers.txt";
    private static final String OUTPUT_PATH = "customers_to_be_invited.txt";
    //    Latitude and Longitude of office location
    private static double LATITUDE = Math.toRadians(53.3381985);
    private static double LONGITUDE = Math.toRadians(-6.2592576);
    private static int EARTH_RADIUS = 6373; //km
    private static int RANGE = 100; //km
    private static String DELIM = ",";
    private static String JSON_REGEXP = "\\{\\s*(\"(latitude|user_id|name|longitude)\"\\s*:(.*?),\\s*){3}\"(latitude|user_id|name|longitude)\"\\s*:(.*?)\\s*\\}";
    /**
     * Read the JSON file to get all the customers
     * @param filePath
     * @return The list of customers from JSON file
     */
    private List<Customer> loadFile(String filePath){
        List<Customer> customerList = new ArrayList<Customer>();
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                Pattern jsonPattern = Pattern.compile(JSON_REGEXP);
                Matcher m = jsonPattern.matcher(line.trim());
                if(m.find()){
                    int line_len = line.length();
                    line = line.replaceAll("[\"{}]","");
                    String[] line_Arr = line.split(DELIM);
                    customerList.add(new Customer(line_Arr));
                }
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file");
        }
        catch(IOException ex) {
            System.out.println("Error reading file");
        }

        return customerList;
    }

    /**
     * Get the distance between the locations of office and current user
     * @param user_latitude
     * @param user_longitude
     * @return the distance between office and the current user
     */
    private double getDistance(double user_latitude, double user_longitude){
       /*
        Haversine formula:
        a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
        c = 2 ⋅ atan2( √a, √(1−a) )
        d = R ⋅ c
        */
        double d_Lat = (Math.toRadians(user_latitude) - LATITUDE);
        double d_Lon = (Math.toRadians(user_longitude) - LONGITUDE);

        double a = Math.sin(d_Lat / 2) * Math.sin(d_Lat / 2) +
                Math.cos(LONGITUDE) * Math.cos(Math.toRadians(user_latitude)) *
                        Math.sin(d_Lon / 2) * Math.sin(d_Lon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

        return distance;
    }

    /**
     * Get all the customers that are within 100km and will be invited
     * @return the list of customers that are within 100km of the office
     */
    private List<Customer> getCloseCustomers(){
        List<Customer> customerList = loadFile(FILE_PATH);
        List<Customer> customerInvited = new ArrayList<Customer>();

        for(Customer c : customerList){
            if(getDistance(c.getLatitude(), c.getLongitude()) <= RANGE){
                customerInvited.add(c);
            }
        }

        return customerInvited;
    }

    /**
     * sort a list of customers by user id ascending
     * @param customers
     */
    private void sortCustomerById(List<Customer> customers){
//        sort customers by user_id
        Collections.sort(customers, new Comparator<Customer>(){
            public int compare(Customer customer1, Customer customer2){
                return customer1.getUser_id() - customer2.getUser_id();
            }
        });
    }

    /**
     * Output the lucky customers into the customers_to_be_invited file
     * @param customers
     */
    private void printCustomers(List<Customer> customers){
        BufferedWriter bw = null;
        FileWriter fw = null;
        String content = "Customers to be invited: \n";

        sortCustomerById(customers);
        for(Customer customer : customers){
            content += customer.getUser_id() + " : " + customer.getName() +" \n";
        }

        try {
            fw = new FileWriter(OUTPUT_PATH);
            bw = new BufferedWriter(fw);
            bw.write(content);
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Main
     * @param argv
     */
    public static void main(String argv[]) {
        CustomerInvitation cusObj = new CustomerInvitation();
        List<Customer> customers = cusObj.getCloseCustomers();
        cusObj.printCustomers(customers);
    }

}
