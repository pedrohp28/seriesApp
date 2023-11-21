package com.example.series.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.series.R;
import com.example.series.model.Serie;
import com.example.series.vh.SerieVh;

import java.util.ArrayList;
import java.util.List;

public class SerieAdapter extends RecyclerView.Adapter<SerieAdapter.SerieVh>{
    private final List<Serie> lista;
    public SerieAdapter(List<Serie> lista) {
        this.lista = lista;
    }
    public void setItems(ArrayList<Serie> items) {
        lista.addAll(items);
    }
    public ArrayList<Serie> getTarefas() {
        return lista;
    }
    public Serie getTarefa(int position) {
        return lista.get(position);
    }

    @NonNull
    @Override
    public SerieVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new SerieVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SerieVh holder, int position) {

    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

    class SerieVh extends RecyclerView.ViewHolder {

        public SerieVh(@NonNull View itemView) {
            super(itemView);

        }
    }
}
