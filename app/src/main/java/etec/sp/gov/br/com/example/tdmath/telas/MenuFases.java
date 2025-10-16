package etec.sp.gov.br.com.example.tdmath.telas;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import etec.sp.gov.br.com.example.tdmath.R;
import etec.sp.gov.br.com.example.tdmath.controller.MapController;
import etec.sp.gov.br.com.example.tdmath.model.Banco;
import etec.sp.gov.br.com.example.tdmath.model.Mapa;

public class MenuFases extends AppCompatActivity {
    private SQLiteDatabase db;
    private Banco bd = new Banco(this);
    LinearLayout LnLm;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_fases);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        consultas
//        try {
//            try {
//                db = bd.getReadableDatabase();
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//            String[] colunas = {"idMapa", "nome", "ImgUrlMap"};
//
//            // consulta
//            Cursor cursor = db.query("mapa", colunas, null, null, null, null, null);
//
//            // não vazia
//            if (cursor.moveToFirst()) {
//                do {
//                    @SuppressLint("Range")
//                    int id = cursor.getInt(cursor.getColumnIndex("idMapa"));
//                    @SuppressLint("Range")
//                    String nome = cursor.getString(cursor.getColumnIndex("nome"));
//                    @SuppressLint("Range")
//                    String img = cursor.getString(cursor.getColumnIndex("ImgUrlMap"));
//
//                    // LogCat
//                    Log.d("mapa", "ID: " + id + ", nome: " + nome + ", img: " + img);
//                } while (cursor.moveToNext());
//            } else {
//                Log.d("mapa", "Nenhum registro encontrado");
//            }
//            cursor.close();
//        } catch (Exception e) {
//            Log.e("mapa", "Erro ao consultar: " + e.getMessage());
//        }
        LnLm = findViewById(R.id.LnLm);

        MapController map = new MapController(this);

        ArrayList<Mapa> mapas = map.consultaMapas();

        for (Mapa mapa : mapas){
            Integer id = mapa.getIdMapa();
            String nome = mapa.getNome();
            String img = mapa.getImgUrlMap();

            @SuppressLint("DiscouragedApi")
            int resImg = getResources().getIdentifier(img, "drawable", getPackageName());

            //criando um Layout vertical para ser o btn Mapa
            LinearLayout btnContainer = new LinearLayout(this);
            btnContainer.setOrientation(LinearLayout.VERTICAL);
            btnContainer.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            containerParams.setMargins(16, 16, 16, 16);
            btnContainer.setLayoutParams(containerParams);

            Log.d("mapa", "Nome imagem: " + img + " -> resID: " + resImg);

            ImageButton btnMap = new ImageButton(this);
            btnMap.setBackgroundResource(resImg);
            LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(300, 200);
            btnMap.setLayoutParams(imgParams);

            TextView txtMap = new TextView(this);
            txtMap.setText(nome);
            txtMap.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            txtMap.setLayoutParams(txtParams);

            btnContainer.addView(btnMap);
            btnContainer.addView(txtMap);

            btnContainer.setOnClickListener(v -> {
                    Toast.makeText(MenuFases.this, "Mapa selecionado: " + mapa.getNome(), Toast.LENGTH_SHORT).show();
            });
            btnMap.setOnClickListener(v -> {
                Toast.makeText(this, "Mapa selecionado: " + mapa.getNome(), Toast.LENGTH_SHORT).show();
            });
            LnLm.addView(btnContainer);
        }
    }
}