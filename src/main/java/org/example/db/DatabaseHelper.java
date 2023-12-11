package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseHelper {

    public Connection connection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.DB_USER, DatabaseUtils.DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException exception) {
            throw new RuntimeException(exception);
        }
        return null;
    }
}









