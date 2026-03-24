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
public class ReporteCumplimientoPresupuesto {
    private int idCategoria;
    private String nombreCategoria;
    private int idSubcategoria;
    private String nombreSubcategoria;
    private BigDecimal montoPresupuestado;
    private BigDecimal montoEjecutado;
    private BigDecimal diferencia;
    private BigDecimal porcentajeEjecucion;
    private String indicadorVisual;

    public ReporteCumplimientoPresupuesto() {
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

    public int getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(int idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }

    public String getNombreSubcategoria() {
        return nombreSubcategoria;
    }

    public void setNombreSubcategoria(String nombreSubcategoria) {
        this.nombreSubcategoria = nombreSubcategoria;
    }

    public BigDecimal getMontoPresupuestado() {
        return montoPresupuestado;
    }

    public void setMontoPresupuestado(BigDecimal montoPresupuestado) {
        this.montoPresupuestado = montoPresupuestado;
    }

    public BigDecimal getMontoEjecutado() {
        return montoEjecutado;
    }

    public void setMontoEjecutado(BigDecimal montoEjecutado) {
        this.montoEjecutado = montoEjecutado;
    }

    public BigDecimal getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(BigDecimal diferencia) {
        this.diferencia = diferencia;
    }

    public BigDecimal getPorcentajeEjecucion() {
        return porcentajeEjecucion;
    }

    public void setPorcentajeEjecucion(BigDecimal porcentajeEjecucion) {
        this.porcentajeEjecucion = porcentajeEjecucion;
    }

    public String getIndicadorVisual() {
        return indicadorVisual;
    }

    public void setIndicadorVisual(String indicadorVisual) {
        this.indicadorVisual = indicadorVisual;
    }
}