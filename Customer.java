public class Customer {
    int user_id;
    String name;
    double latitude;
    double longitude;

    public enum ATTRIBUTE {
        USER_ID,
        NAME,
        LATITUDE,
        LONGITUDE;

        void setValue(String item, Customer customer) {
            item = item.trim();
            switch (this) {
                case USER_ID:
                    customer.setUser_id(Integer.parseInt(item));
                    break;
                case NAME:
                    customer.setName(item);
                    break;
                case LATITUDE:
                    customer.setLatitude(Double.parseDouble(item));
                    break;
                case LONGITUDE:
                    customer.setLongitude(Double.parseDouble(item));
                    break;
                default:
                    throw new AssertionError("Invalid JSON " + this);
            }
        }

    }

    public Customer(){

    }

    public Customer(String[] strArr){
        for(String s : strArr){
            int index = s.indexOf(":");
            ATTRIBUTE attr = ATTRIBUTE.valueOf(s.substring(0, index).trim().toUpperCase());
            attr.setValue(s.substring(++ index), this);
        }
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getUser_id() {

        return user_id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
