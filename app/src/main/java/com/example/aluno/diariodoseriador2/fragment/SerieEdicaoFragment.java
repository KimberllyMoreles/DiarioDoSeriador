package com.example.aluno.diariodoseriador2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aluno.diariodoseriador2.R;
import com.example.aluno.diariodoseriador2.activity.SerieActivity;

/**
 * Created by aluno on 14/12/16.
 */
public class SerieEdicaoFragment extends BaseFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        ((SerieActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_edicaoserie);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edicao_serie, container, false);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_edicaoserie, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuitem_salvar:
                Toast.makeText(getContext(), "salvar", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuitem_excluir:
                Toast.makeText(getContext(), "excluir", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
