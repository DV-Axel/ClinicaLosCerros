package com.example.clinicaloscerros;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicaloscerros.Adapters.PacienteAdapter;
import com.example.clinicaloscerros.Classes.Paciente;
import com.example.clinicaloscerros.Classes.PacienteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListadoPacientesActivity extends AppCompatActivity {
    private PacienteAdapter pacienteAdapter;

    private PacienteDatabase db;
    private ExecutorService executor;
    private Handler mainHandler;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado_pacientes);

        db = PacienteDatabase.getInstance(this);
        executor = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());


        recyclerView = findViewById(R.id.recyclerPacientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        executor.execute(() -> {
            List<Paciente> pacientes = db.pacienteDao().getAllPacientes();

            System.out.println(db.pacienteDao().getAllPacientes());

            mainHandler.post(() -> {
                pacienteAdapter = new PacienteAdapter(this, pacientes, db, executor);
                recyclerView.setAdapter(pacienteAdapter);
            });
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }
}