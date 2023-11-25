package com.example.series;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.series.adapter.SerieAdapter;
import com.example.series.model.Serie;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ListaSeriesActivity extends AppCompatActivity {

    FirebaseAuth auth;
    public RecyclerView recyclerView;
    public FloatingActionButton btnAdd, btnSair;
    public SerieAdapter adapter;
    Serie s = new Serie("Serie", 2, 3, "Quarta-Feira", "Netflix");
    Serie s2 = new Serie("Serie2", 1, 5, "Sexta-Feira", "HBO Max");
    ArrayList<Serie> list = new ArrayList<>(Arrays.asList(s, s2));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_series);

        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerView);
        btnSair = findViewById(R.id.btnfSair);
        btnAdd = findViewById(R.id.btnfAdd);

        recyclerView.setHasFixedSize(true);
        adapter = new SerieAdapter(list);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHandler(0, ItemTouchHelper.RIGHT));
        helper.attachToRecyclerView(recyclerView);

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Toast.makeText(getApplicationContext(), "Saindo...", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        openMainActivity();
                    }
                }, 1000);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddSeriesActivity();
            }
        });
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void openAddSeriesActivity() {
        Intent intent = new Intent(this, AddSeriesActivity.class);
        startActivity(intent);
    }

    private class ItemTouchHandler extends ItemTouchHelper.SimpleCallback{
        public ItemTouchHandler(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();

            Collections.swap(adapter.getSeries(), from, to);
            adapter.notifyItemMoved(from, to);
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            String key = adapter.getSerie(viewHolder.getAdapterPosition()).getKey();
//            userRef = database.getReference("Usuario").child(usuarioId).child("Tarefas").child(key);
//            userRef.removeValue();
            adapter.getSeries().remove(viewHolder.getAdapterPosition());
            adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
//            usuarioId = FirebaseAuth.getInstance().getUid();
//            database = FirebaseDatabase.getInstance();
        }
    }
}