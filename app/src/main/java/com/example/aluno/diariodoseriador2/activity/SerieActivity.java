package com.example.aluno.diariodoseriador2.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.aluno.diariodoseriador2.R;
import com.example.aluno.diariodoseriador2.fragment.SerieNovoFragment;

/**
 * Created by aluno on 09/12/16.
 */
public class SerieActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_serie);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_serie);
        setSupportActionBar(toolbar);

        String msg = (String) getIntent().getCharSequenceExtra("qualFragmentAbrir");

        if(msg.equals("qualFragmentAbrir")){
            replaceFragment(R.id.fragment_container, new SerieNovoFragment());
        }
    }
}
