package com.example.series;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.series.model.Serie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddSeriesActivity extends AppCompatActivity {

    EditText titulo, temporada, episodio;
    Spinner dias, plataforma;
    Button salvar, voltar;
    String usuarioId;
    FirebaseDatabase database;
    String[] arrayDias = new String[] {
            "", "Segunda-Feira", "Terça-Feira", "Quarta-Feira", "Quinta-Feira", "Sexta-Feira", "Sábado", "Domingo"
    };
    String[] arryaPlataformas = new String[] {
            "", "Netflix", "HBO Max", "Disney+", "Star+", "Outros"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_series);

        usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();

        titulo = findViewById(R.id.edtTitulo);
        temporada = findViewById(R.id.edtTemporada);
        episodio = findViewById(R.id.edtEpisodio);
        dias = findViewById(R.id.spinDias);
        plataforma = findViewById(R.id.spinPlataforma);
        salvar = findViewById(R.id.btnSalvarSerie);
        voltar = findViewById(R.id.btnVoltar);

        dias.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        plataforma.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);

        spinnerDias();
        spinnerPlataforma();

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarSerie();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void spinnerDias() {
        dias.setAdapter(new ArrayAdapter<String>(
                getApplicationContext(),
                R.layout.spinner_text,
                arrayDias
        ));
    }

    private void spinnerPlataforma() {
        plataforma.setAdapter(new ArrayAdapter<String>(
                getApplicationContext(),
                R.layout.spinner_text,
                arryaPlataformas
        ));
    }

    private void salvarSerie() {
        String tituloSerie = titulo.getText().toString();
        Integer temporadaSerie = Integer.parseInt(temporada.getText().toString());
        Integer episodioSerie = Integer.parseInt(episodio.getText().toString());
        String diaSerie = dias.getSelectedItem().toString();
        String plataformaSerie = plataforma.getSelectedItem().toString();

        Serie serie = new Serie(tituloSerie, temporadaSerie, episodioSerie, diaSerie, plataformaSerie);

        DatabaseReference query = database.getReference("Usuario");
        query.child(usuarioId).child("Series").push().setValue(serie);
        Toast.makeText(getApplicationContext(), "Serie salva com sucesso!", Toast.LENGTH_SHORT).show();
    }
}