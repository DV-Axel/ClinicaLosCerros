package com.example.clinicaloscerros;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clinicaloscerros.Classes.Paciente;
import com.example.clinicaloscerros.Classes.PacienteDatabase;

import org.w3c.dom.Text;

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



        btnGuardar.setOnClickListener(view -> {
            String nombre = etNombre.getText().toString();
            String apellido = etApellido.getText().toString();
            String dni = etDNI.getText().toString();
            String fechaIngreso = etFechaIngreso.getText().toString();
            String sintomas = etSintomas.getText().toString();

            Paciente paciente = new Paciente(nombre, apellido, dni, fechaIngreso, sintomas);

            paciente.validar();

        });

    }
}