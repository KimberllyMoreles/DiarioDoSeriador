package com.example.aluno.diariodoseriador2.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aluno.diariodoseriador2.R;
import com.example.aluno.diariodoseriador2.activity.SerieActivity;
import com.example.aluno.diariodoseriador2.activity.SeriesActivity;
import com.example.aluno.diariodoseriador2.adapter.SeriesAdapter;
import com.example.aluno.diariodoseriador2.model.Serie;
import com.example.aluno.diariodoseriador2.service.SerieServiceBD;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aluno on 09/12/16.
 */
public class SeriesFragment extends BaseFragment implements SearchView.OnQueryTextListener{

    protected RecyclerView recyclerview;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Serie> series;
    private SerieServiceBD serieServiceBD;
    private String tipo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //cria o bd, se ainda não estiver sido criado
        //ou obtém a instância de acesso ao bd
        serieServiceBD = SerieServiceBD.getInstance(getContext());

        //se há argumentos, o armazena para filtar a lista de series
        if (getArguments() != null) {
            this.tipo = getArguments().getString("tipo");
        }

        setHasOptionsMenu(true);

        ((SeriesActivity)getActivity()).getSupportActionBar().setTitle(R.string.titulo_fragmentseries);

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == android.content.pm.PackageManager.PERMISSION_GRANTED) {

        }else{

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new Task().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_series, container, false);

        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview_fragmentseries);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setHasFixedSize(true);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(OnRefreshListener());
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1, R.color.refresh_progress_2, R.color.refresh_progress_3);

        return view;
    }

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Task().execute();
            }
        };
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_series, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menuitem_pesquisar).getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getString(R.string.hint_pesquisar));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<Serie> serieList = new ArrayList<>();

        for (Serie serie : series){
            if (serie.nome.contains(newText)){
                serieList.add(serie);
            }
        }

        SeriesAdapter adapter = new SeriesAdapter(getContext(), serieList, onClickSerie());
        recyclerview.setAdapter(adapter);

        return true;
    }

    /*
        Este método utiliza a interface declarada na classe SeriesAdapter para tratar o evento onClick do item da lista.
     */
    protected SeriesAdapter.SerieOnClickListener onClickSerie() {
        //chama o contrutor da interface (implícito) para criar uma instância da interface declarada no adaptador.
        return new SeriesAdapter.SerieOnClickListener() {
            // Aqui trata o evento onItemClick.
            @Override
            public void onClickSerie(View view, int idx) {
                //armazena o serie que foi clicado
                Serie serie = series.get(idx);
                //chama outra Activity para detalhar ou editar o serie clicado pelo usuário
                Intent intent = new Intent(getContext(), SerieActivity.class); //configura uma Intent explícita
                intent.putExtra("serie", serie); //inseri um extra com a referência para o objeto Serie
                intent.putExtra("qualFragmentAbrir", "SerieDetalheFragment");
                startActivity(intent);
            }
        };
    }

    /*
        Classe interna para operaçẽos assíncronas na base de dados.
     */
    private class Task extends AsyncTask<Void, Void, List<Serie>> { //<Params, Progress, Result>

        List<Serie> series;

        @Override
        protected List<Serie> doInBackground(Void... voids) {
            //busca os series em background, em uma thread exclusiva para esta tarefa.
            if(SeriesFragment.this.tipo.equals(getString(R.string.tabs_abc))){
                return serieServiceBD.getByTipo(getString(R.string.tabs_abc));
            }else if(SeriesFragment.this.tipo.equals(getString(R.string.tabs_cw))) {
                return serieServiceBD.getByTipo(getString(R.string.tabs_cw));
            }if(SeriesFragment.this.tipo.equals(getString(R.string.tabs_hbo))) {
                return serieServiceBD.getByTipo(getString(R.string.tabs_hbo));
            }else if(SeriesFragment.this.tipo.equals(getString(R.string.tabs_netflix))) {
                    return serieServiceBD.getByTipo(getString(R.string.tabs_netflix));
            }else if(SeriesFragment.this.tipo.equals(getString(R.string.tabs_todos))) {
                return serieServiceBD.getAll();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Serie> series) {
            super.onPostExecute(series);
            //copia a lista de series para uso no onQueryTextChange()
            SeriesFragment.this.series = series;
            //atualiza a view na UIThread
            recyclerview.setAdapter(new SeriesAdapter(getContext(), series, onClickSerie())); //Context, fonte de dados, tratador do evento onClick
            swipeRefreshLayout.setRefreshing(false);
        }
    }//fim classe interna

}
