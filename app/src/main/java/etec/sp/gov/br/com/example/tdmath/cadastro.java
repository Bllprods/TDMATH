package etec.sp.gov.br.com.example.tdmath;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import etec.sp.gov.br.com.example.tdmath.telas.Login;

public class cadastro extends AppCompatActivity {
    Button BtnCadc, BtnVolc;
    EditText edtUser, edtEmail, edtSenha, edtCSenha;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        int fontSize = sharedPref.getInt("font_size", 16);
        config.updateFontSize(findViewById(R.id.main), fontSize);

        BtnCadc = findViewById(R.id.btnCadC);
        BtnVolc = findViewById(R.id.btnVolc);
        edtUser = findViewById(R.id.edtTxtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        edtCSenha = findViewById(R.id.edtTxtContSenha);

        BtnVolc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TelaAnterior = new Intent(cadastro.this, Login.class);
                startActivity(TelaAnterior);
            }
        });
        BtnCadc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // implementação do php
                if (!TextUtils.isEmpty(edtUser.getText().toString().trim())){
                    Intent TelaLogin = new Intent(cadastro.this, Login.class);
                    startActivity(TelaLogin);
                } else {
                    AlertDialog erro = new AlertDialog.Builder(cadastro.this).create();
                    erro.setTitle("ERRO");
                    erro.setMessage("erro no cadastro!!!!");
                    erro.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    erro.show();
                }
            }
        });
    }
}