package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Test {
	public static void main(String[] args) throws Exception {
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://localhost:1234/postgres";
		Connection c = DriverManager.getConnection(url,"postgres","password");
		System.out.println(c);
		ResultSet rs = c.createStatement().executeQuery("select airport_code from airport");
		
		while (rs.next()) {
			System.out.println(rs.getString(1));
		}
	}
}
