package etec.sp.gov.br.com.example.tdmath.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

import etec.sp.gov.br.com.example.tdmath.model.nivel;
public class Mapa {
    private int idMapa;
    private String nome;
    private String imgUrlMap;
    private List<nivel> niveis;

    public Mapa(int idMapa, String nome, String imgUrlMap) {
        this.idMapa = idMapa;
        this.nome = nome;
        this.imgUrlMap = imgUrlMap;
    }
    public Mapa(int idMapa, String nome, String imgUrlMap, List<nivel> niveis) {
        this.idMapa = idMapa;
        this.nome = nome;
        this.imgUrlMap = imgUrlMap;
        this.niveis = niveis;
    }

    public int getIdMapa() { return idMapa; }
    public String getNome() { return nome; }
    public String getImgUrlMap() { return imgUrlMap; }
    public List<nivel> getNiveis() { return niveis; }
    public void setNiveis(List<nivel> niveis) { this.niveis = niveis; }
}
