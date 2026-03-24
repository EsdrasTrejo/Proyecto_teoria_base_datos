/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import Conexion.ConexionBD;
import modelo.ReporteResumenMensual;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.ReporteDistribucionGastosCategoria;
import modelo.ReporteCumplimientoPresupuesto;
import modelo.ReporteTendenciaGastosCategoria;
import modelo.ReporteEstadoObligacionFija;
/**
 *
 * @author Usuario
 */
public class ReporteDAO {

    public List<ReporteResumenMensual> obtenerReporteResumenMensual(
            int idUsuario,
            int anioDesde,
            int mesDesde,
            int anioHasta,
            int mesHasta) {

        List<ReporteResumenMensual> lista = new ArrayList<>();
        String sql = "{call sp_reporte_resumen_mensual(?,?,?,?,?)}";

        try (Connection cn = ConexionBD.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {

            cs.setInt(1, idUsuario);
            cs.setInt(2, anioDesde);
            cs.setInt(3, mesDesde);
            cs.setInt(4, anioHasta);
            cs.setInt(5, mesHasta);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    ReporteResumenMensual item = new ReporteResumenMensual();
                    item.setAnio(rs.getInt("anio"));
                    item.setMes(rs.getInt("mes"));
                    item.setTotalIngresos(rs.getBigDecimal("total_ingresos"));
                    item.setTotalGastos(rs.getBigDecimal("total_gastos"));
                    item.setTotalAhorros(rs.getBigDecimal("total_ahorros"));
                    item.setBalanceFinal(rs.getBigDecimal("balance_final"));
                    lista.add(item);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el reporte: " + e.getMessage(), e);
        }

        return lista;
    }
    
    public List<ReporteDistribucionGastosCategoria> obtenerReporteDistribucionGastosCategoria(
        int idUsuario, int anio, int mes) {

    List<ReporteDistribucionGastosCategoria> lista = new ArrayList<>();
    String sql = "{call sp_reporte_distribucion_gastos_categoria(?,?,?)}";

    try (Connection cn = ConexionBD.getConnection();
         CallableStatement cs = cn.prepareCall(sql)) {

        cs.setInt(1, idUsuario);
        cs.setInt(2, anio);
        cs.setInt(3, mes);

        try (ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                ReporteDistribucionGastosCategoria item = new ReporteDistribucionGastosCategoria();
                item.setIdCategoria(rs.getInt("id_categoria"));
                item.setNombreCategoria(rs.getString("nombre_categoria"));
                item.setTotalGastado(rs.getBigDecimal("total_gastado"));
                item.setPorcentajeTotalGastos(rs.getBigDecimal("porcentaje_total_gastos"));
                item.setCantidadTransacciones(rs.getInt("cantidad_transacciones"));
                lista.add(item);
            }
        }

    } catch (Exception e) {
        throw new RuntimeException("Error al obtener el reporte de distribución de gastos por categoría: " + e.getMessage(), e);
    }

    return lista;
}
    public List<ReporteCumplimientoPresupuesto> obtenerReporteCumplimientoPresupuesto(
        int idPresupuesto, int anio, int mes) {

    List<ReporteCumplimientoPresupuesto> lista = new ArrayList<>();
    String sql = "{call sp_reporte_cumplimiento_presupuesto_categoria_subcategoria(?,?,?)}";

    try (Connection cn = ConexionBD.getConnection();
         CallableStatement cs = cn.prepareCall(sql)) {

        cs.setInt(1, idPresupuesto);
        cs.setInt(2, anio);
        cs.setInt(3, mes);

        try (ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                ReporteCumplimientoPresupuesto item = new ReporteCumplimientoPresupuesto();

                item.setIdCategoria(rs.getInt("id_categoria"));
                item.setNombreCategoria(rs.getString("nombre_categoria"));
                item.setIdSubcategoria(rs.getInt("id_subcategoria"));
                item.setNombreSubcategoria(rs.getString("nombre_subcategoria"));
                item.setMontoPresupuestado(rs.getBigDecimal("monto_presupuestado"));
                item.setMontoEjecutado(rs.getBigDecimal("monto_ejecutado"));
                item.setDiferencia(rs.getBigDecimal("diferencia"));
                item.setPorcentajeEjecucion(rs.getBigDecimal("porcentaje_ejecucion"));
                item.setIndicadorVisual(rs.getString("indicador_visual"));

                lista.add(item);
            }
        }

    } catch (Exception e) {
        throw new RuntimeException("Error al obtener el reporte 3: " + e.getMessage(), e);
    }

    return lista;
}
    
    public List<ReporteTendenciaGastosCategoria> obtenerReporteTendenciaGastosCategoria(
        int idUsuario,
        int anioDesde,
        int mesDesde,
        int anioHasta,
        int mesHasta) {

    List<ReporteTendenciaGastosCategoria> lista = new ArrayList<>();
    String sql = "{call sp_reporte_tendencia_gastos_categoria(?,?,?,?,?)}";

    try (Connection cn = ConexionBD.getConnection();
         CallableStatement cs = cn.prepareCall(sql)) {

        cs.setInt(1, idUsuario);
        cs.setInt(2, anioDesde);
        cs.setInt(3, mesDesde);
        cs.setInt(4, anioHasta);
        cs.setInt(5, mesHasta);

        try (ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                ReporteTendenciaGastosCategoria item = new ReporteTendenciaGastosCategoria();
                item.setIdCategoria(rs.getInt("id_categoria"));
                item.setNombreCategoria(rs.getString("nombre_categoria"));
                item.setAnio(rs.getInt("anio"));
                item.setMes(rs.getInt("mes"));
                item.setTotalGastado(rs.getBigDecimal("total_gastado"));
                lista.add(item);
            }
        }

    } catch (Exception e) {
        throw new RuntimeException("Error al obtener el reporte 4: " + e.getMessage(), e);
    }

    return lista;
}
    
    public List<ReporteEstadoObligacionFija> obtenerReporteEstadoObligacionesFijas(
        int idUsuario, int anio, int mes) {

    List<ReporteEstadoObligacionFija> lista = new ArrayList<>();
    String sql = "{call sp_reporte_estado_obligaciones_fijas(?,?,?)}";

    try (Connection cn = ConexionBD.getConnection();
         CallableStatement cs = cn.prepareCall(sql)) {

        cs.setInt(1, idUsuario);
        cs.setInt(2, anio);
        cs.setInt(3, mes);

        try (ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                ReporteEstadoObligacionFija item = new ReporteEstadoObligacionFija();
                item.setIdObligacion(rs.getInt("id_obligacion"));
                item.setNombreObligacion(rs.getString("nombre_obligacion"));
                item.setNombreCategoria(rs.getString("nombre_categoria"));
                item.setMontoMensual(rs.getBigDecimal("monto_mensual"));
                item.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
                item.setTotalPagadoMes(rs.getBigDecimal("total_pagado_mes"));
                item.setFechaUltimoPago(rs.getDate("fecha_ultimo_pago"));
                item.setEstadoPago(rs.getString("estado_pago"));
                item.setDiasReferencia(rs.getInt("dias_referencia"));
                lista.add(item);
            }
        }

    } catch (Exception e) {
        throw new RuntimeException("Error al obtener el reporte 5: " + e.getMessage(), e);
    }

    return lista;
}
}