package etec.sp.gov.br.com.example.tdmath.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import etec.sp.gov.br.com.example.tdmath.model.Banco;

public class DadosBd {

    private final Banco bd;
    private SQLiteDatabase db;

    public DadosBd(Context ct) {
        bd = new Banco(ct);
    }

    public String dadosNec(int idUser) {
        db = bd.getReadableDatabase();
        Cursor cursor = null;
        String resultado = "[]";
        try {
            String sql = "SELECT n.idNivel, n.pontuacao, o.operacao " +
                    "FROM progresso p " +
                    "JOIN nivel n ON p.fkIdNivel = n.idNivel " +
                    "LEFT JOIN operacaoNivel o ON n.idNivel = o.fkIdNivel " +
                    "WHERE p.fkIdUser = ?";

            cursor = db.rawQuery(sql, new String[]{ String.valueOf(idUser) });

            if(cursor.moveToFirst()) {
                JSONArray niveis = null;
                do {
                    int idNivel = cursor.getInt(0);
                    int pont = cursor.getInt(1);
                    String op = cursor.getString(2);

                    JSONObject obj = new JSONObject();
                    obj.put("idNivel", idNivel);
                    obj.put("operacao", op);
                    niveis.put(obj);
                } while(cursor.moveToNext());
                resultado = niveis.toString();
            }

        } catch(Exception e){
            e.printStackTrace();
        } finally {
            if(cursor != null) cursor.close();
            db.close();
        }
        return resultado;
    }

    public void dadosRec(){

    }

}
