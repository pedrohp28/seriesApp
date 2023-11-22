package com.example.series;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarComponentes();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista();
            }
        });
    }

    public void lista() {
        Intent intent = new Intent(this, ListaSeriesActivity.class);
        startActivity(intent);
    }

    private void iniciarComponentes() {
        button.findViewById(R.id.button);
    }
}