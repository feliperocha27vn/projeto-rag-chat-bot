package br.felipe.com.felipe_kaike_rag.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import br.felipe.com.felipe_kaike_rag.R;
import br.felipe.com.felipe_kaike_rag.view.services.ApiService;

public class TourFateb extends AppCompatActivity  {
    private Button btnFazerPergunta;
    private ImageButton botaoVoltar;
    private EditText editPergunta;
    private ProgressDialog progressDialog;
    private Handler mainHandler = new Handler();
    private Gson gson = new Gson();
    private ApiService apiService;
    private static final String BASE_URL = "URL_API";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_fateb);

        botaoVoltar = findViewById(R.id.btnVoltarTourFateb);
        btnFazerPergunta = findViewById(R.id.btnFazerPerguntaTour);
        editPergunta = findViewById(R.id.editPerguntaTourFateb);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando...");
        progressDialog.setCancelable(false);

        apiService = new ApiService(this, mainHandler, gson, BASE_URL);

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TourFateb.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnFazerPergunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pergunta = editPergunta.getText().toString().trim();

                apiService.fazerRequisicao(pergunta, progressDialog, response -> {
                    Intent intent = new Intent(TourFateb.this, RespostaActivity.class);
                    intent.putExtra("pergunta", pergunta);
                    intent.putExtra("resposta", response);
                    startActivity(intent);
                }, errorMessage -> {
                    Toast.makeText(TourFateb.this, errorMessage, Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        super.onDestroy();
    }
}