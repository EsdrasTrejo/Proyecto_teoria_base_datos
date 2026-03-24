/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Usuario
 */
public class PresupuestoDetalle {
    private int idDetalle;
    private int idPresupuesto;
    private int idSubcategoria;
    private double montoMensual;
    private String observaciones;
    private boolean estado;

    public PresupuestoDetalle() {
    }

    public PresupuestoDetalle(int idDetalle, int idPresupuesto, int idSubcategoria, double montoMensual, String observaciones, boolean estado) {
        this.idDetalle = idDetalle;
        this.idPresupuesto = idPresupuesto;
        this.idSubcategoria = idSubcategoria;
        this.montoMensual = montoMensual;
        this.observaciones = observaciones;
        this.estado = estado;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(int idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public int getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(int idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }

    public double getMontoMensual() {
        return montoMensual;
    }

    public void setMontoMensual(double montoMensual) {
        this.montoMensual = montoMensual;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}