package com.example.clinicaloscerros.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicaloscerros.Classes.Paciente;
import com.example.clinicaloscerros.Classes.PacienteDatabase;
import com.example.clinicaloscerros.FormularioPacienteActivity;
import com.example.clinicaloscerros.FormularioRegistroActivity;
import com.example.clinicaloscerros.ListadoPacientesActivity;
import com.example.clinicaloscerros.R;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class PacienteAdapter extends RecyclerView.Adapter<PacienteAdapter.PacienteViewHolder> {

    private Context context;
    private List<Paciente> pacientes;
    private PacienteDatabase db;
    private ExecutorService executor;

    public PacienteAdapter(Context context, List<Paciente> pacientes, PacienteDatabase db, ExecutorService executor) {
        this.context = context;
        this.pacientes = pacientes;
        this.db = db;
        this.executor = executor;
    }

    @Override
    public PacienteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_paciente, parent, false);
        return new PacienteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PacienteViewHolder holder, int position) {
        Paciente paciente = pacientes.get(position);

        // Asignar datos a las vistas
        holder.tvNombre.setText(paciente.getNombre() + " " + paciente.getApellido());
        holder.tvSintomas.setText(paciente.getSintomas());
        holder.tvDni.setText("DNI: " + paciente.getDni()); // Mostrar DNI
        holder.tvFechaIngreso.setText("Fecha Ingreso: " + paciente.getFechaIngreso()); // Mostrar Fecha de Ingreso

        // Configurar el botón "Dar de Alta"
        holder.btnDarAlta.setOnClickListener(v -> {
            // Mostrar el diálogo en el hilo principal
            new AlertDialog.Builder(context).setTitle("Confirmar alta").setMessage("¿Estás seguro de que deseas dar de alta a " + paciente.getNombre() + " " + paciente.getApellido() + "?").setPositiveButton("Sí", (dialog, which) -> {
                // Ejecutar la eliminación en un hilo en segundo plano
                executor.execute(() -> {
                    db.pacienteDao().delete(paciente);

                    ((ListadoPacientesActivity) context).runOnUiThread(() -> {
                        pacientes.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, pacientes.size());
                        Toast.makeText(context, "Paciente dado de alta", Toast.LENGTH_SHORT).show();
                    });
                });
            }).setNegativeButton("No", (dialog, which) -> {
                // Cerrar el diálogo sin hacer nada
                dialog.dismiss();
            }).show();
        });


        // Configurar el boton "Editar"
        holder.btnEditar.setOnClickListener(v -> {

            System.out.println(paciente.getId());

            Intent intent = new Intent(context, FormularioPacienteActivity.class);
            intent.putExtra("id_paciente", paciente.getId());
            context.startActivity(intent);

        });
    }


    @Override
    public int getItemCount() {
        return pacientes.size();
    }

    public static class PacienteViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre, tvSintomas, tvDni, tvFechaIngreso;
        Button btnDarAlta; // Nuevo campo para el botón
        Button btnEditar;

        public PacienteViewHolder(View itemView) {
            super(itemView);

            // Inicialización de los elementos del layout
            tvNombre = itemView.findViewById(R.id.tvNombrePaciente);
            tvSintomas = itemView.findViewById(R.id.tvSintomasPaciente);
            tvDni = itemView.findViewById(R.id.tvDniPaciente); // Nueva vista para DNI
            tvFechaIngreso = itemView.findViewById(R.id.tvFechaIngreso); // Nueva vista para Fecha de Ingreso
            btnDarAlta = itemView.findViewById(R.id.btnDarAlta); // Inicialización del botón
            btnEditar = itemView.findViewById(R.id.btnEditar);
        }
    }


}

