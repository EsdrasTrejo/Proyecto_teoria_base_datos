/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Usuario
 */
public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/presupuesto_personal_proyecto?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    // 🔹 Método para obtener conexión
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 🔹 Método para probar conexión
    public static void probarConexion() {
        try (Connection conn = getConnection()) {
            System.out.println("Conexion exitosa ");
        } catch (Exception e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }
}