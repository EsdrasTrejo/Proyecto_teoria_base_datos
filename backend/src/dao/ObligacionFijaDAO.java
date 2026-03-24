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
import java.util.List;
import modelo.ObligacionFija;
import java.sql.PreparedStatement;
/**
 *
 * @author Usuario
 */

public class ObligacionFijaDAO {

    public void insertarObligacionFija(ObligacionFija o, String creadoPor) throws Exception {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = ConexionBD.getConnection();
            cs = con.prepareCall("{call sp_insertar_obligacion_fija(?,?,?,?,?,?,?,?,?)}");

            cs.setInt(1, o.getIdSubcategoria());
            cs.setString(2, o.getNombre());
            cs.setString(3, o.getDescripcion());
            cs.setDouble(4, o.getMonto());
            cs.setInt(5, o.getDiaVencimiento());
            cs.setDate(6, o.getFechaInicio());
            cs.setDate(7, o.getFechaFin());
            cs.setString(8, creadoPor);
            cs.setInt(9, o.getIdUsuario());

            cs.execute();
        } finally {
            if (cs != null) cs.close();
            if (con != null) con.close();
        }
    }

    public void actualizarObligacionFija(ObligacionFija o, String modificadoPor) throws Exception {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = ConexionBD.getConnection();
            cs = con.prepareCall("{call sp_actualizar_obligacion_fija(?,?,?,?,?,?,?,?)}");

            cs.setInt(1, o.getIdObligacion());
            cs.setString(2, o.getNombre());
            cs.setString(3, o.getDescripcion());
            cs.setDouble(4, o.getMonto());
            cs.setInt(5, o.getDiaVencimiento());
            cs.setDate(6, o.getFechaFin());
            cs.setInt(7, o.getActivo());
            cs.setString(8, modificadoPor);

            cs.execute();
        } finally {
            if (cs != null) cs.close();
            if (con != null) con.close();
        }
    }

    public void eliminarObligacionFija(int idObligacion, String modificadoPor) throws Exception {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = ConexionBD.getConnection();
            cs = con.prepareCall("{call sp_eliminar_obligacion(?,?)}");

            cs.setInt(1, idObligacion);
            cs.setString(2, modificadoPor);

            cs.execute();
        } finally {
            if (cs != null) cs.close();
            if (con != null) con.close();
        }
    }

    public ObligacionFija consultarObligacionFija(int idObligacion) throws Exception {
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        ObligacionFija o = null;

        try {
            con = ConexionBD.getConnection();
            cs = con.prepareCall("{call sp_consultar_obligacion_fija(?)}");
            cs.setInt(1, idObligacion);

            rs = cs.executeQuery();

            if (rs.next()) {
                o = new ObligacionFija();
                o.setIdObligacion(rs.getInt("id_obligacion"));
                o.setIdSubcategoria(rs.getInt("id_subcategoria"));
                o.setIdUsuario(rs.getInt("id_usuario"));
                o.setNombre(rs.getString("nombre"));
                o.setDescripcion(rs.getString("descripcion"));
                o.setMonto(rs.getDouble("monto_mensual"));
                o.setDiaVencimiento(rs.getInt("dia_vencimiento"));
                o.setFechaInicio(rs.getDate("fecha_inicio"));
                o.setFechaFin(rs.getDate("fecha_finalizacion"));
                
            }

            return o;
        } finally {
            if (rs != null) rs.close();
            if (cs != null) cs.close();
            if (con != null) con.close();
        }
    }
public boolean procesarObligacionesMes(
        int idUsuario,
        int anio,
        int mes,
        int idPresupuesto
) {
    String sql = "{call sp_procesar_obligaciones_mes(?,?,?,?)}";

    try (Connection conn = ConexionBD.getConnection();
         CallableStatement cs = conn.prepareCall(sql)) {

        cs.setInt(1, idUsuario);
        cs.setInt(2, anio);
        cs.setInt(3, mes);
        cs.setInt(4, idPresupuesto);

        cs.executeUpdate();
        return true;

    } catch (Exception e) {
        System.out.println("Error al procesar obligaciones del mes: " + e.getMessage());
        return false;
    }
}
    public List<ObligacionFija> listarObligacionesFijas(int idUsuario) throws Exception {
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        List<ObligacionFija> lista = new ArrayList<>();

        try {
            con = ConexionBD.getConnection();
            cs = con.prepareCall("{call sp_listar_obligacion_fija(?)}");
            cs.setInt(1, idUsuario);

            rs = cs.executeQuery();

            while (rs.next()) {
                ObligacionFija o = new ObligacionFija();
                o.setIdObligacion(rs.getInt("id_obligacion"));
                o.setNombre(rs.getString("nombre"));
                lista.add(o);
            }

            return lista;
        } finally {
            if (rs != null) rs.close();
            if (cs != null) cs.close();
            if (con != null) con.close();
        }
    }
    
    public int diasHastaVencimiento(int idObligacion) {
    String sql = "SELECT fn_dias_hasta_vencimiento(?)";

    int dias = 0;

    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idObligacion);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                dias = rs.getInt(1);
            }
        }

    } catch (Exception e) {
        System.out.println("Error al calcular dias hasta vencimiento: " + e.getMessage());
    }

    return dias;
}
}