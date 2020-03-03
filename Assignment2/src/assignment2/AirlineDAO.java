package assignment2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Kamil Zambrowski
 * Date created: 10/7/19
 * I Pledge My Honor That I Have Abided By The Stevens Honor System
 */
public class AirlineDAO {

    private Connection conn;

    /**
     * Constructor
     * @param conn connection to use in all db operations of this DAO
     */
    public AirlineDAO(Connection conn) {
        this.conn = conn;
        try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void insertAirport(Airport airport) throws Exception {
    	String sql = "INSERT INTO Airport(airport_code, name, city, state) "
    			+ "VALUES (?,?,?,?)";
    	PreparedStatement stmt = conn.prepareStatement(sql);
    	stmt.setString(1, airport.getCode());
    	stmt.setString(2, airport.getName());
    	stmt.setString(3, airport.getCity());
    	stmt.setString(4, airport.getState());
    	stmt.executeUpdate();
    	conn.commit();
    }
    public void insertAirports(Airport[] airports) throws Exception {
    	String sql = "INSERT INTO Airport(airport_code, name, city, state) "
    			+ "VALUES (?,?,?,?)";
    	PreparedStatement stmt = conn.prepareStatement(sql);
    	for (Airport airport: airports) {
        	stmt.setString(1, airport.getCode());
        	stmt.setString(2, airport.getName());
        	stmt.setString(3, airport.getCity());
        	stmt.setString(4, airport.getState());
        	stmt.addBatch();
    	}
    	stmt.executeBatch();
    	conn.commit();
    }

    /**
     * Get the list of all airports stored in DB
     * @return
     */
    public List<Airport> getAllAirports() {
        List<Airport> airports = new ArrayList<>();
        // complete code here
        Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try { 
        	ResultSet rs = stmt.executeQuery("SELECT * FROM airport");
        	while(rs.next()) {
        		String code = rs.getString(1);
        		String name = rs.getString(2);
        		String city = rs.getString(3);
        		String state = rs.getString(4);
        		Airport entry = new Airport(code, name, city, state);
        		airports.add(entry);
        	}
        	rs.close();
        	stmt.close();
        } catch (SQLException e) {
        	e.printStackTrace();
        }
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
        Statement stmt = null;
        try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
        	String temp = String.format("SELECT * FROM airport join flightleg on flightleg.dep_airport_code = airport.airport_code where airline_code = '%s'", airlineCode);
        	ResultSet rs = stmt.executeQuery(temp);
        	while (rs.next()) {
        		String code = rs.getString(1);
        		String name = rs.getString(2);
        		String city = rs.getString(3);
        		String state = rs.getString(4);
        		Airport entry = new Airport(code,name,city,state);
        		airports.add(entry);
        	}
        	rs.close();
        	stmt.close();
        }
        catch (SQLException e) {
        	e.printStackTrace();
        }
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
        Statement stmt = null;
        try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
        	String temp = String.format("SELECT * FROM airport join flightleg on flightleg.arr_airport_code = airport.airport_code where airline_code = '%s'", airlineCode);
        ResultSet rs = stmt.executeQuery(temp);
        while (rs.next()) {
        	String code = rs.getString(1);
        	String name = rs.getString(2);
    		String city = rs.getString(3);
    		String state = rs.getString(4);
    		Airport entry = new Airport(code,name,city,state);
    		airports.add(entry);
        }
        rs.close();
        stmt.close();
        }
        catch (SQLException e) {
        	e.printStackTrace();
        }
        return airports;
    }

    /**
     * Get the list of all direct flights stored in DB
     * @return
     */
    public List<AirlineFlight> getAllDirectFlights() {
        List<AirlineFlight> flights = new ArrayList<>();
        // complete code here
        Statement stmt = null;
        try {
        	stmt = conn.createStatement();
        } catch (SQLException e1) {
        	e1.printStackTrace();
        }
        try {
        	String temp = "SELECT flightleg.airline_code, flight_number, flight.week_days FROM flight natural join flightleg where flightleg.leg_number = 1"
        			+ " except select flightleg.airline_code,flight_number, flight.week_days from flight natural join flightleg where flightleg.leg_number = 2"; 
        	ResultSet rs = stmt.executeQuery(temp);
        	while(rs.next()) {
        		String code = rs.getString(1);
        		int number = rs.getInt(2);
        		String weekdays = rs.getString(3);
        		AirlineFlight entry = new AirlineFlight();
        		entry.setCode(code);
        		entry.setNumber(number);
        		entry.setFlightDays(weekdays);
        		flights.add(entry);
        	}
        	rs.close();
        	stmt.close();
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return flights;
    }

    private static <T> void printListInfo(String context, List<T> list) {
        System.out.println("printing " + context + " ...");
        for (T item : list) {
            System.out.println(item.toString());
        }
    }

    public static void main(String [] args) throws Exception {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:1234/postgres";
        Connection conn =  DriverManager.getConnection(url,"postgres", "password");
        AirlineDAO dao = new AirlineDAO(conn);
        Airport test = new Airport("PHL", "Philadelphia International Airport", "Philadelphia", "PA");
        Airport tests[] = {new Airport("1","2","3","4"), new Airport("5","6","7","8")};    
        System.out.println("Airports:");
        dao.insertAirport(test);
        dao.insertAirports(tests);
        printListInfo("All the airports", dao.getAllAirports());
        printListInfo("All departure airports for UA", dao.getDepartureAirports("UA"));
        printListInfo("All arrival airports for UA", dao.getArrivalAirports("UA"));
        printListInfo("All direct flights",dao.getAllDirectFlights());
    }
}
