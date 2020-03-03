package edu.stevens.cs442.assignments;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployeeDAOTests {
	private static java.util.Date toDate(String dateStr) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static void main(String [] args) throws Exception {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:1234/postgres";
        Connection conn =  DriverManager.getConnection(url,"postgres", "password");
        EmployeeDAO dao = new EmployeeDAO(conn);
        List<Employee> empList = new ArrayList<>();
        Employee CEO = new Employee(1, "Big", "Boss", toDate("2010-01-01"), 500000);
        Employee Boss1 = new Employee(2, "Jake", "Lee", toDate("2011-01-01"), 400000);
        Employee Boss2 = new Employee(3, "Jack", "Last", toDate("2011-01-01"), 300000);
        Employee Manager1 = new Employee(4, "Jane", "Doe", toDate("2011-01-01"), 200000);
        Employee Manager2 = new Employee(5, "John", "Doe", toDate("2012-01-01"), 100000);
        Employee Manager3 = new Employee(6, "Jesse", "Day", toDate("2012-01-01"), 100000);
        empList.add(CEO);
        empList.add(Boss1);
        empList.add(Boss2);
        empList.add(Manager1);
        empList.add(Manager2);
        empList.add(Manager3);
        dao.addEmployees(empList, null);
        empList.clear();
        Department d1 = new Department(101, "Corporate Office", 1);
        Department d2 = new Department(102, "Lower Office 1", 2);
        Department d3 = new Department(103, "Lower Office 2", 3);
        Department d4 = new Department(104, "Sales", 4);
        Department d5 = new Department(105, "Tech", 5);
        dao.addDepartment(d1);
        dao.addDepartment(d2);
        dao.addDepartment(d3);
        dao.addDepartment(d4);
        dao.addDepartment(d5);
        CEO.setDept(d1);
        Boss1.setDept(d1);
        Boss2.setDept(d1);
        Manager1.setDept(d2);
        Manager2.setDept(d2);
        Manager3.setDept(d3);
        dao.updateEmployee(CEO);
        dao.updateEmployee(Boss1);
        dao.updateEmployee(Boss2);
        dao.updateEmployee(Manager1);
        dao.updateEmployee(Manager2);
        dao.updateEmployee(Manager3);
        Employee Employee1 = new Employee(7, "Int", "Tern", toDate("2013-01-01"), 50000);
        Employee Employee2 = new Employee(8, "New", "Bee", toDate("2013-01-01"), 50000);
        Employee1.setDept(d4);
        Employee2.setDept(d4);
        empList.add(Employee1);
        empList.add(Employee2);
        dao.addEmployees(empList, d4.getDeptId());
        empList.clear();
        Employee Employee3 = new Employee(9, "Five", "Guy", toDate("2014-01-01"), 60000);
        Employee3.setDept(d5);
        empList.add(Employee3);
        dao.addEmployees(empList, d5.getDeptId());
        empList.clear();
        Employee Employee4 = new Employee(10, "Last", "Man", toDate("2015-01-01"), 70000);
        empList.add(Employee4);
        dao.addEmployees(empList, null);
        empList.clear();
        Set<Integer> empIds = new HashSet<>();
        for (int i=1; i <= 9; i++) {
        	empIds.add(i);
        }
        List<Employee> employees = dao.getEmployees(empIds);
        for (Employee emp: employees) {
            System.out.println(emp);
        }
        List<Department> depts = new ArrayList<>();
        depts.add(d1);
        depts.add(d2);
        depts.add(d3);
        depts.add(d4);
        depts.add(d5);
        dao.setAverageSalaries(depts);
        for (Department dept : depts) {
            System.out.println(dept);
        }
        List<Employee> sups = dao.getSuperiors(Employee2);
        System.out.println("Superiors of "+ Employee2.getFirstName() + " " +Employee2.getLastName() + "...");
        for (Employee emp : sups) {
            System.out.println(emp);
        }
        dao.deleteEmployee(Employee3);
        conn.close();
}
}
