package etec.sp.gov.br.com.example.tdmath.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import etec.sp.gov.br.com.example.tdmath.model.Banco;
import etec.sp.gov.br.com.example.tdmath.model.Mapa;

public class MapController {
    private SQLiteDatabase db;
    private Banco bd;

    public MapController(Context context){
        bd = new Banco(context);
    }
    public ArrayList<Mapa> consultaMapas(){
        ArrayList<Mapa> lista = new ArrayList<>();

        db = bd.getReadableDatabase();
        Cursor dados = db.query("mapa", null, null, null, null, null, null);

        //acho que funciona tipo foreach
        if (dados.moveToFirst()){
            do {
                    @SuppressLint("Range")
                    int id = dados.getInt(dados.getColumnIndex("idMapa"));
                    @SuppressLint("Range")
                    String nome = dados.getString(dados.getColumnIndex("nome"));
                    @SuppressLint("Range")
                    String img = dados.getString((dados.getColumnIndex("ImgUrlMap")));
                    lista.add(new Mapa(id, nome, img));
            } while (dados.moveToNext());
        }else {
            return null;
        }
        dados.close();
        db.close();
        return lista;
    }

}
