package etec.sp.gov.br.com.example.tdmath.model;

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
