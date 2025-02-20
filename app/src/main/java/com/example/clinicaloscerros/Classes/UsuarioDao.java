package com.example.clinicaloscerros.Classes;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UsuarioDao {
    @Query("SELECT * FROM usuarios")
    List<Usuario> getAllUsuarios();


    @Insert
    void insert(Usuario usuario);

    @Update
    void update(Usuario usuario);

    @Query("SELECT * FROM usuarios WHERE correo = :correo AND contrasenia = :contrasenia LIMIT 1")
    Usuario login(String correo, String contrasenia);
}
