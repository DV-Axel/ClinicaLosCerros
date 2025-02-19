package com.example.clinicaloscerros.Classes;

import java.util.List;

public class Hospital {
    private String nombre;
    private String direccion;
    private String horarioAtencion;
    private double latitud;
    private double longitud;
    private List<String> servicios;

    public Hospital(String nombre, String direccion, String horarioAtencion, List<String> servicios, double latitud, double longitud) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.horarioAtencion = horarioAtencion;
        this.servicios = servicios;
        this.latitud = latitud;
        this.longitud = longitud;
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

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", horarioAtencion='" + horarioAtencion + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", servicios=" + servicios +
                '}';
    }
}
