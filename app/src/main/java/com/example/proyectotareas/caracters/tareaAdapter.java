package com.example.proyectotareas.caracters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectotareas.EditarTareaActivity;
import com.example.proyectotareas.R;
import com.example.proyectotareas.model.agregarTareaModel;

import java.util.List;

public class tareaAdapter extends RecyclerView.Adapter<tareaAdapter.ViewHolder> {
    private List<agregarTareaModel> listaTareas;
    private Context context;

    public tareaAdapter(Context context,List<agregarTareaModel> listaTareas){
        this.context = context;
        this.listaTareas = listaTareas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView teViNombre, teVidescripcion, teViEstado;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            teViNombre = itemView.findViewById(R.id.teViNombre);
            teVidescripcion = itemView.findViewById(R.id.teViDescripcion);
            teViEstado = itemView.findViewById(R.id.teViEstado);
        }
    }

    @NonNull
    @Override
    public tareaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tareas, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull tareaAdapter.ViewHolder holder, int position){
        agregarTareaModel agregarTareaModel = listaTareas.get(position);
        holder.teViNombre.setText("Nombre: " + agregarTareaModel.getNombre());
        holder.teVidescripcion.setText("Descripción: " + agregarTareaModel.getDescripcion());
        holder.teViEstado.setText("estado: " + agregarTareaModel.getCompletadoPendiente());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, EditarTareaActivity.class);
            intent.putExtra("titulo", agregarTareaModel.getNombre());
            intent.putExtra("descripcion", agregarTareaModel.getDescripcion());
            intent.putExtra("estado", agregarTareaModel.getCompletadoPendiente());
            context.startActivity(intent);
        });
        }

    @Override
    public int getItemCount(){
        return listaTareas.size();
    }
}
