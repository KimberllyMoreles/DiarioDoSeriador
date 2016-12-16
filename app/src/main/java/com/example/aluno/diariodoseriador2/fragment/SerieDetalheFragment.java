package com.example.aluno.diariodoseriador2.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.aluno.diariodoseriador2.R;
import com.example.aluno.diariodoseriador2.activity.SerieActivity;
import com.example.aluno.diariodoseriador2.model.Serie;

/**
 * Created by aluno on 14/12/16.
 */
public class SerieDetalheFragment extends BaseFragment{

    private Serie serie;

    private RadioButton rbAbc, rbCw, rbHbo, rbNetflix;
    private EditText etNome, etDataIni, etDataFim, etTemporadas, etUrlVideo;
    private ImageView ivFoto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        ((SerieActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_detalheserie);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhe_serie, container, false);

        //mapeia e inicializa os componentes da UI
        //Card0
        ivFoto = (ImageView) view.findViewById(R.id.imageView_card_detalhe_serie);
        if (serie.urlFoto != null){
            ivFoto.setImageURI(Uri.parse(serie.urlFoto));
        }
        //Card1
        rbAbc = (RadioButton) view.findViewById(R.id.rbABC_card1_fragmentDetalheSerie);
        //rbAbc.setChecked(true);
        rbCw = (RadioButton) view.findViewById(R.id.rbCW_card1_fragmentDetalheSerie);
        rbHbo = (RadioButton) view.findViewById(R.id.rbHBO_card1_fragmentDetalheSerie);
        rbNetflix = (RadioButton) view.findViewById(R.id.rbNetflix_card1_fragmentDetalheSerie);
        rbNetflix.setChecked(true);

        //Card2
        etNome = (EditText) view.findViewById(R.id.etNome_card2_fredicaoserie);
        etDataIni = (EditText) view.findViewById(R.id.etDataIni_card2_fredicaoserie);
        etDataFim = (EditText) view.findViewById(R.id.etDataFim_card2_fredicaoserie);
        etTemporadas = (EditText) view.findViewById(R.id.etTemporadas_card2_fredicaoserie);

        //Card4
        etUrlVideo = (EditText) view.findViewById(R.id.etURLVideo__card4_fredicaoserie);
        etUrlVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cria uma Intent
                //primeiro argumento: ação ACTION_PICK "escolha um item a partir dos dados e retorne o seu URI"
                //segundo argumento: URI
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                //inicializa uma Activity. Neste caso, uma que forneca acesso a galeria de imagens do dispositivo.
                startActivityForResult(Intent.createChooser(intent, "Selecione um video"), 0);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_detalheserie, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuitem_editar:
                //Toast.makeText(getContext(), "editar", Toast.LENGTH_SHORT).show();
                SerieEdicaoFragment serieEdicaoFragment = new SerieEdicaoFragment();
                serieEdicaoFragment.setSerie(serie);
                replaceFragment(R.id.fragment_container, serieEdicaoFragment);
                break;
            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return true;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }
}
