package com.example.clinicaloscerros;

import android.app.AlertDialog;
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

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private PacienteDatabase db;
    private ExecutorService executor;
    private Handler mainHandler;
    private TextView pacientesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pacientesTextView = findViewById(R.id.paciente_text);
        Button addButton = findViewById(R.id.add_button);


        db = PacienteDatabase.getInstance(this);
        executor = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        addButton.setOnClickListener(v -> showAddPacienteDialog());
        loadPaciente();
    }

    private void showAddPacienteDialog(){
        EditText titleInput = new EditText(this);
        titleInput.setHint("Nombre");

        new AlertDialog.Builder(this)
                .setTitle("Nuevo paciente")
                .setView(titleInput)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String title = titleInput.getText().toString();
                    if(!title.isEmpty()){
                        savePaciente(new Paciente(title, 20));
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void savePaciente(Paciente paciente){
        executor.execute(() -> {
            db.pacienteDao().insert(paciente);
            loadPaciente();
        });
    }


    private void loadPaciente(){
        executor.execute(() -> {
            List<Paciente> pacientes = db.pacienteDao().getAllPacientes();
            mainHandler.post(() -> {
                StringBuilder text = new StringBuilder();
                for(Paciente paciente : pacientes){
                    text.append(paciente.getNombre()).append("\n");
                }
                pacientesTextView.setText(text.toString());
            });
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        executor.shutdown();
    }



}