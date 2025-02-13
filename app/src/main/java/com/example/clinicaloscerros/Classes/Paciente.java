package com.example.clinicaloscerros.Classes;


import android.content.Context;
import android.widget.Toast;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Entity(tableName = "pacientes")
public class Paciente {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "apellido")
    private String apellido;

    @ColumnInfo(name = "dni")
    private String dni;

    @ColumnInfo(name = "fecha_ingreso")
    private String fechaIngreso;

    @ColumnInfo(name = "sintomas")
    private String sintomas;


    public Paciente(String nombre, String apellido, String dni, String fechaIngreso, String sintomas) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
        this.sintomas = sintomas;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni=" + dni +
                ", fechaIngreso='" + fechaIngreso + '\'' +
                ", sintomasSS='" + sintomas + '\'' +
                '}';
    }


    public void validar(Context context, Paciente paciente){

        //Valida los campos vacios
        if(paciente.nombre.isEmpty() || paciente.apellido.isEmpty() || paciente.dni.isEmpty()
                || paciente.fechaIngreso.isEmpty() || paciente.sintomas.isEmpty()){
            Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        //Validacion de nombre y apellido
        if(!paciente.nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$") || !paciente.apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")){
            Toast.makeText(context,"Verifique su nombre y apellido", Toast.LENGTH_SHORT).show();
            return;
        }

        //Validacion de dni
        if(!paciente.dni.matches("^\\d{8}$")){
            Toast.makeText(context, "El DNI tiene un formato incorrecto.", Toast.LENGTH_SHORT).show();
            return;
        }

        //Validacion de fecha
        //Usa try catch por que parse devuelve un booleano
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        try{
            //Solo pruebo que se pueda convertir
            //Por comodidad y simplicidad trabajo la fecha como String
            sdf.parse(paciente.fechaIngreso);

        } catch(ParseException e){
            Toast.makeText(context, "Error en la fecha", Toast.LENGTH_SHORT).show();
            return;
        }

        System.out.println(paciente);

        paciente.guardarPaciente();
    }



    public void guardarPaciente(){

    }
}
