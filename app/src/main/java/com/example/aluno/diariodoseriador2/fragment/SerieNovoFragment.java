package com.example.aluno.diariodoseriador2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aluno.diariodoseriador2.R;
import com.example.aluno.diariodoseriador2.activity.SeriesActivity;

/**
 * Created by aluno on 12/12/16.
 */
public class SerieNovoFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        ((SeriesActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_novaserie);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nova_serie, container, false);
        return view;
    }
}
