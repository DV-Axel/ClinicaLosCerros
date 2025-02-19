package com.example.clinicaloscerros.Classes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Usuario.class}, version = 1)
public abstract class UsuarioDatabase extends RoomDatabase {

    private static UsuarioDatabase instance;

    public abstract UsuarioDao usuarioDao();

    public static synchronized UsuarioDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), UsuarioDatabase.class, "usuario_database").fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
