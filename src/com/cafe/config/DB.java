package com.cafe.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class DB {

    private static final String CONFIG_FILE = "config.properties";

    // Método para obtener la conexión
    public static Connection getConnection() {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(CONFIG_FILE));

            String url = props.getProperty("DB_URL");
            String user = props.getProperty("DB_USER");
            String pass = props.getProperty("DB_PASS");

            return DriverManager.getConnection(url, user, pass);

        } catch (IOException e) {
            throw new RuntimeException("Error loading database configuration", e);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    // Test rápido de conexión
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("✅ Connected: " + (conn != null && !conn.isClosed()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
