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
public class ReporteResumenMensual {
    private int anio;
    private int mes;
    private BigDecimal totalIngresos;
    private BigDecimal totalGastos;
    private BigDecimal totalAhorros;
    private BigDecimal balanceFinal;

    public ReporteResumenMensual() {
    }

    public ReporteResumenMensual(int anio, int mes, BigDecimal totalIngresos,
                                 BigDecimal totalGastos, BigDecimal totalAhorros,
                                 BigDecimal balanceFinal) {
        this.anio = anio;
        this.mes = mes;
        this.totalIngresos = totalIngresos;
        this.totalGastos = totalGastos;
        this.totalAhorros = totalAhorros;
        this.balanceFinal = balanceFinal;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public BigDecimal getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(BigDecimal totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public BigDecimal getTotalGastos() {
        return totalGastos;
    }

    public void setTotalGastos(BigDecimal totalGastos) {
        this.totalGastos = totalGastos;
    }

    public BigDecimal getTotalAhorros() {
        return totalAhorros;
    }

    public void setTotalAhorros(BigDecimal totalAhorros) {
        this.totalAhorros = totalAhorros;
    }

    public BigDecimal getBalanceFinal() {
        return balanceFinal;
    }

    public void setBalanceFinal(BigDecimal balanceFinal) {
        this.balanceFinal = balanceFinal;
    }
}
