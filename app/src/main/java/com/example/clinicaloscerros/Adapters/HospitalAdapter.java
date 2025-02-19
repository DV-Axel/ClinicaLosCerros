package com.example.clinicaloscerros.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicaloscerros.Classes.Hospital;
import com.example.clinicaloscerros.R;

import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder> {

    private Context context;
    private List<Hospital> hospitales;

    public HospitalAdapter(Context context, List<Hospital> hospitales) {
        this.context = context;
        this.hospitales = hospitales;


    }

    @Override
    public HospitalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_hospital, parent, false);
        return new HospitalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HospitalViewHolder holder, int position) {
        Hospital hospital = hospitales.get(position);

        holder.tvNombreHospital.setText(hospital.getNombre());
        holder.tvDireccionHospital.setText(hospital.getDireccion());
        holder.tvHorarioAtencion.setText(hospital.getHorarioAtencion());
    }

    @Override
    public int getItemCount() {
        return hospitales.size();
    }

    public static class HospitalViewHolder extends RecyclerView.ViewHolder{

        TextView tvNombreHospital, tvDireccionHospital, tvHorarioAtencion;
        Button btnIrMapa;

        public HospitalViewHolder(View itemView) {
            super(itemView);

            tvNombreHospital = itemView.findViewById(R.id.tvNombreHospital);
            tvDireccionHospital = itemView.findViewById(R.id.tvDireccionHospital);
            tvHorarioAtencion = itemView.findViewById(R.id.tvHorarioAtencion);
            btnIrMapa = itemView.findViewById(R.id.btnIrMapa);

        }
    }
}
