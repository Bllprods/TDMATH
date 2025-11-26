package etec.sp.gov.br.com.example.tdmath.controller;

import android.media.MediaPlayer;
import android.util.Log;

import etec.sp.gov.br.com.example.tdmath.controller.Music;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

// uma classe pai que é chamadas pelas outras
public abstract class BaseActivity extends AppCompatActivity {
    private static MediaPlayer mp;
    private static int oldmp = 0;
    private int mscAtual;

    // codigo é enviado obrigatóriamente pela activity que extender a Base
    protected abstract int getcodeAct();

    @Override
    protected void onStart() {//quando aberto
        super.onStart();
        mscAtual = getcodeAct();
        Music music = new Music();

        if (mp == null) {
                if (oldmp != mscAtual) {
                    try {
                        mp = music.playMusic(this, mscAtual);//pega o codigo da activity
                    } catch (IOException e) {
                        Log.d("Music", "onResume: FALHA NA EXECUÇÃO");
                    }
                } else {
                }
                // mp = music.playMusic(this, mscAtual);//pega o codigo da activity
        } else {
            if (oldmp != mscAtual) {
                mp.release();
                mp = null;

                try {
                    mp = music.playMusic(this, mscAtual);//pega o codigo da activity
                } catch (IOException e) {
                    Log.d("Music", "onResume: FALHA NA EXECUÇÃO");
                }
            } else {
            }
        }
        oldmp = mscAtual;
        if (mp != null && !mp.isPlaying()) {
            mp.start();
        }
    }

    @Override
    protected void onPause() {// quando em segundo plano
        super.onPause();
        if (mp != null) {
            mp.pause();
        }
    }

    @Override
    protected void onDestroy() {// quando fechado
        super.onDestroy();
      if (mp != null) {
           mp.release(); // limpar cache e parar a musica
          mp = null;
      }
    }
}
