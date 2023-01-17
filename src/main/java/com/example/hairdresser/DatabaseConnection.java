package com.example.hairdresser;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Class which contains handling to the database
 */
public class DatabaseConnection {
    /**
     * Method which creating database connection
     * @return database connection lub null on situation when database in not found or connection failed
     */
    public static Connection connectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hairdresser", "root", "ZAQ!2wsx");
            return connection;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
