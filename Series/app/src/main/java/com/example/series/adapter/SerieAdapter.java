package com.example.series.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.series.R;
import com.example.series.model.Serie;

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
    public List<Serie> getTarefas() {
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
        Serie serie = lista.get(position);
        holder.bind(serie);
    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

    class SerieVh extends RecyclerView.ViewHolder {

        TextView nomeSerie, temporada, episodio, diaSemana, plataforma;

        public SerieVh(@NonNull View itemView) {
            super(itemView);

            nomeSerie.findViewById(R.id.txtNome);
            temporada.findViewById(R.id.txtTemporada);
            episodio.findViewById(R.id.txtEpisodio);
            diaSemana.findViewById(R.id.txtDiaSemana);
            plataforma.findViewById(R.id.txtPlataforma);
        }

        public void bind(Serie serie) {
            nomeSerie.setText(serie.getNome());
            temporada.setText("Temporada" + serie.getTemporada());
            episodio.setText("Episodio" + serie.getEpisodio());
            diaSemana.setText(serie.getDiaSemana());
            plataforma.setText(serie.getPlataforma());
        }
    }
}
