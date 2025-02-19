package com.example.clinicaloscerros.Classes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PacienteDao {
    @Query("SELECT * FROM pacientes")
    List<Paciente>getAllPacientes();

    @Insert
    void insert(Paciente paciente);

    @Update
        // Anotación para actualizar
    void update(Paciente paciente);  // Método para actualizar un paciente

    @Delete
    void delete(Paciente paciente);

    @Query("SELECT * FROM pacientes WHERE id = :id LIMIT 1")
    Paciente getPacienteById(long id);  // Obtener un paciente por su ID
}
