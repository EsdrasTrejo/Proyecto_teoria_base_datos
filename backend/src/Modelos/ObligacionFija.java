/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.Date;
/**
 *
 * @author Usuario
 */
public class ObligacionFija {

    private int idObligacion;
    private int idSubcategoria;
    private int idUsuario;
    private String nombre;
    private String descripcion;
    private double monto;
    private int diaVencimiento;
    private Date fechaInicio;
    private Date fechaFin;
    private int activo;

    public int getIdObligacion() {
        return idObligacion;
    }

    public void setIdObligacion(int idObligacion) {
        this.idObligacion = idObligacion;
    }

    public int getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(int idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getDiaVencimiento() {
        return diaVencimiento;
    }

    public void setDiaVencimiento(int diaVencimiento) {
        this.diaVencimiento = diaVencimiento;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
}
