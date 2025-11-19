package etec.sp.gov.br.com.example.tdmath.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import etec.sp.gov.br.com.example.tdmath.model.Banco;

public class UserController {
    //https://www.devmedia.com.br/criando-um-crud-com-android-studio-e-sqlite/32815
    private SQLiteDatabase db;
    private final Banco bd;

    public UserController(Context context) {
        bd = new Banco(context);
    }

    public boolean existeUsuario() {
        db = bd.getReadableDatabase();
        Cursor cursor = null;
        boolean existe = false;

        try {
            cursor = db.query(
                    "usuario",         // tabela
                    new String[]{"idUser"}, // só precisa de alguma coluna
                    null,              // sem filtro, pega todos
                    null,
                    null,
                    null,
                    null,
                    "1"                // LIMIT 1 para só verificar se existe
            );

            if (cursor.moveToFirst()) {
                existe = true; // encontrou pelo menos 1 usuário
            }
        } catch (Exception e) {
            Log.e("UserController", "Erro ao verificar usuários: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return existe;
    }

    public void consultaUsers() {
        ArrayList<String> lista = new ArrayList<>();

        db = bd.getReadableDatabase();
        Cursor dados = null;
        try {
            dados = db.query("usuario", null, null, null, null, null, null);
            if (dados.moveToFirst()) {
                do {
                    int id = dados.getInt(dados.getColumnIndexOrThrow("idUser"));
                    String email = dados.getString(dados.getColumnIndexOrThrow("email"));
                    String name = dados.getString(dados.getColumnIndexOrThrow("nome"));
                    int mapaAtual = dados.getInt(dados.getColumnIndexOrThrow("mapaAtual"));

                    String info = "ID: " + id +
                            ", Email: " + email +
                            ", nome: " + name +
                            ", Mapa Atual: " + mapaAtual;

                    lista.add(info);
                    Log.d("UserController", info);
                } while (dados.moveToNext());
            } else {
                Log.d("UserController", "Nenhum usuário encontrado.");
            }
        } catch (Exception e) {
            Log.e("UserController", "Erro ao consultar usuários: " + e.getMessage());
        } finally {
            if (dados != null) dados.close();
            db.close();
        }
        Log.d("usuarios", "consultaUsers: " + lista);
    }

    public Boolean cadastro(String nome, String email, String senha) {
        db = bd.getWritableDatabase();

        Cursor cursor = db.query(
                "usuario",
                new String[]{"idUser"},
                "email = ?",
                new String[]{email.toLowerCase()},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return false; // usuário já existe
        }
        cursor.close();

        String senhaHash;
        //classe android, funciona como container de pares chave-valor("senha", senha)
        ContentValues values;
        //metodo insert vai devolver result do tipo long
        long resultado;

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

        values = new ContentValues();
        values.put("nome", nome.toLowerCase());
        values.put("email", email.toLowerCase());
        values.put("senhaHash", senhaHash);

        resultado = db.insert("usuario",null,values);
        Log.e("CADASTRO_DEBUG", "Resultado insert: " + resultado + " | Email: " + email);

        db.close();

        if (resultado == -1){
            return false;
        } else {
            return true;
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
