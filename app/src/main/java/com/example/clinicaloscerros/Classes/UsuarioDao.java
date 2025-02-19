package com.example.clinicaloscerros.Classes;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
@Dao
public interface UsuarioDao {

    @Insert
    void insert(Usuario usuario);

    @Update
    void update(Usuario usuario);

    @Query("SELECT * FROM usuarios WHERE correo = :correo AND contrasenia = :contrasenia LIMIT 1")
    Usuario login(String correo, String contrasenia);
}
