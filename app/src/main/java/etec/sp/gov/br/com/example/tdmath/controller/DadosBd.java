package etec.sp.gov.br.com.example.tdmath.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.webkit.JavascriptInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import etec.sp.gov.br.com.example.tdmath.model.Banco;

public class DadosBd {

    private final Banco bd;
    private SQLiteDatabase db;

    public DadosBd(Context ct) {
        bd = new Banco(ct);
    }

    private int getPontuacaoTotalAcumulada(SQLiteDatabase db, int fkIdUser) {
        Cursor cursor = db.rawQuery(
                "SELECT SUM(pontuacao) FROM progresso WHERE fkIdUser = ?",
                new String[]{String.valueOf(fkIdUser)}
        );
        int total = 0;
        if (cursor.moveToFirst()) {
            total = cursor.getInt(0);
        }
        if (cursor != null) cursor.close();
        return total;
    }

    public String dadosNec(int idUser) {
        db = bd.getReadableDatabase();
        Cursor cursor = null;
        String resultado ="[]";
        try {
            String sql = "SELECT n.idNivel, n.pontuacao, o.operacao, p.pontuacaoTotal " +
                    "FROM progresso p " +
                    "JOIN nivel n ON p.fkIdNivel = n.idNivel " +
                    "LEFT JOIN operacaoNivel o ON n.idNivel = o.fkIdNivel " +
                    "WHERE p.fkIdUser = ?";

            cursor = db.rawQuery(sql, new String[]{ String.valueOf(idUser) });

            if(cursor.moveToFirst()) {
                JSONArray niveis = new JSONArray();
                do {
                    int idNivel = cursor.getInt(0);
                    String op = cursor.getString(2);

                    JSONObject obj = new JSONObject();
                    obj.put("idNivel", idNivel);
                    obj.put("operacao", op);
                    niveis.put(obj);
                } while(cursor.moveToNext());
                resultado = niveis.toString();
                Log.d("JSON", "dadosNec: " + resultado);
            }

        } catch(Exception e){
            e.printStackTrace();
        } finally {
            if(cursor != null) cursor.close();
        }
        return resultado;
    }

    @JavascriptInterface
    public void dadosRec(String dadosRecebidos) {
        Log.d("JSON", "Dados recebidos do JavaScript: " + dadosRecebidos);

        final int FK_ID_USER = 1;

        try {
            JSONObject obj = new JSONObject(dadosRecebidos);
            SQLiteDatabase db = bd.getWritableDatabase();

            int idNivel = obj.getInt("idNivel");
            int pontuacaoNova = 100;
            String status = obj.getString("status");
            String operacao = obj.getString("operacao");

            int pontuacaoAntiga = 0;
            Cursor cursor = db.rawQuery(
                    "SELECT pontuacao FROM progresso WHERE fkIdUser = ? AND fkIdNivel = ?",
                    new String[]{String.valueOf(FK_ID_USER), String.valueOf(idNivel)}
            );
            if (cursor.moveToFirst()) {
                pontuacaoAntiga = cursor.getInt(0);
            }
            if (cursor != null) cursor.close();

            int totalAtualGlobal = getPontuacaoTotalAcumulada(db, FK_ID_USER);

            int novaPontuacaoTotalGlobal = (totalAtualGlobal - pontuacaoAntiga) + pontuacaoNova;

            String deleteSql = "DELETE FROM progresso WHERE fkIdUser = " + FK_ID_USER + " AND fkIdNivel = " + idNivel;
            db.execSQL(deleteSql);

            String insertSql = "INSERT INTO progresso (fkIdUser, fkIdNivel, pontuacao, pontuacaoTotal) " +
                    "VALUES(" + FK_ID_USER + ", " + idNivel + ", " + pontuacaoNova + ", " +
                    novaPontuacaoTotalGlobal + ")";

            db.execSQL(insertSql);

            Log.i("DadosBd", "Progresso salvo (via execSQL) e Total Atualizado: " + novaPontuacaoTotalGlobal);

            db.close();

        } catch (Exception e) {
            Log.e("JSON", "Erro ao processar e salvar JSON com execSQL: ", e);
        }
    }
}