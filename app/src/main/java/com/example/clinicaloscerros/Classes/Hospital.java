package com.example.clinicaloscerros.Classes;

import java.util.List;

public class Hospital {
    private String nombre;
    private String direccion;
    private String horarioAtencion;
    private List<String> servicios;

    public Hospital(String nombre, String direccion, String horarioAtencion, List<String> servicios) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.horarioAtencion = horarioAtencion;
        this.servicios = servicios;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getHorarioAtencion() {
        return horarioAtencion;
    }

    public List<String> getServicios() {
        return servicios;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", horarioAtencion='" + horarioAtencion + '\'' +
                ", servicios=" + servicios +
                '}';
    }





}
