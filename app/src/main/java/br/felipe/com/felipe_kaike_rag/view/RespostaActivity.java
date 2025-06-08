package br.felipe.com.felipe_kaike_rag.view;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.felipe.com.felipe_kaike_rag.R;

public class RespostaActivity extends AppCompatActivity {

    private TextView tvPerguntaTexto;
    private TextView tvRespostaTexto;
    private ImageButton btnVoltarResposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resposta);

        tvPerguntaTexto = findViewById(R.id.tvPerguntaTexto);
        tvRespostaTexto = findViewById(R.id.tvRespostaTexto);
        btnVoltarResposta = findViewById(R.id.btnVoltarResposta);

        // Recebe os textos da Intent
        String pergunta = getIntent().getStringExtra("pergunta");
        String resposta = getIntent().getStringExtra("resposta");

        // Exibe os textos recebidos (ou mensagem padrão)
        tvPerguntaTexto.setText(pergunta != null ? pergunta : "Pergunta não informada.");
        tvRespostaTexto.setText(resposta != null ? resposta : "Resposta não informada.");

        // Botão voltar fecha a activity
        btnVoltarResposta.setOnClickListener(v -> finish());
    }
}