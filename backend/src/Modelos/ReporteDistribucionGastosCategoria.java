/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.math.BigDecimal;
/**
 *
 * @author Usuario
 */
public class ReporteDistribucionGastosCategoria {
    private int idCategoria;
    private String nombreCategoria;
    private BigDecimal totalGastado;
    private BigDecimal porcentajeTotalGastos;
    private int cantidadTransacciones;

    public ReporteDistribucionGastosCategoria() {
    }

    public ReporteDistribucionGastosCategoria(int idCategoria, String nombreCategoria,
                                              BigDecimal totalGastado,
                                              BigDecimal porcentajeTotalGastos,
                                              int cantidadTransacciones) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.totalGastado = totalGastado;
        this.porcentajeTotalGastos = porcentajeTotalGastos;
        this.cantidadTransacciones = cantidadTransacciones;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public BigDecimal getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(BigDecimal totalGastado) {
        this.totalGastado = totalGastado;
    }

    public BigDecimal getPorcentajeTotalGastos() {
        return porcentajeTotalGastos;
    }

    public void setPorcentajeTotalGastos(BigDecimal porcentajeTotalGastos) {
        this.porcentajeTotalGastos = porcentajeTotalGastos;
    }

    public int getCantidadTransacciones() {
        return cantidadTransacciones;
    }

    public void setCantidadTransacciones(int cantidadTransacciones) {
        this.cantidadTransacciones = cantidadTransacciones;
    }
}