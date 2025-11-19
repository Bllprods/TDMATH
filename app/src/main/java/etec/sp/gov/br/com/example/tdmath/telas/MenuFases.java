package etec.sp.gov.br.com.example.tdmath.telas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import etec.sp.gov.br.com.example.tdmath.R;
import etec.sp.gov.br.com.example.tdmath.controller.MapController;
import etec.sp.gov.br.com.example.tdmath.model.Mapa;

public class MenuFases extends AppCompatActivity {
    LinearLayout container;
    private int Id;

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
        SharedPreferences sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        int fontSize = sharedPref.getInt("font_size", 20);
        Config.updateFontSize(findViewById(R.id.main), fontSize);

        container = findViewById(R.id.LnLm);

        MapController map = new MapController(getBaseContext());
        ArrayList<Mapa> mapas = map.consultaMapas();
        //Mapa é o Construtor da classe Mapa, na model

        for (Mapa m : mapas) {
            LinearLayout containerMini = new LinearLayout(this);
            LinearLayout.LayoutParams LinearParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            containerMini.setOrientation(LinearLayout.VERTICAL);

            LinearParams.setMargins(15,15,15,15);
            LinearParams.gravity = Gravity.CENTER_HORIZONTAL;
            containerMini.setLayoutParams(LinearParams);
            containerMini.setOnClickListener(v -> {
                Intent fase = new Intent(this, tela_jogos.class);
                startActivity(fase);
//                AlertDialog mundo = new AlertDialog.Builder(this).create();
//                mundo.setTitle(m.getNome());
//                mundo.setMessage("este é o Mundo" + m.getNome() + "\n Imagem: " +m.getImgUrlMap());
//                mundo.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
//                mundo.show();
            });

            ImageButton MundoBtn = new ImageButton( this);
            int RecursoImg = getResources().getIdentifier(m.getImgUrlMap(), "drawable", getPackageName());
            //procura pelo nome, devolve o id
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    400,// largura
                    200 // altura
            );
            params.gravity = Gravity.CENTER_HORIZONTAL;
            params.setMargins(8, 8, 8, 8);
            MundoBtn.setId(m.getIdMapa());
            MundoBtn.setBackgroundResource(RecursoImg); // adicionando o id da imagem
            MundoBtn.setScaleType(ImageView.ScaleType.CENTER_CROP); // ou FIT_CENTER
            MundoBtn.setLayoutParams(params); // adicionando os parametros do botão
            MundoBtn.setClickable(false);

            TextView MundoTxt = new TextView(this);
            MundoTxt.setText(m.getNome());
            MundoTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

            containerMini.addView(MundoBtn);
            containerMini.addView(MundoTxt);
            container.addView(containerMini);
        }
    }

}