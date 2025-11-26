package etec.sp.gov.br.com.example.tdmath.telas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import etec.sp.gov.br.com.example.tdmath.controller.WebViewInterface;

public class tela_jogos extends AppCompatActivity {
    WebView GameView;
    Button btnVoltar;
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
        btnVoltar = findViewById(R.id.btnVoltar);

        WebSettings webSettings = GameView.getSettings();
        webSettings.setJavaScriptEnabled(true);// Js
        webSettings.setAllowFileAccess(true);// Acessar arquivos
        webSettings.setAllowFileAccessFromFileURLs(true);// Acessar Arquivos via Url
        WebViewInterface bridge = new WebViewInterface(this);
        // O nome "AndroidBridge" DEVE ser o mesmo usado no script.js
        GameView.addJavascriptInterface(bridge, "AndroidBridge");


        int idMapa = getIntent().getIntExtra("idMapa", -1);
        int idNivel = getIntent().getIntExtra("idNivel", -1);

        switch (idMapa){
            case 1:
                GameView.loadUrl("file:///android_asset/jogos/1/pa.html");
                break;
            case 2:
                GameView.loadUrl("file:///android_asset/jogos/2/cabo.html");
                break;
            case 3:
                GameView.loadUrl("file:///android_asset/jogos/3/game1.html");
                break;
            case 4:
                GameView.loadUrl("file:///android_asset/jogos/4/jogo.html");
                break;
            case 5:
                GameView.loadUrl("file:///android_asset/jogos/5/pagor.html");
                break;
            case 6:
                GameView.loadUrl("file:///android_asset/jogos/1/pa.html");
                break;
            case 7:
                GameView.loadUrl("file:///android_asset/jogos/1/pa.html");
                break;

        }
        Log.d("DEBUG", "ID do mapa: " + idMapa + " | ID do nível: " + idNivel);


        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(tela_jogos.this, MenuFases.class);
                startActivity(menu);
            }
        });

    }

}