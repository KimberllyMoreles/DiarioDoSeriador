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
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.aluno.diariodoseriador2.R;
import com.example.aluno.diariodoseriador2.activity.SerieActivity;
import com.example.aluno.diariodoseriador2.model.Serie;

/**
 * Created by aluno on 14/12/16.
 */
public class SerieDetalheFragment extends BaseFragment{

    private Serie serie;

    private RadioButton rbAbc, rbCw, rbHbo, rbNetflix;
    private TextView tvNome, tvDataIni, tvDataFim, tvTemporadas;
    private ImageView ivFoto;
    private VideoView vvVideo;


    public void setSerie(Serie serie) {
        this.serie = serie;
    }

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
        else {
            ivFoto.setImageResource(R.drawable.television);
        }

        //Card1
        rbAbc = (RadioButton) view.findViewById(R.id.rbABC_card1_fragmentDetalheSerie);
        //rbAbc.setChecked(true);
        rbCw = (RadioButton) view.findViewById(R.id.rbCW_card1_fragmentDetalheSerie);
        rbHbo = (RadioButton) view.findViewById(R.id.rbHBO_card1_fragmentDetalheSerie);
        rbNetflix = (RadioButton) view.findViewById(R.id.rbNetflix_card1_fragmentDetalheSerie);

        if (serie.emissora.equals(getContext().getResources().getString(R.string.tipo_abc))){
            rbAbc.setChecked(true);
        } else if (serie.emissora.equals(getContext().getResources().getString(R.string.tipo_cw))){
            rbCw.setChecked(true);
        } else if (serie.emissora.equals(getContext().getResources().getString(R.string.tipo_hbo))){
            rbHbo.setChecked(true);
        } else {
            rbNetflix.setChecked(true);
        }

        //Card2
        tvNome = (TextView) view.findViewById(R.id.tvNome_card2_fragmentdetalhe);
        tvDataIni = (TextView) view.findViewById(R.id.tvDataInicio_card2_fragmentdetalhe);
        tvDataFim = (TextView) view.findViewById(R.id.tvDataFim_card2_fragmentdetalhe);
        tvTemporadas = (TextView) view.findViewById(R.id.tvTemporadas_card2_fragmentdetalhe);

        tvNome.setText(serie.nome);
        tvDataIni.setText(serie.ano_inicio);
        tvDataFim.setText(serie.ano_fim);
        tvTemporadas.setText(serie.temporadas);

        //Card4
        vvVideo = (VideoView) view.findViewById(R.id.videoView_card4_frdetalheserie);
        final ImageView imageViewPlayVideo = (ImageView) view.findViewById(R.id.imageView_card4_fradetalheserie);
        imageViewPlayVideo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (serie.urlVideo != null){
                    imageViewPlayVideo.setVisibility(View.INVISIBLE);
                    vvVideo.setMediaController(new MediaController(getContext()));
                    vvVideo.setVideoURI(Uri.parse(serie.urlVideo));
                    vvVideo.start();
                } else {
                    Toast.makeText(getContext(), R.string.toast_message_noVideo, Toast.LENGTH_SHORT).show();
                }
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
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, serieEdicaoFragment).commit();
                break;
            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return true;
    }

}
