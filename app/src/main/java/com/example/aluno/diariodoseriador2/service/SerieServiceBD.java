package com.example.aluno.diariodoseriador2.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
                " temporadas text not null);";
        Log.d(TAG, "Criando a tabela serie. Aguarde ...");
        sqLiteDatabase.execSQL(sql);
        Log.d(TAG, "Tabela serie criada com sucesso.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
