package etec.sp.gov.br.com.example.tdmath.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.webkit.JavascriptInterface;

import org.json.JSONObject;

import java.util.FormatFlagsConversionMismatchException;

import etec.sp.gov.br.com.example.tdmath.model.Banco;

/*
* https://youtu.be/9RwJeocTgJg
* */
public class WebViewInterface {
    //https://developer.android.com/develop/ui/views/layout/webapps/webview?utm_source=chatgpt.com&hl=pt-br
    private DadosBd dds;
    private Context ct;

    public WebViewInterface(Context ct){
        this.ct = ct; // o contexto desta função é o contexto de quem chamar
        this.dds = new DadosBd(ct);
    }

    @JavascriptInterface // diz que a interação é sobre a interface JS
    public void DadosNecJogos() {
        dds.dadosNec(1);
    }
    @JavascriptInterface
    public void DadosRec(String jsonRecebido) {
        Log.d("JSON", "Recebi do JS: " + jsonRecebido);

        // converter para objeto:
        try {
            JSONObject obj = new JSONObject(jsonRecebido);
            String status = obj.getString("status");
            String operacao  = obj.getString("operacao");

            if ("sim".equals(status)){
                dds.dadosRec(jsonRecebido);
            } else {
                Log.d("JSON", "Status não concluído.");
            }
        } catch(Exception e){
//            e.printStackTrace();
            Log.e("JSON", "Erro ao processar JSON: " + jsonRecebido, e);
        }
    }

}
