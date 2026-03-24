/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import Conexion.ConexionBD;
import modelo.usuario;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



/**
 *
 * @author Usuario
 */
public class UsuarioDAO {
    
    
    public usuario iniciarSesion(String correo, String clave) {
    String sql = "select id_usuario, nombre, apellido, correo, clave, salario_mensual, estado "
               + "from usuario "
               + "where correo = ? and clave = ? and estado = 1";

    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, correo);
        ps.setString(2, clave);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            usuario usuario = new usuario();
            usuario.setIdUsuario(rs.getInt("id_usuario"));
            usuario.setNombre(rs.getString("nombre"));
            usuario.setApellido(rs.getString("apellido"));
            usuario.setCorreo(rs.getString("correo"));
            usuario.setClave(rs.getString("clave"));
            usuario.setSalarioMensual(rs.getDouble("salario_mensual"));
            usuario.setEstado(rs.getBoolean("estado"));
            return usuario;
        }

    } catch (Exception e) {
        System.out.println("Error al iniciar sesion: " + e.getMessage());
    }

    return null;
}
    public usuario buscarPorCorreo(String correo) {
        String sql = "select id_usuario, nombre, apellido, correo, salario_mensual, estado from usuario where correo = ? and estado = 1";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario usuario = new usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setSalarioMensual(rs.getDouble("salario_mensual"));
                usuario.setEstado(rs.getBoolean("estado"));
                return usuario;
            }

        } catch (Exception e) {
            System.out.println("Error al buscar usuario: " + e.getMessage());
        }

        return null;
    }

    public boolean existeCorreo(String correo) {
        String sql = "select count(*) from usuario where correo = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            System.out.println("Error al validar correo: " + e.getMessage());
        }

        return false;
    }

public boolean registrarUsuario(String nombre, String apellido, String correo, String clave, double salarioMensual, String creadoPor) {
    String sql = "{call sp_insertar_usuario(?, ?, ?, ?, ?, ?)}";

    try (Connection conn = ConexionBD.getConnection();
         CallableStatement cs = conn.prepareCall(sql)) {

        cs.setString(1, nombre);
        cs.setString(2, apellido);
        cs.setString(3, correo);
        cs.setDouble(4, salarioMensual);
        cs.setString(5, creadoPor);
        cs.setString(6, clave);

        cs.execute();
        return true;

    } catch (Exception e) {
        System.out.println("Error al registrar usuario: " + e.getMessage());
        return false;
    }
}
 
public boolean actualizarUsuario(int idUsuario, String nombre, String apellido, String correo, double salarioMensual, int estado, String clave, String modificadoPor) {
    String sql = "{call sp_actualizar_usuario(?, ?, ?, ?, ?, ?, ?, ?)}";

    try (Connection conn = ConexionBD.getConnection();
         CallableStatement cs = conn.prepareCall(sql)) {

        cs.setInt(1, idUsuario);
        cs.setString(2, nombre);
        cs.setString(3, apellido);
        cs.setString(4, correo);
        cs.setDouble(5, salarioMensual);
        cs.setInt(6, estado);
        cs.setString(7, clave);
        cs.setString(8, modificadoPor);

        cs.execute();
        return true;

    } catch (Exception e) {
        System.out.println("Error al actualizar usuario: " + e.getMessage());
        return false;
    }
}
 
public boolean eliminarUsuario(int idUsuario, String eliminadoPor) {
    String sql = "{call sp_eliminar_usuario(?, ?)}";

    try (Connection conn = ConexionBD.getConnection();
         CallableStatement cs = conn.prepareCall(sql)) {

        cs.setInt(1, idUsuario);
        cs.setString(2, eliminadoPor);

        cs.execute();
        return true;

    } catch (Exception e) {
        System.out.println("Error al eliminar usuario: " + e.getMessage());
        return false;
    }
}
}
