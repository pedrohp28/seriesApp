package com.example.series;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.series.adapter.SerieAdapter;
import com.example.series.model.Serie;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class ListaSeriesActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public FloatingActionButton button;
    public AppBarLayout bar;
    public SerieAdapter adapter;
    Serie s = new Serie("Serie", 2, 3, "Quarta-Feira", "Netflix");
    Serie s2 = new Serie("Serie2", 1, 5, "Sexta-Feira", "HBO Max");
    ArrayList<Serie> list = new ArrayList<>(Arrays.asList(s, s2));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_series);

        button.findViewById(R.id.btnAdd);
        bar.findViewById(R.id.appBar);
        recyclerView.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        adapter = new SerieAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}