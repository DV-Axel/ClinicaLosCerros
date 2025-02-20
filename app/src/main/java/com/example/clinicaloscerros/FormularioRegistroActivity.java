package com.example.clinicaloscerros;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clinicaloscerros.Classes.PacienteDatabase;
import com.example.clinicaloscerros.Classes.Usuario;
import com.example.clinicaloscerros.Classes.UsuarioDatabase;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FormularioRegistroActivity extends AppCompatActivity {

    private UsuarioDatabase db;
    private ExecutorService executor;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_registro);

        TextView tvTitle;
        EditText etNombre, etApellido, etCorreo, etMatricula, etPassword, etPassword2, etFechaNacimiento;
        // Para acceder al valor seleccionado en otro punto del código:
        Button btnRegistrarDoctor;

        // Referencias a los elementos del layout
        btnRegistrarDoctor = findViewById(R.id.btnRegistrarDoctor);
        tvTitle = findViewById(R.id.tvTitle);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etCorreo = findViewById(R.id.etCorreo);
        etMatricula = findViewById(R.id.etMatricula);
        etPassword = findViewById(R.id.etPassword);
        etPassword2 = findViewById(R.id.etPassword2);
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento);

        db = UsuarioDatabase.getInstance(this);
        executor = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        etFechaNacimiento.setOnClickListener(view -> mostrarDatePicker(etFechaNacimiento));


        Spinner spEspecializacion = findViewById(R.id.spEspecializacion);
        String[] especializaciones = {"Cardiología", "Pediatría", "Dermatología", "Neurología", "Oncología"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, especializaciones);
        spEspecializacion.setAdapter(adapter);

        // Variable para almacenar la especialización seleccionada
        final String[] especializacionSeleccionada = {especializaciones[0]}; // Inicializado con la primera opción

        spEspecializacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                especializacionSeleccionada[0] = especializaciones[position]; // Guardar el valor seleccionado
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // En caso de que no se seleccione nada, podrías dejar el valor predeterminado
            }
        });
        
        btnRegistrarDoctor.setOnClickListener(view -> {
            String nombre = etNombre.getText().toString();
            String apellido = etApellido.getText().toString();
            String correo = etCorreo.getText().toString();
            String matricula = etMatricula.getText().toString();
            String seleccion = especializacionSeleccionada[0];
            String password = etPassword.getText().toString();
            String password2 = etPassword2.getText().toString();
            String fechaNacimiento = etFechaNacimiento.getText().toString();

            if(password.equals(password2)){
                Usuario usuario = new Usuario(nombre, apellido, correo, matricula, seleccion, password, fechaNacimiento);


                if(usuario.validar(this)){
                    executor.execute(() -> {
                        db.usuarioDao().insert(usuario);
                        System.out.println("Guardado correstamente");
                        // Mostrar el Toast en el hilo principal
                        mainHandler.post(() -> {
                            Toast.makeText(this, "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                        });

                        System.out.println(db.usuarioDao().getAllUsuarios());

                        Intent intent = new Intent(this, LoginActivity.class);
                        this.startActivity(intent);
                        finish();
                    });


                }
            }else{
                Toast.makeText(this, "Contraseñas no coinciden", Toast.LENGTH_SHORT);
            }
        });

    }

    //Esta es una funcion para abrir un calendario y elegir la fecha, te formatea automaticamente la fecha
    private void mostrarDatePicker(EditText etFechaNacimiento) {
        final Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    // Sumar 1 al mes porque empieza desde 0
                    String fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                    etFechaNacimiento.setText(fechaSeleccionada);
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