package edu.stevens.cs442.assignments;

/**
 * Created by Ravi Varadarajan on 4/9/2018.
 */
public class EmployeeException extends Exception {

    public EmployeeException(String msg) {
        super(msg);
    }

    public EmployeeException(String msg, Throwable t) {
        super(msg, t);
    }

}
