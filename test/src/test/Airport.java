package test;

/**
 * Author: Ravi Varadarajan
 * Date created: 10/7/19
 */
public class Airport {

    private final String code;
    private final String name;
    private final String city;
    private final String state;

    public Airport(String code, String name, String city, String state) {
        this.code = code;
        this.name = name;
        this.city = city;
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String toString() {
        return String.format("Airport with code: ,name: , city: , state: ",
                this.code, this.name, this.city, this.state);
    }

}
