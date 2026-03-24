/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import Conexion.ConexionBD;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelo.Categoria;
/**
 *
 * @author Usuario
 */
public class CategoriaDAO {

    public Categoria consultarCategoria(int idCategoria) {
        String sql = "{call sp_consultar_categoria(?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idCategoria);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNombre(rs.getString("nombre"));
                categoria.setDescripcion(rs.getString("descripcion"));
                categoria.setTipo(rs.getString("tipo"));
                categoria.setIdUsuarioPropietario(rs.getInt("id_usuario_propetario"));
                
                return categoria;
            }

        } catch (Exception e) {
            System.out.println("Error al consultar categoria: " + e.getMessage());
        }

        return null;
    }

    public ArrayList<Categoria> listarCategorias(int idUsuario, String tipo) {
        ArrayList<Categoria> lista = new ArrayList<>();
        String sql = "{call sp_listar_categoria(?, ?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idUsuario);
            cs.setString(2, tipo);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setTipo(rs.getString("tipo"));
                lista.add(categoria);
            }

        } catch (Exception e) {
            System.out.println("Error al listar categorias: " + e.getMessage());
        }

        return lista;
    }

    public boolean registrarCategoria(String nombre, String descripcion, String tipo, String creadoPor, int idUsuario) {
        String sql = "{call sp_insertar_categoria(?, ?, ?, ?, ?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, nombre);
            cs.setString(2, descripcion);
            cs.setString(3, tipo);
            cs.setString(4, creadoPor);
            cs.setInt(5, idUsuario);

            cs.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Error al registrar categoria: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarCategoria(int idCategoria, String nombre, String descripcion, String modificadoPor) {
        String sql = "{call sp_actualizar_categoria(?, ?, ?, ?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idCategoria);
            cs.setString(2, nombre);
            cs.setString(3, descripcion);
            cs.setString(4, modificadoPor);

            cs.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Error al actualizar categoria: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarCategoria(int idCategoria) {
        String sql = "{call sp_eliminar_categoria(?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idCategoria);

            cs.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Error al eliminar categoria: " + e.getMessage());
            return false;
        }
    }
    
    public double obtenerTotalCategoriaMes(
        int idCategoria,
        int idPresupuesto,
        int anio,
        int mes
) {
    String sql = "SELECT fn_obtener_total_categoria_mes(?,?,?,?)";
    double total = 0;

    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idCategoria);
        ps.setInt(2, idPresupuesto);
        ps.setInt(3, anio);
        ps.setInt(4, mes);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        }

    } catch (Exception e) {
        System.out.println("Error al obtener total de categoria del mes: " + e.getMessage());
    }

    return total;
}
    
    public double obtenerTotalEjecutadoCategoriaMes(
        int idCategoria,
        int anio,
        int mes
) {
    String sql = "SELECT fn_obtener_total_ejecutado_categoria_mes(?,?,?)";

    double total = 0;

    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idCategoria);
        ps.setInt(2, anio);
        ps.setInt(3, mes);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        }

    } catch (Exception e) {
        System.out.println("Error al obtener total ejecutado de categoria: " + e.getMessage());
    }

    return total;
}
}
