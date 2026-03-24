/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Usuario
 */
public class Subcategoria {
    private int idSubcategoria;
    private int idCategoria;
    private String nombre;
    private String descripcion;
    private boolean esDefecto;
    private boolean estado;

    public Subcategoria() {
    }

    public Subcategoria(int idSubcategoria, int idCategoria, String nombre, String descripcion, boolean esDefecto, boolean estado) {
        this.idSubcategoria = idSubcategoria;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.esDefecto = esDefecto;
        this.estado = estado;
    }

    public int getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(int idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
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

    public boolean isEsDefecto() {
        return esDefecto;
    }

    public void setEsDefecto(boolean esDefecto) {
        this.esDefecto = esDefecto;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}