package com.example.proyectotareas.model;

public class agregarTareaModel {

    private String nombre;
    private String descripcion;
    private String  completadoPendiente;

    public agregarTareaModel(String nombre, String descripcion, String completadoPendiente) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.completadoPendiente = completadoPendiente;
    }

    public agregarTareaModel() {
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

    public String getCompletadoPendiente() {
        return completadoPendiente;
    }

    public void setCompletadoPendiente(String completadoPendiente) {
        this.completadoPendiente = completadoPendiente;
    }
}
