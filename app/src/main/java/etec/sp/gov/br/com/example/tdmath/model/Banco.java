package etec.sp.gov.br.com.example.tdmath.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;



public class Banco extends SQLiteOpenHelper {
    //https://www.devmedia.com.br/criando-um-crud-com-android-studio-e-sqlite/32815
    private static final String nomeBd = "tdmath.db";
    private static final Integer versaoBd = 1;
    public Banco(@Nullable Context context) {
        super(context, nomeBd, null, versaoBd);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");

        db.execSQL("CREATE TABLE IF NOT EXISTS usuario(" +
                "idUser INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR(100) NOT NULL UNIQUE," +
                "email VARCHAR(150) NOT NULL UNIQUE," +
                "senhaHash VARCHAR(250) NOT NULL," +
                "pontuacaoTotal int default 0," +
                "mapaAtual int default 1" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS conquistaBase(" +
                "idConq INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR(100) NOT NULL," +
                "descricao TEXT NOT NULL," +
                "imgUrlConq VARCHAR(250) NOT NULL" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS config(" +
                "fkIdUser INTEGER PRIMARY KEY," +
                "nivelSom INTEGER NOT NULL," +
                "tamanhoUI INTEGER NOT NULL," +
                "FOREIGN KEY(fkIdUser) REFERENCES usuario(idUser)" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS mapa(" +
                "idMapa INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR(100) NOT NULL," +
                "ImgUrlMap VARCHAR(250) NOT NULL" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS nivel(" +
                "idNivel INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "pontuacao int default 150," +
                "fkIdMap INTEGER NOT NULL," +
                "FOREIGN KEY(fkIdMap) REFERENCES mapa(idMapa)" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS operacaoNivel(" +
                "fkIdNivel INTEGER NOT NULL," +
                "operacao VARCHAR(100) NOT NULL," +
                "PRIMARY KEY (fkIdNivel, operacao)," +
                "FOREIGN KEY(fkIdNivel) REFERENCES nivel(idNivel)" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS progresso(" +
                "fkIdUser INTEGER NOT NULL," +
                "fkIdNivel INTEGER NOT NULL," +
                "pontuacao INTEGER DEFAULT 0," +
                "pontuacaoTotal int default 0," +
                "PRIMARY KEY (fkIdUser, fkIdNivel)," +
                "FOREIGN KEY (fkIdUser) REFERENCES usuario(idUser)," +
                "FOREIGN KEY (fkIdNivel) REFERENCES nivel(idNivel)" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS conquistaUser(" +
                "fkIdUser INTEGER NOT NULL," +
                "fkIdConq INTEGER NOT NULL," +
                "dataConquista TEXT NOT NULL," +
                "PRIMARY KEY (fkIdUser, fkIdConq)," +
                "FOREIGN KEY (fkIdUser) REFERENCES usuario(idUser)," +
                "FOREIGN KEY (fkIdConq) REFERENCES conquista(idConq)" +
                ")");


        db.beginTransaction();// permite inserções em massa
        try {
            ContentValues values1 = new ContentValues();
            values1.put("nome", "somary");
            values1.put("ImgUrlMap", "imgm1");
            db.insert("mapa", null, values1);

            ContentValues values2 = new ContentValues();
            values2.put("nome", "subvitryl");
            values2.put("ImgUrlMap", "imgm2");
            db.insert("mapa", null, values2);

            ContentValues values3 = new ContentValues();
            values3.put("nome", "multil");
            values3.put("ImgUrlMap", "imgm3");
            db.insert("mapa", null, values3);

            ContentValues values4 = new ContentValues();
            values4.put("nome", "division");
            values4.put("ImgUrlMap", "imgm4");
            db.insert("mapa", null, values4);

            ContentValues values5 = new ContentValues();
            values5.put("nome", "descanso");
            values5.put("ImgUrlMap", "imgm5");
            db.insert("mapa", null, values5);

            ContentValues values6 = new ContentValues();
            values6.put("nome", "fract");
            values6.put("ImgUrlMap", "imgm6");
            db.insert("mapa", null, values6);

            ContentValues values7 = new ContentValues();
            values7.put("nome", "cortando caminhos");
            values7.put("ImgUrlMap", "imgm7");
            db.insert("mapa", null, values7);

            ContentValues values8 = new ContentValues();
            values8.put("nome", "prov");
            values8.put("ImgUrlMap", "imgm8");
            db.insert("mapa", null, values8);

            ContentValues values9 = new ContentValues();
            values9.put("nome", "dencanso");
            values9.put("ImgUrlMap", "imgm9");
            db.insert("mapa", null, values9);

            ContentValues values10 = new ContentValues();
            values10.put("nome", "mathworld");
            values10.put("ImgUrlMap", "imgm10");
            db.insert("mapa", null, values10);

            db.setTransactionSuccessful(); // Marca a transação como bem-sucedida
        } finally {
            db.endTransaction(); // Confirma as inserções
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS conquista");
        db.execSQL("DROP TABLE IF EXISTS config");
        db.execSQL("DROP TABLE IF EXISTS mapa");
        db.execSQL("DROP TABLE IF EXISTS nivel");
        db.execSQL("DROP TABLE IF EXISTS operacaoNivel");
        db.execSQL("DROP TABLE IF EXISTS progresso");
        db.execSQL("DROP TABLE IF EXISTS conquistaUser");
        onCreate(db);
    }

}
