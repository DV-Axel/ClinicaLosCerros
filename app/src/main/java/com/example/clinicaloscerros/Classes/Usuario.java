package com.example.clinicaloscerros.Classes;

import android.content.Context;
import android.widget.Toast;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "usuarios")
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "apellido")
    private String apellido;

    @ColumnInfo(name = "correo")
    private String correo;

    @ColumnInfo(name = "matricula")
    private String matricula;

    @ColumnInfo(name = "especializacion")
    private String especializacion;

    @ColumnInfo(name = "contrasenia")
    private String contrasenia;

    @ColumnInfo(name = "fechaNacimiento")
    private String fechaNacimiento;

    public Usuario(String nombre, String apellido, String correo, String matricula, String especializacion, String contrasenia, String fechaNacimiento) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.matricula = matricula;
        this.especializacion = especializacion;
        this.contrasenia = contrasenia;
        this.fechaNacimiento = fechaNacimiento;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEspecializacion() {
        return especializacion;
    }

    public void setEspecializacion(String especializacion) {
        this.especializacion = especializacion;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", matricula='" + matricula + '\'' +
                ", especializacion='" + especializacion + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                '}';
    }

    public boolean validar(Context context){

        if(this.nombre.isEmpty() || this.apellido.isEmpty() || this.correo.isEmpty() || this.matricula.isEmpty() || this.especializacion.isEmpty() || this.contrasenia.isEmpty() || this.fechaNacimiento.isEmpty()){
            Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return false;
        }



        //Validacion de nombre y apellido
        if(!this.nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$") || !this.apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")){
            Toast.makeText(context,"Verifique su nombre y apellido", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Validacion de fecha
        //Usa try catch por que parse devuelve un booleano
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); //evita que se acepten fechas invalidas

        try {
            // Convertir la fecha de ingreso del paciente a un objeto Date
            Date fechaIngresoDate = sdf.parse(this.fechaNacimiento);


        } catch(ParseException e){
            Toast.makeText(context, "Error en la fecha", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }
}
