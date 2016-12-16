package com.example.aluno.diariodoseriador2.activity;

import android.os.Bundle;
import android.util.Log;

import com.example.aluno.diariodoseriador2.R;
import com.example.aluno.diariodoseriador2.fragment.SerieDetalheFragment;
import com.example.aluno.diariodoseriador2.fragment.SerieNovoFragment;
import com.example.aluno.diariodoseriador2.model.Serie;

/**
 * Created by aluno on 09/12/16.
 */
public class SerieActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_series);

        String msg = (String) getIntent().getCharSequenceExtra("qualFragmentAbrir");

        if(msg.equals("SerieNovoFragment")){
            replaceFragment(R.id.fragment_container, new SerieNovoFragment());
        }

        else {
            if (msg.equals("SerieDetalheFragment")){

                SerieDetalheFragment serieDetalheFragment = new SerieDetalheFragment();
                replaceFragment(R.id.fragment_container, serieDetalheFragment);

                Serie serie = (Serie) getIntent().getSerializableExtra("serie");
                Log.d(TAG, "Objeto série recebido: " + serie.toString());

                serieDetalheFragment.setSerie(serie);
            }
        }
    }

}
