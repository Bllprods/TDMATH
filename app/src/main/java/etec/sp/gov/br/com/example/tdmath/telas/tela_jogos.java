package etec.sp.gov.br.com.example.tdmath.telas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import etec.sp.gov.br.com.example.tdmath.R;

public class tela_jogos extends AppCompatActivity {
    WebView GameView;
    Button btnVoltar, btnPause;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_jogos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Pegando a referência do WebView
        GameView = findViewById(R.id.GameView);
        btnPause = findViewById(R.id.btnVoltar);
        btnVoltar = findViewById(R.id.btnPause);

        WebSettings webSettings = GameView.getSettings();
        webSettings.setJavaScriptEnabled(true);// Js
        webSettings.setAllowFileAccess(true);// Acessar arquivos
        webSettings.setAllowFileAccessFromFileURLs(true);// Acessar Arquivos via Url
        GameView.loadUrl("file:///android_asset/PerArea/pa.html");

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(tela_jogos.this, MenuFases.class);
                startActivity(menu);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}