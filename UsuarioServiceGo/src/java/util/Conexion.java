/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/rurago_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin117";  
    
    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("✓ Driver PostgreSQL cargado");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar driver PostgreSQL", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Método de prueba
    public static void main(String[] args) {
        try {
            Connection conn = Conexion.getConnection();
            System.out.println("✓ Conexión exitosa a PostgreSQL!");
            Conexion.closeConnection(conn);
        } catch (SQLException e) {
            System.out.println("✗ Error de conexión:");
            e.printStackTrace();
        }
    }
}

