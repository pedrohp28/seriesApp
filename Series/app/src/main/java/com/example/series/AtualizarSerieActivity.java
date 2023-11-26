package com.example.series;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.series.model.Serie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AtualizarSerieActivity extends AppCompatActivity {

    TextView titulo, dias, plataforma;
    EditText temporada, episodio;
    ImageButton plusEp, plusTemp;
    Button salvar, voltar;
    String usuarioId;
    FirebaseDatabase database;
    Serie serie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_serie);

        usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();

        titulo = findViewById(R.id.txtTituloUp);
        temporada = findViewById(R.id.edtTemporadaUp);
        episodio = findViewById(R.id.edtEpisodioUp);
        dias = findViewById(R.id.spinDiasUp);
        plataforma = findViewById(R.id.spinPlataformaUp);
        salvar = findViewById(R.id.btnSalvarSerieUp);
        voltar = findViewById(R.id.btnVoltarUp);
        plusEp = findViewById(R.id.ibtnPlusEp);
        plusTemp = findViewById(R.id.ibtnPlusTemp);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            serie = (Serie) bundle.getSerializable("SERIE");
            titulo.setText(serie.getNome());
            temporada.setText(serie.getTemporada().toString());
            episodio.setText(serie.getEpisodio().toString());
            dias.setText(serie.getDiaSemana());
            plataforma.setText(serie.getPlataforma());
        }

        plusTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer temp = Integer.parseInt(temporada.getText().toString());
                temp += 1;
                temporada.setText(temp.toString());
            }
        });

        plusEp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer ep = Integer.parseInt(episodio.getText().toString());
                ep += 1;
                episodio.setText(ep.toString());
            }
        });

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarSerie();
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

    private void atualizarSerie() {
        String key = serie.getKey();
        String tituloSerie = titulo.getText().toString();
        Integer temporadaSerie = Integer.parseInt(temporada.getText().toString());
        Integer episodioSerie = Integer.parseInt(episodio.getText().toString());
        String diaSerie = dias.getText().toString();
        String plataformaSerie = plataforma.getText().toString();

        Serie serie = new Serie(tituloSerie, temporadaSerie, episodioSerie, diaSerie, plataformaSerie);

        DatabaseReference query = database.getReference("Usuario");
        query.child(usuarioId).child("Series").child(key).setValue(serie);
        Toast.makeText(getApplicationContext(), "Serie atualizada com sucesso!", Toast.LENGTH_SHORT).show();
    }
}