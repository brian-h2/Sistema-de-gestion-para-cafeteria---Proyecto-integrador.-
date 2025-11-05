package com.cafe.config;

import java.sql.*;

public class DB {
    // Configuración de la conexión a la base de datos
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/cafeteria?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";
        String pass = "BriVlogs12";
        try {
            //DriverManager es una clase que gestiona las conexiones a bases de datos
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public static void main(String[] args) {
        // Test simple para la conexion
        try (Connection conn = getConnection()) {
            System.out.println("Connected: " + (conn != null && !conn.isClosed()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
