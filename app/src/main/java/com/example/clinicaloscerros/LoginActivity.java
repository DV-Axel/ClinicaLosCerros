package com.example.clinicaloscerros;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clinicaloscerros.Classes.PacienteDatabase;
import com.example.clinicaloscerros.Classes.Usuario;
import com.example.clinicaloscerros.Classes.UsuarioDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {
    private UsuarioDatabase db;
    private ExecutorService executor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        EditText etCorreo, etPassword;
        Button btnLogin, btnRegistro;

        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.btnRegistro);

        db = UsuarioDatabase.getInstance(this);
        executor = Executors.newSingleThreadExecutor();


        btnLogin.setOnClickListener(view -> {

            String correo = etCorreo.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            executor.execute(() -> {
                Usuario usuario = db.usuarioDao().login(correo, password);

                runOnUiThread(() -> {
                    if (usuario != null) {
                        Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                        // Navegar a la pantalla principal
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Evita volver al login con "Atrás"
                    } else {
                        Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                });
            });

        });


        btnRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormularioRegistroActivity.class);
            startActivity(intent);
            finish(); // Evita volver al login con "Atrás"
        });


    }
}