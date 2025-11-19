package etec.sp.gov.br.com.example.tdmath.telas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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

import etec.sp.gov.br.com.example.tdmath.R;
import etec.sp.gov.br.com.example.tdmath.controller.UserController;
import etec.sp.gov.br.com.example.tdmath.model.Banco;

public class Cadastro extends AppCompatActivity {
    Button BtnCadc, BtnVolc;
    EditText edtUser, edtEmail, edtSenha, edtCSenha;
    private SQLiteDatabase db;
    private Banco criabd = new Banco(this);

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
        try {
            db = criabd.getWritableDatabase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        SharedPreferences sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        int fontSize = sharedPref.getInt("font_size", 16);
        Config.updateFontSize(findViewById(R.id.main), fontSize);

        BtnVolc = findViewById(R.id.btnVolc);
        BtnCadc = findViewById(R.id.btnCadC);
        edtUser = findViewById(R.id.edtTxtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        edtCSenha = findViewById(R.id.edtTxtContSenha);

        BtnVolc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TelaAnterior = new Intent(Cadastro.this, Login.class);
                startActivity(TelaAnterior);
            }
        });
        BtnCadc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // implementação do php
                if (!TextUtils.isEmpty(edtUser.getText().toString().trim()) &&
                        !TextUtils.isEmpty(edtEmail.getText().toString().trim()) &&
                        !TextUtils.isEmpty(edtSenha.getText().toString().trim()) ) {

                    UserController crud = new UserController(getBaseContext());
                    String user = edtUser.getText().toString();
                    String email = edtEmail.getText().toString();
                    String senha = edtSenha.getText().toString();
                    //String cadastro;
                    try {
                        //cadastro = String.valueOf(crud.cadastro(user, email, senha));
                        if (crud.cadastro(user, email, senha)){
                            Intent log = new Intent( Cadastro.this, Login.class);
                            startActivity(log);
                        } else {
                            AlertDialog erro = new AlertDialog.Builder(Cadastro.this).create();
                            erro.setTitle("ERRO");
                            erro.setMessage("usuario já cadastrado");
                            erro.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                            erro.show();
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    AlertDialog erro = new AlertDialog.Builder(Cadastro.this).create();
                    erro.setTitle("ERRO");
                    erro.setMessage("Insira todos os dados");
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