package edu.stevens.cs442.assignments;

import java.io.FileWriter;

//Kamil Zambrowski
//I Pledge My Honor That I Have Abided By The Stevens Honor System

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class EmployeeDAO extends AbstractEmployeeDAO {
	public EmployeeDAO(Connection conn) {
		super(conn);
	}
	public void addDepartment(Department dept) throws DepartmentException {
		String sql = "INSERT INTO department(dept_id, dept_name, manager_id) values (?,?,?)";
		String check = "SELECT dept_id, dept_name, manager_id FROM department WHERE dept_id = ? and dept_name = ? and manager_id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(check);
			stmt.setInt(1, dept.getDeptId());
			stmt.setString(2, dept.getDeptName());
			stmt.setInt(3, dept.getManagerId());
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				throw new DepartmentException("This department already exists.");
			} else if(!rs.next()) {
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, dept.getDeptId());
				stmt.setString(2, dept.getDeptName());
				stmt.setInt(3, dept.getManagerId());
				stmt.executeUpdate();
			}
			conn.commit();
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addEmployees(List<Employee> employees, Integer deptId) throws EmployeeException {
		String sql_employee = "INSERT INTO employee(employee_id, first_name, last_name, employment_date, dept_id, annual_salary) values (?,?,?,?,?,?)";
		String sql_dept = "SELECT dept_id FROM employee where dept_id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql_dept);
			ResultSet rs = null;
			for (Employee employee : employees) {
				stmt.setInt(1, employee.getEmployeeId());
				rs = stmt.executeQuery();
				if (rs.next()) {
					throw new EmployeeException("This employee already exists.");
				}
			}
			rs.close();
			stmt.close();
			stmt = conn.prepareStatement(sql_employee);
			for (Employee employee : employees) {
					stmt.setInt(1, employee.getEmployeeId());
					stmt.setString(2, employee.getFirstName());
					stmt.setString(3, employee.getLastName());
					stmt.setDate(4, new java.sql.Date(employee.getEmploymentDate().getTime()));
					if (deptId == null) {
						stmt.setNull(5, Types.INTEGER);
					} else {
						stmt.setInt(5, deptId);
					}
					stmt.setDouble(6, employee.getAnnualSalary());
					stmt.addBatch();
				}	
			stmt.executeBatch();
			conn.commit();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateEmployee(Employee employee) throws EmployeeException {
		String sql = "UPDATE employee SET first_name = ?, last_name = ?, employment_date = ?, dept_id = ?, annual_salary = ? WHERE employee_id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, employee.getFirstName());
			stmt.setString(2, employee.getLastName());
			stmt.setDate(3, new java.sql.Date(employee.getEmploymentDate().getTime()));
			stmt.setInt(4, employee.getDept().getDeptId());
			stmt.setDouble(5, employee.getAnnualSalary());
			stmt.setInt(6, employee.getEmployeeId());
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
		} catch (SQLException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteEmployee(Employee employee) throws EmployeeException {
		String sql = "DELETE FROM employee WHERE employee_id = ?";
		String check = "SELECT employee_id from employee WHERE employee_id = ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(check);
			stmt.setInt(1, employee.getEmployeeId());
			ResultSet rs = stmt.executeQuery();
			if (!rs.next()) {
				throw new EmployeeException("This employee does not exist.");
			} else {
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, employee.getEmployeeId());
				stmt.executeUpdate();
			}
			conn.commit();
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Employee> getEmployees(Set<Integer> employeeIds) {
		String sql = "SELECT employee_id, first_name, last_name, employment_date, dept_id, annual_salary FROM employee";
		String dept = "SELECT dept_id, dept_name, manager_id FROM department WHERE dept_id = ?";
		List<Employee> employeeList = new ArrayList<>();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int curID = rs.getInt(1);
				if (employeeIds.contains(curID)) {
					String curFirstName = rs.getString(2);
					String curLastName = rs.getString(3);
					Date curEmploymentDate = rs.getDate(4);
					int curDeptId = rs.getInt(5);
					PreparedStatement stmt2 = conn.prepareStatement(dept);
					stmt2.setInt(1, curDeptId);
					ResultSet rs2 = stmt2.executeQuery();
					String curDepName = null;
					int curManId = 0;
					while (rs2.next()) {
						stmt2.setInt(1, curDeptId);
						curDepName = rs2.getString(2);
						curManId = rs2.getInt(3);
					}
					double curAnnualSalary = rs.getDouble(6);
					Employee curEmployee = new Employee(curID, curFirstName, curLastName, curEmploymentDate, curAnnualSalary);
					Department curDept = new Department(curDeptId, curDepName, curManId);
					curEmployee.setDept(curDept);
					employeeList.add(curEmployee);
					conn.commit();
					stmt2.close();
					rs2.close();
				}
			}
			conn.commit();
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return employeeList;
	}
	
	public void setAverageSalaries(List<Department> departments) {
		String sql = "SELECT dept_id, avg(annual_salary) FROM employee GROUP BY dept_id";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				for (Department department : departments) {
					if (department.getDeptId() == rs.getInt(1)) {
						department.setAverageSalary(rs.getDouble(2));
					}
				}
			}
			conn.commit();
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Employee> getSuperiors(Employee employee) {
		Set<Integer> superiors_temp = new HashSet<Integer>();
		Set<Integer> superiors = new HashSet<Integer>();
		int manager = (employee.getDept().getManagerId());
		superiors_temp.add(manager);
		superiors.add(manager);
		List<Employee> curEmp = getEmployees(superiors);
		while (curEmp.get(0).getDept() != null && curEmp.get(0).getDept().getManagerId() != curEmp.get(0).getEmployeeId()) {
			superiors_temp.remove(manager);
			manager = curEmp.get(0).getDept().getManagerId();
			superiors_temp.add(manager);
			curEmp = getEmployees(superiors_temp);
			superiors.add(manager);
			}
		return getEmployees(superiors);
		}
	
	public Document getEmployeeInfo(Set<Integer> employeeIds) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("Company");
		List<Employee> list = getEmployees(employeeIds);
		for (Employee emp : list) {
			Element employee = root.addElement("Employee").addAttribute("employee_id", String.valueOf(emp.getEmployeeId()));
			employee.addElement("first_name").setText(emp.getFirstName());
			employee.addElement("last_name").setText(emp.getLastName());
			DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			employee.addElement("employment_date").setText(date.format(emp.getEmploymentDate()));
			employee.addElement("dept_id").setText(String.valueOf(emp.getDept().getDeptId()));
			employee.addElement("annual_salary").setText(String.valueOf((emp.getAnnualSalary())));
		}
		for (Employee emp : list) {
			Element department = root.addElement("Department").addAttribute("dept_id", String.valueOf(emp.getDept().getDeptId()));
			department.addElement("dept_name").setText(emp.getDept().getDeptName());
			department.addElement("manager_id").setText(String.valueOf((emp.getDept().getManagerId())));
		}
		return document;
	}
	
	public boolean isValid(String xmlDataFileName, String xmlSchemaFileName) throws Exception {
		try {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		factory.setSchema(schemaFactory.newSchema(
				new Source[] {new StreamSource(xmlSchemaFileName)}));
		factory.setValidating(true);
		
		SAXParser parser = factory.newSAXParser();
		SAXReader reader = new SAXReader(parser.getXMLReader());
		reader.setErrorHandler(new ErrorHandler() {

			@Override
			public void error(SAXParseException exception) throws SAXException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void fatalError(SAXParseException exception) throws SAXException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void warning(SAXParseException exception) throws SAXException {
				// TODO Auto-generated method stub
				
			}});
		reader.read(xmlDataFileName);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public List<Employee> getEmployees(Document doc, String mgr, double minSalary) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		XPathFactory xpathfactory = XPathFactory.newInstance();
		XPath xpath = xpathfactory.newXPath();
		Element root = doc.getRootElement();
		List<Element> companyElements = root.elements();
		List<String> names = null;
		for (Element companyElement : companyElements) {
			names.add((companyElement.element("first_name").getStringValue()) + (companyElement.element("last_name").getStringValue()));
		}
		XPathExpression expr = xpath.compile("//employee[annual_salary>minSalary]/employee_id/text()");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		for (int i = 0; i < nodes.getLength(); i++) {
			nodes.item(i).getNodeValue();
		}
		return null;
	}

		public static void main(String [] args) throws Exception {
	        Class.forName("org.postgresql.Driver");
	        String url = "jdbc:postgresql://localhost:1234/postgres";
	        Connection conn =  DriverManager.getConnection(url,"postgres", "password");
	        EmployeeDAO dao = new EmployeeDAO(conn);
	        Set<Integer> ids = new HashSet<Integer>(7);
	        for (int i=1; i<7; i++) {
	        	ids.add(i);
	        }
	        Document test = dao.getEmployeeInfo(ids);
	        OutputFormat format = OutputFormat.createPrettyPrint();
	        XMLWriter writer = new XMLWriter(new FileWriter("test.xml"), format);
	        writer.write(test);
	        writer.close();
	        
	        System.out.println(dao.isValid("test.xml", "employees.xsd"));
	        conn.close();
	}
}

