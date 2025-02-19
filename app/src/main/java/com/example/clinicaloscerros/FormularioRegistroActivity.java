package com.example.clinicaloscerros;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clinicaloscerros.Classes.Usuario;

public class FormularioRegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_registro);

        TextView tvTitle;
        EditText etNombre, etApellido, etCorreo, etMatricula, etPassword, etFechaNacimiento;
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
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento);

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
            String fechaNacimiento = etFechaNacimiento.getText().toString();

            Usuario usuario = new Usuario(nombre, apellido, correo, matricula, seleccion, password, fechaNacimiento);

            System.out.println(usuario.toString());

        });

    }
}