/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Usuario
 */
public class usuario {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private double salarioMensual;
    private String clave;
    private boolean estado;

    public usuario() {
    }

    public usuario(int idUsuario, String nombre, String apellido, String correo, double salarioMensual,String clave, boolean estado) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.salarioMensual = salarioMensual;
        this.clave=clave;
        this.estado = estado;
    }

    public String getClave() {
    return clave;
}

public void setClave(String clave) {
    this.clave = clave;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public double getSalarioMensual() {
        return salarioMensual;
    }

    public void setSalarioMensual(double salarioMensual) {
        this.salarioMensual = salarioMensual;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}