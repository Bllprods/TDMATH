package etec.sp.gov.br.com.example.tdmath.telas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import etec.sp.gov.br.com.example.tdmath.R;
import etec.sp.gov.br.com.example.tdmath.controller.BaseActivity;

public class Config extends BaseActivity {


    int Volume, volumeAtual, maxVolumeSistema;
    int TextFont;
    ImageView img;
    Button BtnVoltar, BtnConfirm;
    SeekBar brTfont, brAsom;
    TextView resultadoSom, resultadoText;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_config);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService((LAYOUT_INFLATER_SERVICE));

        BtnVoltar = findViewById(R.id.btnVoltar);
        BtnConfirm = findViewById(R.id.BtnConfirmar);
        brAsom = findViewById(R.id.brAsom);
        brTfont = findViewById(R.id.brTfonte);
        img = findViewById(R.id.imageView8);

        resultadoSom = findViewById(R.id.resultadoSom);
        resultadoText = findViewById(R.id.resultadoText);

        //volume
        maxVolumeSistema = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        brAsom.setMax(maxVolumeSistema);
        brAsom.setProgress(volumeAtual);

        // Carregar tamanho da fonte salvo
        SharedPreferences sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        TextFont = sharedPref.getInt("font_size", 20);

        // Atualizar a SeekBar e o TextView
        brTfont.setMin(20);
        brTfont.setMax(30);
        brTfont.setProgress(TextFont);

        updateFontSize(findViewById(R.id.main), TextFont);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brAsom.setProgress(0);
                Volume = brAsom.getProgress();
            }
        });
        BtnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Voltar = new Intent(Config.this, MainActivity.class);
                startActivity(Voltar);
            }
        });

        // https://youtu.be/zhOaEvZVO3c?si=pGiyfTnY89Kw6gXj
        brAsom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            // é sempre que muda, ele atualiza
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                resultadoSom.setText("Audio: " + progress + "/" + seekBar.getMax());
            }

            @Override
            // quando clica, os dois de baixo
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Volume = brAsom.getProgress();
            }
        });

        brTfont.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                resultadoText.setText("Tamanho da fonte: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                TextFont = brTfont.getProgress();
            }
        });
//  https://developer.android.com/develop/ui/views/components/dialogs#java
        // novo alerta, com construtor nesta tela, e o cria
        BtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog confirmaSave = new AlertDialog.Builder(Config.this).create();

                // definindo Titulo
                confirmaSave.setTitle("Confirmar?");

                // definindo Mensagem
                confirmaSave.setMessage("Deseja confirmar estas mudanças?");

                confirmaSave.setButton(AlertDialog.BUTTON_POSITIVE, "Sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, Volume, 0);

                                SharedPreferences sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putInt("font_size", TextFont);
                                editor.apply();

                                updateFontSize(findViewById(R.id.main), TextFont);
                            }
                        });

                confirmaSave.setButton(AlertDialog.BUTTON_NEGATIVE, "Não",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                confirmaSave.show();
            }
        });
    }
    public static void updateFontSize(View view, float size) {
        // Se a view for um TextView, aplique o tamanho da fonte.
        if (view instanceof TextView) {
            // Se a view for um ViewGroup, percorra todos os seus filhos.
            ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        } else if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            // percorrer todas as TextViews
            for (int i = 0; i < group.getChildCount(); i++) {
                updateFontSize(group.getChildAt(i), size);
            }
        }

        // atualizar layout
        // https://medium.com/kotlin-android-chronicle/understanding-the-roles-of-requestlayout-and-invalidate-when-adding-a-view-in-android-93d47be50e1f
        view.requestLayout();
    }
    protected int getcodeAct() {
        // manda o codigo desta activity
        return 3; // MainActivity toca música 1
    }
}
