package com.example.clinicaloscerros.Adapters;

import android.content.Context;
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

        holder.tvNombre.setText(paciente.getNombre() + " " + paciente.getApellido());
        holder.tvSintomas.setText(paciente.getSintomas());

        // Configurar el botón "Dar de Alta"
        holder.btnDarAlta.setOnClickListener(v -> {
            executor.execute(() -> {
                db.pacienteDao().delete(paciente);

                ((ListadoPacientesActivity) context).runOnUiThread(() -> {
                    pacientes.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, pacientes.size());
                    Toast.makeText(context, "Paciente dado de alta", Toast.LENGTH_SHORT).show();
                });


            });
        });
    }


    @Override
    public int getItemCount() {
        return pacientes.size();
    }

    public static class PacienteViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre, tvSintomas;
        Button btnDarAlta; // Nuevo campo para el botón

        public PacienteViewHolder(View itemView) {
            super(itemView);

            // Inicialización de los elementos del layout
            tvNombre = itemView.findViewById(R.id.tvNombrePaciente);
            tvSintomas = itemView.findViewById(R.id.tvSintomasPaciente);
            btnDarAlta = itemView.findViewById(R.id.btnDarAlta); // Inicialización del botón
        }
    }

}

