package etec.sp.gov.br.com.example.tdmath;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.function.Function;

import etec.sp.gov.br.com.example.tdmath.telas.Login;

public class MainActivity extends AppCompatActivity {

    ImageButton BtnJogar,BtnOpt;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        int fontSize = sharedPref.getInt("font_size", 16);
        config.updateFontSize(findViewById(R.id.main), fontSize);

        BtnJogar = findViewById(R.id.imgBtnJogar);
        BtnOpt = findViewById(R.id.imgBtnOpt);

        BtnJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TelaJogar = new Intent(MainActivity.this, Login.class);
                startActivity(TelaJogar);
            }
        });
        BtnOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent TelaOpt = new Intent(MainActivity.this, config.class);
                startActivity(TelaOpt);
            }
        });
    }
}