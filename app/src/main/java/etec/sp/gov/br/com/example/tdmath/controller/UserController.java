package etec.sp.gov.br.com.example.tdmath.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import etec.sp.gov.br.com.example.tdmath.model.Banco;

public class UserController {
    //https://www.devmedia.com.br/criando-um-crud-com-android-studio-e-sqlite/32815
    private SQLiteDatabase db;
    private final Banco bd;

    public UserController(Context context){
        bd = new Banco(context);
    }

    public String cadastro(String nome, String email, String senha) {
        //classe android, funciona como container de pares chave-valor("senha", senha)
        ContentValues values;
        //metodo insert vai devolver result do tipo long
        long resultado;

        String senhaHash;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] digest = md.digest(senha.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02X", 0xFF & b)); // transforma em hex
            }
            senhaHash = sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException("Erro ao gerar hash da senha", e);
        }

        db = bd.getWritableDatabase();
        values = new ContentValues();
        values.put("nome", nome.toLowerCase());
        values.put("email", email.toLowerCase());
        values.put("senhaHash", senhaHash);

        resultado = db.insert("usuario",null,values);
        db.close();

        if (resultado == -1){
            return "Erro ao inserir registro";
        } else {
            return "registro Inserido com sucesso";
        }
    }

    public boolean Logar(String user, String senha){
        Cursor resultado;
        String[] values = {
                "nome","email", "senhaHash"
        };
        String selection = "email = ? OR nome = ?";
        String[] selectionArgs = { user.toLowerCase(), user.toLowerCase() };
        db = bd.getWritableDatabase();

        resultado = db.query("usuario", values, selection,selectionArgs, null, null, null, null);
        if (resultado.moveToFirst()){ //encontrar usuario
            String senhaBd = resultado.getString(resultado.getColumnIndexOrThrow("senhaHash"));
            resultado.close();

            String senhaHash;
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-512");
                byte[] digest = md.digest(senha.getBytes("UTF-8"));
                StringBuilder sb = new StringBuilder();
                for (byte b : digest) {
                    sb.append(String.format("%02X", 0xFF & b)); // transforma em hex
                }
                senhaHash = sb.toString();
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                throw new RuntimeException("Erro ao gerar hash da senha", e);
            }
            return senhaBd.equals(senhaHash); //true se a senha bater

        } else {
            resultado.close();
            db.close();
            return false;
        }
    }

}
