package br.felipe.com.felipe_kaike_rag.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import br.felipe.com.felipe_kaike_rag.view.OpcoesActivity;
import br.felipe.com.felipe_kaike_rag.R;

public class MainActivity extends AppCompatActivity {
    private Button botaoComecar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        botaoComecar = findViewById(R.id.btnComecar);

        botaoComecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OpcoesActivity.class);
                startActivity(intent);
            }
        });
    }
}