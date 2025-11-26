package etec.sp.gov.br.com.example.tdmath.model;
import etec.sp.gov.br.com.example.tdmath.model.Mapa;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        db.execSQL("CREATE TABLE IF NOT EXISTS usuario(" +
                "idUser INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR(150) NOT NULL," +
                "email VARCHAR(150) NOT NULL UNIQUE," +
                "senhaHash VARCHAR(250) NOT NULL," +
                "pontuacaoTotal int default 0," +
                "mapaAtual int default 1" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS conquista(" +
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
        OnInit(db);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
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
    private void OnInit(SQLiteDatabase db) {
        // iniciando multipla transação de dados
        // ideal para evitar lentidão, basicamente fazendo um unico acesso ao banco
        // ao invés de um para cada inserção

        db.beginTransaction();
        try { // tentar codigo
            inserirMp(db);
            inserirNvs(db);
            inserirOpr(db);
            db.setTransactionSuccessful();
        } catch (SQLException e) { //tratar erro
            throw new RuntimeException(e);
        } finally { // ação que sempre acontece
            db.endTransaction(); // fecha e envia o acesso ao banco
        }
    }
    private void inserirMp(SQLiteDatabase db){
        db.execSQL("INSERT INTO mapa(nome,ImgUrlMap) VALUES('soma', 'imgmap2');");
        db.execSQL("INSERT INTO mapa(nome,ImgUrlMap) VALUES('subtracao', 'imgmap3');");
        db.execSQL("INSERT INTO mapa(nome,ImgUrlMap) VALUES('multiplicacao', 'imgmap4');");
        db.execSQL("INSERT INTO mapa(nome,ImgUrlMap) VALUES('divisao', 'imgmap5');");
        db.execSQL("INSERT INTO mapa(nome,ImgUrlMap) VALUES('relacoes', 'imgmap6');");
        db.execSQL("INSERT INTO mapa(nome,ImgUrlMap) VALUES('geometria', 'imgmap12');");
        db.execSQL("INSERT INTO mapa(nome,ImgUrlMap) VALUES('prova', 'imgmap7');"); // revisão final
    }
    private void inserirNvs(SQLiteDatabase db){
        // o raw Query exige colocar sql como string direto para consultas mais completas
        Cursor mps = db.rawQuery("SELECT COUNT(*) FROM mapa", null);
        int Ttl = 0;

        // percorrer itens
        if (mps.moveToFirst()){
            Ttl = mps.getInt(0);// começar a contar do index 0
            mps.close();
        }
        int[] pont = {
                100, 150, 200, 200, 250, 350
        };
        for (int i = 1; i <= Ttl; i++) {
            for (int j = 0; j < pont.length; j++) {
                db.execSQL("INSERT INTO nivel(fkIdMap, pontuacao) VALUES(" + i + ","+ pont[j] + ");");
            }
        }
    }
    private void inserirOpr(SQLiteDatabase db) {
        Cursor mps = db.rawQuery("SELECT COUNT(*) FROM mapa", null);
        int TtlMp = 0;

        if (mps.moveToFirst()) {
            TtlMp = mps.getInt(0);
        }
        mps.close();

        for (int i = 1; i <= TtlMp; i++) { // cada mapa
            Cursor nvs = db.rawQuery("SELECT idNivel FROM nivel WHERE fkIdMap = ?", new String[]{String.valueOf(i)});

            String[] calcs;
            switch(i) {
                case 1: calcs = new String[]{ "+" }; break;
                case 2: calcs = new String[]{ "-" }; break;
                case 3: calcs = new String[]{ "*" }; break;
                case 4: calcs = new String[]{ "/" }; break;
                case 5: calcs = new String[]{ "<", ">" }; break;
                case 6: calcs = new String[]{ "geo" }; break;
                case 7: calcs = new String[]{ "+","-","*","/","<",">","geo" }; break;
                default: calcs = new String[0]; break;
            }

            if (nvs.moveToFirst()) {
                do {
                    int idNivel = nvs.getInt(0);
                    for (String op : calcs) {
                        db.execSQL("INSERT INTO operacaoNivel(fkIdNivel, operacao) VALUES(" + idNivel + ", '" + op + "');");
                    }
                } while (nvs.moveToNext());
            }
            nvs.close();
        }
    }
    public void testarBanco() {
        SQLiteDatabase dbRead = getReadableDatabase();

        // Tabelas que queremos testar
        String[] tabelas = {
                "usuario", "conquista", "config", "mapa", "nivel",
                "operacaoNivel", "progresso", "conquistaUser"
        };

        for (String tabela : tabelas) {
            Cursor c = dbRead.rawQuery("SELECT * FROM " + tabela, null);
            Log.d("TESTE_BD", "====== Tabela: " + tabela + " ======");
            Log.d("TESTE_BD", "Total de linhas: " + c.getCount());

            if (c.moveToFirst()) {
                do {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < c.getColumnCount(); i++) {
                        sb.append(c.getColumnName(i))
                                .append(": ")
                                .append(c.getString(i))
                                .append(" | ");
                    }
                    Log.d("TESTE_BD", sb.toString());
                } while (c.moveToNext());
            }

            c.close();
        }

        dbRead.close();
    }

}
