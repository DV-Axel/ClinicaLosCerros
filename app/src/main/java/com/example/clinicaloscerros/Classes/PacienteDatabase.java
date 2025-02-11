package com.example.clinicaloscerros.Classes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Paciente.class}, version = 1)
public abstract class PacienteDatabase extends RoomDatabase {
    private static PacienteDatabase instance;

    public abstract PacienteDao pacienteDao();


    public static synchronized PacienteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), PacienteDatabase.class, "paciente_database").build();
        }
        return instance;
    }
}
