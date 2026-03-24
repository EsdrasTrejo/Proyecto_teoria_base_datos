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
import java.util.Date;
import modelo.Transaccion;
import java.sql.PreparedStatement;
/**
 *
 * @author Usuario
 */

public class TransaccionDAO {

    public void insertarTransaccion(Transaccion t, String creadoPor) throws Exception {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = ConexionBD.getConnection();
            cs = con.prepareCall("{call sp_insertar_transaccion(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

            cs.setInt(1, t.getIdUsuario());
            cs.setInt(2, t.getIdPresupuesto());
            cs.setInt(3, t.getAnio());
            cs.setInt(4, t.getMes());
            cs.setInt(5, t.getIdSubcategoria());
            cs.setInt(6, t.getIdObligacion());
            cs.setString(7, t.getTipo());
            cs.setString(8, t.getDescripcion());
            cs.setDouble(9, t.getMonto());
            cs.setDate(10, t.getFecha());
            cs.setString(11, t.getMetodoPago());
            cs.setString(12, t.getNumFactura());
            cs.setString(13, t.getObservaciones());
            cs.setString(14, creadoPor);

            cs.execute();
        } finally {
            if (cs != null) {
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public void actualizarTransaccion(Transaccion t, String modificadoPor) throws Exception {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = ConexionBD.getConnection();
            cs = con.prepareCall("{call sp_actualizar_transaccion(?,?,?,?,?,?,?,?,?,?)}");

            cs.setInt(1, t.getIdTransaccion());
            cs.setInt(2, t.getAnio());
            cs.setInt(3, t.getMes());
            cs.setString(4, t.getDescripcion());
            cs.setDouble(5, t.getMonto());
            cs.setDate(6, t.getFecha());
            cs.setString(7, t.getMetodoPago());
            cs.setString(8, t.getNumFactura());
            cs.setString(9, t.getObservaciones());
            cs.setString(10, modificadoPor);

            cs.execute();
        } finally {
            if (cs != null) {
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public void eliminarTransaccion(int idTransaccion) throws Exception {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = ConexionBD.getConnection();
            cs = con.prepareCall("{call sp_eliminar_transaccion(?)}");

            cs.setInt(1, idTransaccion);

            cs.execute();
        } finally {
            if (cs != null) {
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public Transaccion consultarTransaccion(int idTransaccion) throws Exception {
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        Transaccion t = null;

        try {
            con = ConexionBD.getConnection();
            cs = con.prepareCall("{call sp_consultar_transaccion(?)}");

            cs.setInt(1, idTransaccion);

            rs = cs.executeQuery();

            if (rs.next()) {
                t = new Transaccion();
                t.setIdTransaccion(rs.getInt("id_transaccion"));
                t.setIdUsuario(rs.getInt("id_usuario"));
                t.setIdPresupuesto(rs.getInt("id_presupuesto"));
                t.setAnio(rs.getInt("anio"));
                t.setMes(rs.getInt("mes"));
                t.setIdSubcategoria(rs.getInt("id_subcategoria"));
                t.setIdObligacion(rs.getInt("id_obligacion"));
                t.setTipo(rs.getString("tipo"));
                t.setDescripcion(rs.getString("descripcion"));
                t.setMonto(rs.getDouble("monto"));
                t.setFecha(rs.getDate("fecha"));
                t.setMetodoPago(rs.getString("metodo_pago"));
                t.setNumFactura(rs.getString("num_factura"));
                t.setObservaciones(rs.getString("observaciones"));
            }

            return t;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (cs != null) {
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public List<Transaccion> listarTransacciones(int idPresupuesto) throws Exception {
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Transaccion> lista = new ArrayList<>();

        try {
            con = ConexionBD.getConnection();
            cs = con.prepareCall("{call sp_listar_transaccion(?)}");

            cs.setInt(1, idPresupuesto);

            rs = cs.executeQuery();

            while (rs.next()) {
                Transaccion t = new Transaccion();
                t.setIdTransaccion(rs.getInt("id_transaccion"));
                t.setDescripcion(rs.getString("descripcion"));
                lista.add(t);
            }

            return lista;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (cs != null) {
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public boolean registrarTransaccionCompleta(
        int idUsuario,
        int idPresupuesto,
        int anio,
        int mes,
        int idSubcategoria,
        String tipo,
        String descripcion,
        double monto,
        Date fecha,
        String metodoPago,
        String creadoPor
) {
    String sql = "{call sp_registrar_transaccion_completa(?,?,?,?,?,?,?,?,?,?,?)}";

    try (Connection conn = ConexionBD.getConnection();
         CallableStatement cs = conn.prepareCall(sql)) {

        cs.setInt(1, idUsuario);
        cs.setInt(2, idPresupuesto);
        cs.setInt(3, anio);
        cs.setInt(4, mes);
        cs.setInt(5, idSubcategoria);
        cs.setString(6, tipo);
        cs.setString(7, descripcion);
        cs.setDouble(8, monto);
        cs.setDate(9, new java.sql.Date(fecha.getTime()));
        cs.setString(10, metodoPago);
        cs.setString(11, creadoPor);

        cs.executeUpdate();

        return true;

    } catch (Exception e) {
        System.out.println("Error al registrar transacción: " + e.getMessage());
        return false;
    }
}
    public double calcularMontoEjecutadoMes(
        int idSubcategoria,
        int idPresupuesto,
        int anio,
        int mes
) {
    String sql = "{call sp_calcular_monto_ejecutado_mes(?,?,?,?,?)}";

    double monto = 0;

    try (Connection conn = ConexionBD.getConnection();
         CallableStatement cs = conn.prepareCall(sql)) {

        cs.setInt(1, idSubcategoria);
        cs.setInt(2, idPresupuesto);
        cs.setInt(3, anio);
        cs.setInt(4, mes);

        // OUT parameter
        cs.registerOutParameter(5, java.sql.Types.DECIMAL);

        cs.execute();

        monto = cs.getDouble(5);

    } catch (Exception e) {
        System.out.println("Error al calcular monto ejecutado: " + e.getMessage());
    }

    return monto;
}
  public double calcularPorcentajeEjecucionMes(
        int idSubcategoria,
        int idPresupuesto,
        int anio,
        int mes
) {
    String sql = "{call sp_calcular_porcentaje_ejecucion_mes(?,?,?,?,?)}";

    double porcentaje = 0;

    try (Connection conn = ConexionBD.getConnection();
         CallableStatement cs = conn.prepareCall(sql)) {

        cs.setInt(1, idSubcategoria);
        cs.setInt(2, idPresupuesto);
        cs.setInt(3, anio);
        cs.setInt(4, mes);

        // OUT parameter
        cs.registerOutParameter(5, java.sql.Types.DECIMAL);

        cs.execute();

        porcentaje = cs.getDouble(5);

    } catch (Exception e) {
        System.out.println("Error al calcular porcentaje de ejecucion: " + e.getMessage());
    }

    return porcentaje;
}  
  
  public double calcularPorcentajeEjecutadoFN(
        int idSubcategoria,
        int idPresupuesto,
        int anio,
        int mes
) {
    String sql = "SELECT fn_calcular_porcentaje_ejecutado(?,?,?,?)";

    double porcentaje = 0;

    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idSubcategoria);
        ps.setInt(2, idPresupuesto);
        ps.setInt(3, anio);
        ps.setInt(4, mes);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            porcentaje = rs.getDouble(1);
        }

    } catch (Exception e) {
        System.out.println("Error al calcular porcentaje (fn): " + e.getMessage());
    }

    return porcentaje;
}
    
}