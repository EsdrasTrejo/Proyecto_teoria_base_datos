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
import modelo.Presupuesto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Usuario
 */
public class PresupuestoDAO {

    public boolean registrarPresupuesto(int idUsuario, String nombre,
                                        int anioPeriodoInicio, int mesPeriodoInicio,
                                        int anioPeriodoFin, int mesPeriodoFin,
                                        String creadoPor) {
        String sql = "{call sp_ingresar_presupuesto(?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idUsuario);
            cs.setString(2, nombre);
            cs.setInt(3, anioPeriodoInicio);
            cs.setInt(4, mesPeriodoInicio);
            cs.setInt(5, anioPeriodoFin);
            cs.setInt(6, mesPeriodoFin);
            cs.setString(7, creadoPor);

            cs.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Error al registrar presupuesto: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarPresupuesto(int idPresupuesto, String nombre,
                                         int anioPeriodoInicio, int mesPeriodoInicio,
                                         int anioPeriodoFin, int mesPeriodoFin,
                                         String modificadoPor) {
        String sql = "{call sp_actualizar_presupuesto(?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idPresupuesto);
            cs.setString(2, nombre);
            cs.setInt(3, anioPeriodoInicio);
            cs.setInt(4, mesPeriodoInicio);
            cs.setInt(5, anioPeriodoFin);
            cs.setInt(6, mesPeriodoFin);
            cs.setString(7, modificadoPor);

            cs.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Error al actualizar presupuesto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPresupuesto(int idPresupuesto) {
        String sql = "{call sp_eliminar_presupuesto(?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idPresupuesto);
            cs.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Error al eliminar presupuesto: " + e.getMessage());
            return false;
        }
    }

    public Presupuesto consultarPresupuesto(int idPresupuesto) {
        String sql = "{call sp_consultar_presupuesto(?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idPresupuesto);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                Presupuesto p = new Presupuesto();
                p.setIdPresupuesto(rs.getInt("id_presupuesto"));
                p.setIdUsuario(rs.getInt("id_usuario"));
                p.setNombre(rs.getString("nombre"));
                p.setAnioPeriodoInicio(rs.getInt("year_inicio"));
                p.setMesPeriodoInicio(rs.getInt("mes_inicio"));
                p.setAnioPeriodoFin(rs.getInt("year_fin"));
                p.setMesPeriodoFin(rs.getInt("mes_fin"));

                try {
                    p.setEstado(rs.getBoolean("estado"));
                } catch (Exception e) {
                }

                return p;
            }

        } catch (Exception e) {
            System.out.println("Error al consultar presupuesto: " + e.getMessage());
        }

