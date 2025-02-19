package com.example.clinicaloscerros;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
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


        int idPaciente = getIntent().getIntExtra("id_paciente", -1); //-1 seria un valor por defecto


        TextView tvTitulo;
        EditText etNombre, etApellido, etDNI, etFechaIngreso, etSintomas;
        Button btnGuardar;

        // Referencias a los elementos del layout
        tvTitulo = findViewById(R.id.tvTitulo);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etDNI = findViewById(R.id.etDNI);
        etFechaIngreso = findViewById(R.id.etFechaIngreso);
        etSintomas = findViewById(R.id.etSintomas);
        btnGuardar = findViewById(R.id.btnGuardar);

        db = PacienteDatabase.getInstance(this);
        executor = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        if(idPaciente != -1){
            executor.execute(() -> {

                Paciente pacienteid = db.pacienteDao().getPacienteById(idPaciente);

                etNombre.setText(pacienteid.getNombre());
                etApellido.setText(pacienteid.getApellido());
                etDNI.setText(pacienteid.getDni());
                etFechaIngreso.setText(pacienteid.getFechaIngreso());
                etSintomas.setText(pacienteid.getSintomas());
                tvTitulo.setText("Modificar paciente");
            });
        }

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

                    if(idPaciente != -1){

                        paciente.setId(idPaciente); // Asegurar que el paciente tenga el ID correcto

                        db.pacienteDao().update(paciente);
                        System.out.println("Modificado correstamente");
                        // Mostrar el Toast en el hilo principal
                        mainHandler.post(() -> {
                            Toast.makeText(this, "Paciente modificado correctamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, ListadoPacientesActivity.class);
                            this.startActivity(intent);
                            finish();
                        });
                    }else{
                        db.pacienteDao().insert(paciente);
                        System.out.println("Guardado correstamente");

                        // Mostrar el Toast en el hilo principal
                        mainHandler.post(() -> {
                            Toast.makeText(this, "Paciente guardado correctamente", Toast.LENGTH_SHORT).show();
                        });
                        etNombre.setText("");
                        etApellido.setText("");
                        etDNI.setText("");
                        etFechaIngreso.setText("");
                        etSintomas.setText("");
                    }
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

        // Bloquear fechas anteriores a hoy
        datePickerDialog.getDatePicker().setMinDate(calendario.getTimeInMillis());

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