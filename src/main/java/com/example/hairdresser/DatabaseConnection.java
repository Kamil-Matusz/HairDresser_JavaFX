package com.example.hairdresser;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static Connection connectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql//localhost:3306/hairdresser","root","ZAQ!2wsx");
            return connection;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
