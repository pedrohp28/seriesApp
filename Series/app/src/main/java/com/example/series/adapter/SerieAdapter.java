package com.example.series.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.example.series.AtualizarSerieActivity;
import com.example.series.ListaSeriesActivity;
import com.example.series.R;
import com.example.series.model.Serie;

import java.util.ArrayList;
import java.util.List;

public class SerieAdapter extends RecyclerView.Adapter<SerieAdapter.SerieVh>{

    private final List<Serie> lista = new ArrayList<>();
    public void setItems(ArrayList<Serie> items) {
        lista.addAll(items);
    }
    public List<Serie> getSeries() {
        return lista;
    }
    public Serie getSerie(int position) {
        return lista.get(position);
    }

    @NonNull
    @Override
    public SerieVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new SerieVh(view, parent.getContext());
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

        public SerieVh(@NonNull View itemView, final Context context) {
            super(itemView);

            nomeSerie = itemView.findViewById(R.id.txtNome);
            temporada = itemView.findViewById(R.id.txtTemporada);
            episodio = itemView.findViewById(R.id.txtEpisodio);
            diaSemana = itemView.findViewById(R.id.txtDiaSemana);
            plataforma = itemView.findViewById(R.id.txtPlataforma);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getItemCount() > 0) {
                        Serie serie = lista.get(getLayoutPosition());
                        Intent intent = new Intent(context, AtualizarSerieActivity.class);
                        intent.putExtra("SERIE", serie);
                        Toast.makeText(context, serie.getNome(), Toast.LENGTH_SHORT).show();
                        ((AppCompatActivity) context).startActivity(intent);
                    }
                }
            });
        }

        public void bind(Serie serie) {
            nomeSerie.setText(serie.getNome());
            temporada.setText("Temporada " + serie.getTemporada());
            episodio.setText("Episodio " + serie.getEpisodio());
            diaSemana.setText(serie.getDiaSemana());
            plataforma.setText(serie.getPlataforma());
        }
    }
}
