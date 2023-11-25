package com.example.series;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    ImageButton lessTemp, lessEp, plusTemp, plusEp;
    Button salvar;
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

        titulo = findViewById(R.id.edtTitulo);
        temporada = findViewById(R.id.edtTemporada);
        episodio = findViewById(R.id.edtEpisodio);
        dias = findViewById(R.id.spinDias);
        plataforma = findViewById(R.id.spinPlataforma);
        lessTemp = findViewById(R.id.btnLessTemp);
        lessEp = findViewById(R.id.btnLessEp);
        plusTemp = findViewById(R.id.btnPlusTemp);
        plusEp = findViewById(R.id.btnPlusEp);
        salvar = findViewById(R.id.btnSalvarSerie);

        spinnerDias();
        spinnerPlataforma();

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarTarefa();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        openListaSeries();
//                    }
//                }, 1000);
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

    private void salvarTarefa() {
        String tituloSerie = titulo.getText().toString();
        Integer temporadaSerie = Integer.parseInt(temporada.getText().toString().replace("Temporada ", ""));
        Integer episodioSerie = Integer.parseInt(episodio.getText().toString().replace("Episodio ", ""));
        String diaSerie = dias.getSelectedItem().toString();
        String plataformaSerie = plataforma.getSelectedItem().toString();

        Serie serie = new Serie(tituloSerie, temporadaSerie, episodioSerie, diaSerie, plataformaSerie);

        String usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("Usuario");
        userRef.child(usuarioId).child("Series").push().setValue(serie);
        Toast.makeText(getApplicationContext(), "Serie salva com sucesso!", Toast.LENGTH_SHORT).show();
    }

    public void openListaSeries() {
        Intent intent = new Intent(this, ListaSeriesActivity.class);
        startActivity(intent);
    }
}