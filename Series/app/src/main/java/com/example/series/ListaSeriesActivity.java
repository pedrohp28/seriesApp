package com.example.series;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.series.adapter.SerieAdapter;
import com.example.series.model.Serie;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ListaSeriesActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference userRef;
    String usuarioId;
    public RecyclerView recyclerView;
    public FloatingActionButton btnAdd, btnSair;
    public SerieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_series);

        btnSair = findViewById(R.id.btnfSair);
        btnAdd = findViewById(R.id.btnfAdd);
        auth = FirebaseAuth.getInstance();
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Toast.makeText(getApplicationContext(), "Saindo...", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
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

    private void buscarItens() {

        usuarioId = FirebaseAuth.getInstance().getUid();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("Usuario").child(usuarioId).child("Series");
        Query tarefasQuery = userRef.orderByChild("nome");

        tarefasQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Serie> series = new ArrayList<>();
                for (DataSnapshot dados : snapshot.getChildren()) {

                    Serie item = dados.getValue(Serie.class);
                    item.setKey(dados.getKey());
                    series.add(item);
                }
                adapter.setItems(series);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Erro na busca" + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openAddSeriesActivity() {
        Intent intent = new Intent(this, AddSeriesActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new SerieAdapter();
        buscarItens();
        recyclerView.setAdapter(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHandler(0, ItemTouchHelper.RIGHT));
        helper.attachToRecyclerView(recyclerView);
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
            usuarioId = FirebaseAuth.getInstance().getUid();
            database = FirebaseDatabase.getInstance();
            userRef = database.getReference("Usuario").child(usuarioId).child("Series").child(key);
            userRef.removeValue();
            adapter.getSeries().remove(viewHolder.getAdapterPosition());
            adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }
    }
}