        return null;
    }

    public ArrayList<Presupuesto> listarPresupuestos(int idUsuario) {
    ArrayList<Presupuesto> lista = new ArrayList<>();
    String sql = "{call sp_listar_presupuesto(?)}";

    try (Connection conn = ConexionBD.getConnection();
         CallableStatement cs = conn.prepareCall(sql)) {

        cs.setInt(1, idUsuario);
        ResultSet rs = cs.executeQuery();

        while (rs.next()) {
            Presupuesto p = new Presupuesto();
            p.setIdPresupuesto(rs.getInt("id_presupuesto"));
            p.setNombre(rs.getString("nombre"));
            lista.add(p);
        }

    } catch (Exception e) {
        System.out.println("Error al listar presupuestos: " + e.getMessage());
    }

    return lista;
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
      
      public double[] calcularBalanceMensual(
        int idUsuario,
        int idPresupuesto,
        int anio,
        int mes
) {
    String sql = "{call sp_calcular_balance_mensual(?,?,?,?,?,?,?,?)}";
    double[] resultado = new double[4];

    try (Connection conn = ConexionBD.getConnection();
         CallableStatement cs = conn.prepareCall(sql)) {

        cs.setInt(1, idUsuario);
        cs.setInt(2, idPresupuesto);
        cs.setInt(3, anio);
        cs.setInt(4, mes);

        cs.registerOutParameter(5, java.sql.Types.DECIMAL); // total ingresos
        cs.registerOutParameter(6, java.sql.Types.DECIMAL); // total gastos
        cs.registerOutParameter(7, java.sql.Types.DECIMAL); // total ahorros
        cs.registerOutParameter(8, java.sql.Types.DECIMAL); // balance final

        cs.execute();

        resultado[0] = cs.getDouble(5);
        resultado[1] = cs.getDouble(6);
        resultado[2] = cs.getDouble(7);
        resultado[3] = cs.getDouble(8);

    } catch (Exception e) {
        System.out.println("Error al calcular balance mensual: " + e.getMessage());
    }

    return resultado;
}
      public double[] obtenerResumenCategoriaMes(
        int idCategoria,
        int idPresupuesto,
        int anio,
        int mes
) {
    String sql = "{call sp_obtener_resumen_categoria_mes(?,?,?,?,?,?,?)}";
    double[] resultado = new double[3];

    try (Connection conn = ConexionBD.getConnection();
         CallableStatement cs = conn.prepareCall(sql)) {

        cs.setInt(1, idCategoria);
        cs.setInt(2, idPresupuesto);
        cs.setInt(3, anio);
        cs.setInt(4, mes);

        cs.registerOutParameter(5, java.sql.Types.DECIMAL); // presupuestado
        cs.registerOutParameter(6, java.sql.Types.DECIMAL); // ejecutado
        cs.registerOutParameter(7, java.sql.Types.DECIMAL); // porcentaje

        cs.execute();

        resultado[0] = cs.getDouble(5);
        resultado[1] = cs.getDouble(6);
        resultado[2] = cs.getDouble(7);

    } catch (Exception e) {
        System.out.println("Error al obtener resumen de categoria: " + e.getMessage());
    }

    return resultado;
}
      
      public boolean cerrarPresupuesto(
        int idPresupuesto,
        String modificadoPor
) {
    String sql = "{call sp_cerrar_presupuesto(?,?)}";

    try (Connection conn = ConexionBD.getConnection();
         CallableStatement cs = conn.prepareCall(sql)) {

        cs.setInt(1, idPresupuesto);
        cs.setString(2, modificadoPor);

        cs.executeUpdate();

        return true;

    } catch (Exception e) {
        System.out.println("Error al cerrar presupuesto: " + e.getMessage());
        return false;
    }
}
      public boolean crearPresupuestoCompleto(
        int idUsuario,
        String nombre,
        String descripcion,
        int yearInicio,
        int mesInicio,
        int yearFin,
        int mesFin,
        String listaSubcategoriasJson,
        String creadoPor
) {
    String sql = "{call sp_crear_presupuesto_completo(?,?,?,?,?,?,?,?,?)}";

    try (Connection conn = ConexionBD.getConnection();
         CallableStatement cs = conn.prepareCall(sql)) {

        cs.setInt(1, idUsuario);
        cs.setString(2, nombre);
        cs.setString(3, descripcion);
        cs.setInt(4, yearInicio);
        cs.setInt(5, mesInicio);
        cs.setInt(6, yearFin);
        cs.setInt(7, mesFin);
        cs.setString(8, listaSubcategoriasJson);
        cs.setString(9, creadoPor);

        cs.executeUpdate();

        return true;

    } catch (Exception e) {
        System.out.println("Error al crear presupuesto completo: " + e.getMessage());
        return false;
    }
}
      
      public double calcularMontoEjecutado(
        int idSubcategoria,
        int anio,
        int mes
) {
    String sql = "SELECT fn_calcular_monto_ejecutado(?,?,?)";

    double monto = 0;

    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idSubcategoria);
        ps.setInt(2, anio);
        ps.setInt(3, mes);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            monto = rs.getDouble(1);
        }

    } catch (Exception e) {
        System.out.println("Error al calcular monto ejecutado (fn): " + e.getMessage());
    }

    return monto;
}
     public boolean validarVigenciaPresupuesto(
        java.sql.Date fecha,
        int idPresupuesto
) {
    String sql = "SELECT fn_validar_vigencia_presupuesto(?,?)";

    boolean vigente = false;

    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setDate(1, fecha);
        ps.setInt(2, idPresupuesto);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                vigente = rs.getBoolean(1);
            }
        }

    } catch (SQLException e) {
        System.out.println("Error al validar vigencia del presupuesto: " + e.getMessage());
    }

    return vigente;
}
   
}