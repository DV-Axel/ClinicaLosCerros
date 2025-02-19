package com.example.clinicaloscerros;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clinicaloscerros.Classes.Paciente;
import com.example.clinicaloscerros.Classes.PacienteDatabase;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FormularioPacienteActivity extends AppCompatActivity {

    private PacienteDatabase db;
    private ExecutorService executor;
    private Handler mainHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formulario_paciente);



        EditText etNombre, etApellido, etDNI, etFechaIngreso, etSintomas;
        Button btnGuardar;






        // Referencias a los elementos del layout
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etDNI = findViewById(R.id.etDNI);
        etFechaIngreso = findViewById(R.id.etFechaIngreso);
        etSintomas = findViewById(R.id.etSintomas);
        btnGuardar = findViewById(R.id.btnGuardar);






        db = PacienteDatabase.getInstance(this);
        executor = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());



        etFechaIngreso.setOnClickListener(view -> mostrarDatePicker(etFechaIngreso));



        btnGuardar.setOnClickListener(view -> {
            String nombre = etNombre.getText().toString();
            String apellido = etApellido.getText().toString();
            String dni = etDNI.getText().toString();
            String fechaIngreso = etFechaIngreso.getText().toString();
            String sintomas = etSintomas.getText().toString();

            Paciente paciente = new Paciente(nombre, apellido, dni, fechaIngreso, sintomas);

            if(paciente.validar(this)){
                executor.execute(() -> {
                    db.pacienteDao().insert(paciente);
                    System.out.println("Guardado correstamente");

                    // Mostrar el Toast en el hilo principal
                    mainHandler.post(() -> {
                        Toast.makeText(this, "Paciente guardado correctamente", Toast.LENGTH_SHORT).show();
                    });
                });
            }

        });
    }

    //Esta es una funcion para abrir un calendario y elegir la fecha, te formatea automaticamente la fecha
    private void mostrarDatePicker(EditText etFechaIngreso) {
        final Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    // Sumar 1 al mes porque empieza desde 0
                    String fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                    etFechaIngreso.setText(fechaSeleccionada);
                }, anio, mes, dia);

        datePickerDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }

}