package com.example.aluno.diariodoseriador2.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.aluno.diariodoseriador2.model.Serie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aluno on 14/12/16.
 */
public class SerieServiceBD extends SQLiteOpenHelper {

    private static String TAG = "serieserviceBD";
    private static String NAME = "serie.sqlite";
    private static int VERSION = 1;
    private static SerieServiceBD serieServiceBD = null;

    private SerieServiceBD(Context context) {
        super(context, NAME, null, VERSION);
        getWritableDatabase();
    }

    public static SerieServiceBD getInstance(Context context){
        if (serieServiceBD == null){
            serieServiceBD = new SerieServiceBD(context);
            return serieServiceBD;
        }
        return serieServiceBD;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table if not exists serie" +
                "( _id integer primary key autoincrement, " +
                " nome text not null, " +
                "url_foto text, " +
                "url_video text, " +
                " ano_inicio text not null, " +
                " ano_fim text, " +
                " emissora text not null, " +
                " temporadas text not null);";
        Log.d(TAG, "Criando a tabela serie. Aguarde ...");
        sqLiteDatabase.execSQL(sql);
        Log.d(TAG, "Tabela serie criada com sucesso.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /*
        CRUD
     */
    public List<Serie> getAll(){
        //abre a conexão com o bd
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        try {
            return toList(sqLiteDatabase.rawQuery("select * from serie", null));
        }finally {
            sqLiteDatabase.close(); //libera o recurso
        }

    }

    public List<Serie> getByTipo(String emissora){
        //abre a conexão com o bd
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        try{
            return toList(sqLiteDatabase.rawQuery("select * from serie where emissora = '" + emissora + "'", null));
        }finally {
            sqLiteDatabase.close(); //libera o recurso
        }

    }

    public long save(Serie serie){

        SQLiteDatabase db = getWritableDatabase(); //abre a conexão com o banco

        try{
            //tupla com: chave, valor
            ContentValues values = new ContentValues();
            values.put("nome", serie.nome);
            values.put("ano_inicio", serie.ano_inicio);
            values.put("ano_fim", serie.ano_fim);
            values.put("url_foto", serie.urlFoto);
            values.put("url_video", serie.urlVideo);
            values.put("temporadas", serie.temporadas);
            values.put("emissora", serie.emissora);

            //realiza a operação
            if(serie._id == null){
                //insere no banco de dados
                return db.insert("serie", null, values);
            }else{
                //altera no banco de dados
                values.put("__id", serie._id);
                return db.update("serie", values, "_id=" + serie._id, null);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            db.close(); //libera o recurso
        }

        return 0L; //caso não realize a operação
    }

    public long delete(Serie serie){
        SQLiteDatabase db = getWritableDatabase(); //abre a conexão com o banco
        try{
            return db.delete("serie", "_id=?", new String[]{String.valueOf(serie._id)});
        }
        finally {
            db.close(); //libera o recurso
        }
    }

    /*
        Utilitários
     */
    //converte de Cursor em uma List
    private List<Serie> toList(Cursor c) {
        List<Serie> series = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Serie serie = new Serie();

                // recupera os atributos do cursor para o serie
                serie._id = c.getLong(c.getColumnIndex("_id"));
                serie.nome = c.getString(c.getColumnIndex("nome"));
                serie.ano_inicio = c.getString(c.getColumnIndex("ano_inicio"));
                serie.ano_fim = c.getString(c.getColumnIndex("ano_fim"));
                serie.urlFoto = c.getString(c.getColumnIndex("url_foto"));
                serie.urlVideo = c.getString(c.getColumnIndex("url_video"));
                serie.temporadas = c.getString(c.getColumnIndex("temporadas"));
                serie.emissora = c.getString(c.getColumnIndex("emissora"));

                series.add(serie);

            } while (c.moveToNext());
        }

        return series;
    }
}
