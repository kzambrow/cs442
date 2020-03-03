package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Ravi Varadarajan
 * Date created: 10/7/19
 */
public class AirlineDAO {

    private Connection conn;

    /**
     * Constructor
     * @param conn connection to use in all db operations of this DAO
     */
    public AirlineDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Get the list of all airports stored in DB
     * @return
     */
    public List<Airport> getAllAirports() {
        List<Airport> airports = new ArrayList<>();
        // complete code here
        return airports;
    }

    /**
     * Get the list of all airports, the flights of an airline depart from
     * @param airlineCode
     * @return
     */
    public List<Airport> getDepartureAirports(String airlineCode) {
        List<Airport> airports = new ArrayList<>();
        // complete code here
        return airports;
    }

    /**
     * Get the list of all airports, the flights of an airline arrive at
     * @param airlineCode
     * @return
     */
    public List<Airport> getArrivalAirports(String airlineCode) {
        List<Airport> airports = new ArrayList<>();
        // complete code here
        return airports;
    }

    /**
     * Get the list of all direct flights stored in DB
     * @return
     */
    public List<AirlineFlight> getAllDirectFlights() {
        List<AirlineFlight> airports = new ArrayList<>();
        // complete code here
        return airports;
    }

    public static void main(String [] args) {

    }
}
