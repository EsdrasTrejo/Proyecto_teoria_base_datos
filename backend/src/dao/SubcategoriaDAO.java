/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import Conexion.ConexionBD;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelo.Subcategoria;
import java.sql.PreparedStatement;
/**
 *
 * @author Usuario
 */
public class SubcategoriaDAO {

    public boolean registrarSubcategoria(int idCategoria, String nombre, String descripcion, int esDefecto, String creadoPor) {
        String sql = "{call sp_insertar_subcategoria(?, ?, ?, ?, ?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idCategoria);
            cs.setString(2, nombre);
            cs.setString(3, descripcion);
            cs.setInt(4, esDefecto);
            cs.setString(5, creadoPor);

            cs.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Error al registrar subcategoria: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarSubcategoria(int idSubcategoria, String nombre, String descripcion, String modificadoPor) {
        String sql = "{call sp_actualizar_subcategoria(?, ?, ?, ?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idSubcategoria);
            cs.setString(2, nombre);
            cs.setString(3, descripcion);
            cs.setString(4, modificadoPor);

            cs.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Error al actualizar subcategoria: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarSubcategoria(int idSubcategoria) {
        String sql = "{call sp_eliminar_subcategoria(?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idSubcategoria);

            cs.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Error al eliminar subcategoria: " + e.getMessage());
            return false;
        }
    }

    public Subcategoria consultarSubcategoria(int idSubcategoria) {
        String sql = "{call sp_consultar_subcategoria(?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idSubcategoria);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                Subcategoria s = new Subcategoria();
                s.setIdSubcategoria(rs.getInt("id_subcategoria"));
                s.setIdCategoria(rs.getInt("id_categoria"));
                s.setNombre(rs.getString("nombre"));
                s.setDescripcion(rs.getString("descripcion"));
                s.setEsDefecto(rs.getBoolean("es_defecto"));
                s.setEstado(rs.getBoolean("indicador_activa"));
                return s;
            }

        } catch (Exception e) {
            System.out.println("Error al consultar subcategoria: " + e.getMessage());
        }

        return null;
    }

    public ArrayList<Subcategoria> listarSubcategorias(int idCategoria) {
        ArrayList<Subcategoria> lista = new ArrayList<>();
        String sql = "{call sp_listar_subcategoria(?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idCategoria);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Subcategoria s = new Subcategoria();
                s.setIdSubcategoria(rs.getInt("id_subcategoria"));
                s.setNombre(rs.getString("nombre"));
                lista.add(s);
            }

        } catch (Exception e) {
            System.out.println("Error al listar subcategorias: " + e.getMessage());
        }

        return lista;
    }
    
    public double obtenerBalanceSubcategoria(
        int idPresupuesto,
        int idSubcategoria,
        int anio,
        int mes
) {
    String sql = "SELECT fn_obtener_balance_subcategoria(?,?,?,?)";

    double balance = 0;

    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idPresupuesto);
        ps.setInt(2, idSubcategoria);
        ps.setInt(3, anio);
        ps.setInt(4, mes);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            balance = rs.getDouble(1);
        }

    } catch (Exception e) {
        System.out.println("Error al obtener balance de subcategoria: " + e.getMessage());
    }

    return balance;
}
    public int obtenerCategoriaPorSubcategoria(int idSubcategoria) {
    String sql = "SELECT fn_obtener_categoria_por_subcategoria(?)";

    int idCategoria = 0;

    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idSubcategoria);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                idCategoria = rs.getInt(1);
            }
        }

    } catch (Exception e) {
        System.out.println("Error al obtener categoria por subcategoria: " + e.getMessage());
    }

    return idCategoria;
}
    
    public double calcularProyeccionGastoMensual(
        int idSubcategoria,
        int anio,
        int mes
) {
    String sql = "SELECT fn_calcular_proyeccion_gasto_mensual(?,?,?)";

    double proyeccion = 0;

    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idSubcategoria);
        ps.setInt(2, anio);
        ps.setInt(3, mes);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                proyeccion = rs.getDouble(1);
            }
        }

    } catch (Exception e) {
        System.out.println("Error al calcular proyeccion de gasto mensual: " + e.getMessage());
    }

    return proyeccion;
}
    
    public double obtenerPromedioGastoSubcategoria(
        int idUsuario,
        int idSubcategoria,
        int cantidadMeses
) {
    String sql = "SELECT fn_obtener_promedio_gasto_subcategoria(?,?,?)";

    double promedio = 0;

    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idUsuario);
        ps.setInt(2, idSubcategoria);
        ps.setInt(3, cantidadMeses);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                promedio = rs.getDouble(1);
            }
        }

    } catch (Exception e) {
        System.out.println("Error al obtener promedio de gasto: " + e.getMessage());
    }

    return promedio;
}
    
}