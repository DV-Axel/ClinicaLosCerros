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

import com.example.clinicaloscerros.Adapters.HospitalAdapter;
import com.example.clinicaloscerros.Adapters.PacienteAdapter;
import com.example.clinicaloscerros.Classes.Hospital;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HospitalesCercanosActivity extends AppCompatActivity {

    private HospitalAdapter hospitalAdapter;
    private RecyclerView recyclerView;
    private ExecutorService executor;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hospitales_cercanos);

        executor = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        recyclerView = findViewById(R.id.recyclerHospitales);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        executor.execute(() -> {
            List<Hospital> hospitales = new ArrayList<>();

            if(hospitales.isEmpty()){
                //harcodeo una serie de hospitales
                hospitales.add(new Hospital(
                        "Clinica y Maternidad Suizo Argentina",
                        "Avenida Pueyrredón 1461",
                        "24 horas",
                        Arrays.asList("Guardia", "Cardiología", "Traumatología", "Pediatría")
                ));

                hospitales.add(new Hospital(
                        "Sanatorio Los Arcos",
                        "Avenida Juan Bautista Justo 909",
                        "Lunes a Viernes 08:00 - 20:00",
                        Arrays.asList("Cardiología", "Neurología", "Dermatología")
                ));

                hospitales.add(new Hospital(
                        "Clinica Zabala",
                        "Avenida Cabildo 1295",
                        "24 horas",
                        Arrays.asList("Guardia", "Cirugía", "Ginecología", "Odontología")
                ));

                hospitales.add(new Hospital(
                        "Clinica Olivos",
                        "Avenida Maipú 1660",
                        "Lunes a Sabados 06:00 - 23:00",
                        Arrays.asList("Pediatria", "Cardiologia", "Oftalmologia")
                ));
            }



            mainHandler.post(() -> {
                hospitalAdapter = new HospitalAdapter(this, hospitales);
                recyclerView.setAdapter(hospitalAdapter);
            });

        });

    }
}