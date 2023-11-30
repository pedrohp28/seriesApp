
// https://www.youtube.com/watch?v=sBjw3nC-OT8

package com.example.series;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText email;
    private EditText senha;
    private Button loginButton;
    private Button cadastroButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.edtEmail);
        senha = findViewById(R.id.edtSenha);
        loginButton = findViewById(R.id.btnLogin);
        cadastroButton = findViewById(R.id.btnSalvar);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });

        cadastroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCadastroActivity();
            }
        });
    }

    private void login(View v) {

        String emailLogin = email.getText().toString();
        String senhaLogin = senha.getText().toString();

        auth.signInWithEmailAndPassword(emailLogin, senhaLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Login feito com sucesso!", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            openListaSeries();
                        }
                    }, 1000);
                } else {
                    String erro;
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        erro = "E-mail ou senha incorreto!";
                    }
                    Toast.makeText(getApplicationContext(), erro, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void openListaSeries() {
        Intent intent = new Intent(this, ListaSeriesActivity.class);
        startActivity(intent);
    }

    private void openCadastroActivity() {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }
}