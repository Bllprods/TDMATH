package etec.sp.gov.br.com.example.tdmath.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import etec.sp.gov.br.com.example.tdmath.model.Banco;
import etec.sp.gov.br.com.example.tdmath.model.Mapa;
import etec.sp.gov.br.com.example.tdmath.model.nivel;

public class MapController {
    private SQLiteDatabase db;
    private Banco bd;

    public MapController(Context context){
        bd = new Banco(context);
    }

    public ArrayList<Mapa> consultaMapas() {
        ArrayList<Mapa> lista = new ArrayList<>();
        SQLiteDatabase db = bd.getReadableDatabase();

        Cursor dados = null;
        try {
            dados = db.query("mapa", null, null, null, null, null, null);

            if (dados.moveToFirst()) {
                do {
                    @SuppressLint("Range")
                    int id = dados.getInt(dados.getColumnIndex("idMapa"));
                    @SuppressLint("Range")
                    String nome = dados.getString(dados.getColumnIndex("nome"));
                    @SuppressLint("Range")
                    String img = dados.getString(dados.getColumnIndex("ImgUrlMap"));

                    Mapa m = new Mapa(id, nome, img);
                    m.setNiveis(buscarNiveisPorMapa(db, id)); // associa níveis
                    lista.add(m);

                } while (dados.moveToNext());
            }

            for (Mapa m : lista) {
                Log.d("MapController", "ID: " + m.getIdMapa() +
                        ", Nome: " + m.getNome() +
                        ", Img: " + m.getImgUrlMap() +
                        ", Níveis: " + m.getNiveis().size());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (dados != null) dados.close();
            if (db != null) db.close();
        }

        return lista;
    }

    private List<nivel> buscarNiveisPorMapa(SQLiteDatabase db, int idMapa) {
        List<nivel> niveis = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(
                    "SELECT idNivel, pontuacao, fkIdMap FROM nivel WHERE fkIdMap = ?",
                    new String[]{String.valueOf(idMapa)}
            );

            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range")
                    int idNivel = cursor.getInt(cursor.getColumnIndex("idNivel"));
                    @SuppressLint("Range")
                    int pontuacao = cursor.getInt(cursor.getColumnIndex("pontuacao"));
                    @SuppressLint("Range")
                    int fkIdMap = cursor.getInt(cursor.getColumnIndex("fkIdMap"));

                    niveis.add(new nivel(idNivel, pontuacao, fkIdMap));
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e("MapController", "Erro ao buscar níveis do mapa " + idMapa + ": " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }
        return niveis;
    }
}
