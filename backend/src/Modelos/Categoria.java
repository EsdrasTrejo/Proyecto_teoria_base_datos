/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Usuario
 */

public class Categoria {
    private int idCategoria;
    private String nombre;
    private String descripcion;
    private String tipo;
    private int idUsuarioPropietario;
    private boolean estado;

    public Categoria() {
    }

    public Categoria(int idCategoria, String nombre, String descripcion, String tipo, int idUsuarioPropietario, boolean estado) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.idUsuarioPropietario = idUsuarioPropietario;
        this.estado = estado;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdUsuarioPropietario() {
        return idUsuarioPropietario;
    }

    public void setIdUsuarioPropietario(int idUsuarioPropietario) {
        this.idUsuarioPropietario = idUsuarioPropietario;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}