package com.employee.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/employee_management?serverTimezone=UTC";
    private static final String USER = "root";
    // Change this to the password configured for your local MySQL account.
    private static final String PASSWORD = "root";

    private DatabaseConnection() { }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
