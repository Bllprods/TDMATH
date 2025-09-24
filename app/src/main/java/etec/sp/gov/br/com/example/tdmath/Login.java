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
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

    Button BtnLog, BtnCad;
    ImageButton BtnVoltarL;
    EditText edtUserl, edtSenha;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        int fontSize = sharedPref.getInt("font_size", 16); // 16 é o valor padrão caso ainda não tenha salvo
        config.updateFontSize(findViewById(R.id.main), fontSize);

        BtnLog = findViewById(R.id.btnCadC);
        BtnCad = findViewById(R.id.btnCad);
        BtnVoltarL = findViewById(R.id.btnVL);
        edtSenha = findViewById(R.id.edtSenha);
        edtUserl = findViewById(R.id.edtEmail);


        BtnCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TelaCad = new Intent( Login.this, cadastro.class);
                startActivity(TelaCad);
            }
        });

        BtnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementar o PHP

                if (!TextUtils.isEmpty(edtUserl.getText().toString().trim())){
                    Intent TelaAnterior = new Intent(Login.this, MenuFases.class);
                    startActivity(TelaAnterior);
                } else {
                    AlertDialog erro = new AlertDialog.Builder(Login.this).create();
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

        BtnVoltarL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TelaAnterior = new Intent(Login.this, MainActivity.class);
                startActivity(TelaAnterior);
            }
        });
    }
}