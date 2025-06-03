package br.felipe.com.felipe_kaike_rag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import br.felipe.com.felipe_kaike_rag.R;
import br.felipe.com.felipe_kaike_rag.view.MainActivity;

public class OpcoesActivity extends AppCompatActivity {
    private ImageButton botaoVoltar;
    private LinearLayout opcaoInfosAcademicas;
    private LinearLayout opcaoTourFateb;
    private LinearLayout opcaoCorpoDocente;
    private LinearLayout opcaoOutrasDuvidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes);

        botaoVoltar = findViewById(R.id.btnVoltar);
        opcaoInfosAcademicas = findViewById(R.id.infosAcademicas);
        opcaoTourFateb = findViewById(R.id.tourFateb);
        opcaoCorpoDocente = findViewById(R.id.corpoDocente);
        opcaoOutrasDuvidas = findViewById(R.id.outrasDuvidas);

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OpcoesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}