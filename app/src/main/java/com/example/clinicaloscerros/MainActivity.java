package com.example.clinicaloscerros;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Referenciar los botones
        Button btnAddPatient = findViewById(R.id.btnAddPatient);
        Button btnListPatients = findViewById(R.id.btnListPatients);
        Button btnListHospitals = findViewById(R.id.btnListHospitals);

        // Listener para el botón "Agregar Paciente"
        btnAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad del formulario de pacientes
                Intent intent = new Intent(MainActivity.this, FormularioPacienteActivity.class);
                startActivity(intent);
            }
        });

        // Listener para el botón "Listado Pacientes"
        btnListPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad del listado de pacientes
                Intent intent = new Intent(MainActivity.this, ListadoPacientesActivity.class);
                startActivity(intent);
            }
        });


        // Listener para el botón "Hospitales cercanos"
        btnListHospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad del listado de pacientes
                Intent intent = new Intent(MainActivity.this, HospitalesCercanosActivity.class);
                startActivity(intent);
            }
        });
    }
}
