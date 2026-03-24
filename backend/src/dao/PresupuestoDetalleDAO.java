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
import modelo.PresupuestoDetalle;

/**
 *
 * @author Usuario
 */
public class PresupuestoDetalleDAO {

    public boolean registrarPresupuestoDetalle(int idPresupuesto, int idSubcategoria, double montoMensual, String observaciones, String creadoPor) {
        String sql = "{call sp_insertar_presupuesto_detalle(?, ?, ?, ?, ?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idPresupuesto);
            cs.setInt(2, idSubcategoria);
            cs.setDouble(3, montoMensual);
            cs.setString(4, observaciones);
            cs.setString(5, creadoPor);

            cs.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Error al registrar presupuesto detalle: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarPresupuestoDetalle(int idDetalle, double montoMensual, String observaciones, String modificadoPor) {
        String sql = "{call sp_actualizar_presupuesto_detalle(?, ?, ?, ?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idDetalle);
            cs.setDouble(2, montoMensual);
            cs.setString(3, observaciones);
            cs.setString(4, modificadoPor);

            cs.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Error al actualizar presupuesto detalle: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPresupuestoDetalle(int idDetalle) {
        String sql = "{call sp_eliminar_presupuesto_detalle(?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idDetalle);
            cs.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Error al eliminar presupuesto detalle: " + e.getMessage());
            return false;
        }
    }

    public PresupuestoDetalle consultarPresupuestoDetalle(int idDetalle) {
        String sql = "{call sp_consultar_presupuesto_detalle(?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idDetalle);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                PresupuestoDetalle pd = new PresupuestoDetalle();
                pd.setIdDetalle(rs.getInt("id_presupuesto_detalle"));
                pd.setIdPresupuesto(rs.getInt("id_presupuesto"));
                pd.setIdSubcategoria(rs.getInt("id_subcategoria"));
                pd.setMontoMensual(rs.getDouble("monto_mensual"));
                pd.setObservaciones(rs.getString("observaciones"));

                try {
                    pd.setEstado(rs.getBoolean("estado"));
                } catch (Exception e) {
                }

                return pd;
            }

        } catch (Exception e) {
            System.out.println("Error al consultar presupuesto detalle: " + e.getMessage());
        }

        return null;
    }

    public ArrayList<PresupuestoDetalle> listarPresupuestoDetalle(int idPresupuesto) {
        ArrayList<PresupuestoDetalle> lista = new ArrayList<>();
        String sql = "{call sp_listar_presupuesto_detalle(?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idPresupuesto);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                PresupuestoDetalle pd = new PresupuestoDetalle();

                try {
                    pd.setIdDetalle(rs.getInt("id_presupuesto_detalle"));
                } catch (Exception e) {
                }

                try {
                    pd.setIdSubcategoria(rs.getInt("id_subcategoria"));
                } catch (Exception e) {
                }

                try {
                    pd.setMontoMensual(rs.getDouble("monto_mensual"));
                } catch (Exception e) {
                }

                try {
                    pd.setObservaciones(rs.getString("observaciones"));
                } catch (Exception e) {
                }

                lista.add(pd);
            }

        } catch (Exception e) {
            System.out.println("Error al listar presupuesto detalle: " + e.getMessage());
        }

        return lista;
    }
}