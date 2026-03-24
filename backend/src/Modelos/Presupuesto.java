/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Usuario
 */
public class Presupuesto {
    private int idPresupuesto;
    private int idUsuario;
    private String nombre;
    private int anioPeriodoInicio;
    private int mesPeriodoInicio;
    private int anioPeriodoFin;
    private int mesPeriodoFin;
    private boolean estado;

    public Presupuesto() {
    }

    public Presupuesto(int idPresupuesto, int idUsuario, String nombre,
                       int anioPeriodoInicio, int mesPeriodoInicio,
                       int anioPeriodoFin, int mesPeriodoFin, boolean estado) {
        this.idPresupuesto = idPresupuesto;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.anioPeriodoInicio = anioPeriodoInicio;
        this.mesPeriodoInicio = mesPeriodoInicio;
        this.anioPeriodoFin = anioPeriodoFin;
        this.mesPeriodoFin = mesPeriodoFin;
        this.estado = estado;
    }

    public int getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(int idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
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

    public int getAnioPeriodoInicio() {
        return anioPeriodoInicio;
    }

    public void setAnioPeriodoInicio(int anioPeriodoInicio) {
        this.anioPeriodoInicio = anioPeriodoInicio;
    }

    public int getMesPeriodoInicio() {
        return mesPeriodoInicio;
    }

    public void setMesPeriodoInicio(int mesPeriodoInicio) {
        this.mesPeriodoInicio = mesPeriodoInicio;
    }

    public int getAnioPeriodoFin() {
        return anioPeriodoFin;
    }

    public void setAnioPeriodoFin(int anioPeriodoFin) {
        this.anioPeriodoFin = anioPeriodoFin;
    }

    public int getMesPeriodoFin() {
        return mesPeriodoFin;
    }

    public void setMesPeriodoFin(int mesPeriodoFin) {
        this.mesPeriodoFin = mesPeriodoFin;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}