/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.math.BigDecimal;
import java.util.Date;
/**
 *
 * @author Usuario
 */
public class ReporteEstadoObligacionFija {
    private int idObligacion;
    private String nombreObligacion;
    private String nombreCategoria;
    private BigDecimal montoMensual;
    private Date fechaVencimiento;
    private BigDecimal totalPagadoMes;
    private Date fechaUltimoPago;
    private String estadoPago;
    private int diasReferencia;

    public ReporteEstadoObligacionFija() {
    }

    public int getIdObligacion() {
        return idObligacion;
    }

    public void setIdObligacion(int idObligacion) {
        this.idObligacion = idObligacion;
    }

    public String getNombreObligacion() {
        return nombreObligacion;
    }

    public void setNombreObligacion(String nombreObligacion) {
        this.nombreObligacion = nombreObligacion;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public BigDecimal getMontoMensual() {
        return montoMensual;
    }

    public void setMontoMensual(BigDecimal montoMensual) {
        this.montoMensual = montoMensual;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public BigDecimal getTotalPagadoMes() {
        return totalPagadoMes;
    }

    public void setTotalPagadoMes(BigDecimal totalPagadoMes) {
        this.totalPagadoMes = totalPagadoMes;
    }

    public Date getFechaUltimoPago() {
        return fechaUltimoPago;
    }

    public void setFechaUltimoPago(Date fechaUltimoPago) {
        this.fechaUltimoPago = fechaUltimoPago;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public int getDiasReferencia() {
        return diasReferencia;
    }

    public void setDiasReferencia(int diasReferencia) {
        this.diasReferencia = diasReferencia;
    }
}
