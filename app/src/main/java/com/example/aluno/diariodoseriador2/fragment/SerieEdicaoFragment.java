package com.example.aluno.diariodoseriador2.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.example.aluno.diariodoseriador2.service.SerieServiceBD;

/**
 * Created by aluno on 14/12/16.
 */
public class SerieEdicaoFragment extends BaseFragment {

    private Serie serie;
    private final String SAVE = "save";
    private final String DELETE = "delete";
    private SerieServiceBD serieServiceBD;

    private RadioButton rbAbc, rbCw, rbHbo, rbNetflix;
    private EditText etNome, etDataIni, etDataFim, etTemporadas, etUrlVideo;
    private ImageView ivFoto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);


        serieServiceBD = SerieServiceBD.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edicao_serie, container, false);

        //um título para a janela
        ((SerieActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_edicaoserie);



        //mapeia e inicializa os componentes da UI
        //Card0
        ivFoto = (ImageView) view.findViewById(R.id.imv_card0_fredicaoserie);
        if (serie.urlFoto != null){
            ivFoto.setImageURI(Uri.parse(serie.urlFoto));
        }
        ivFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cria uma Intent
                //primeiro argumento: ação ACTION_PICK "escolha um item a partir dos dados e retorne o seu URI"
                //segundo argumento: refina a ação para arquivos de imagem, retornando um URI
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //inicializa uma Activity. Neste caso, uma que forneca acesso a galeria de imagens do dispositivo.
                startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 0);
            }
        });

        //Card1
        rbAbc = (RadioButton) view.findViewById(R.id.rb_abc_card1_fredicaoserie);
        //rbAbc.setChecked(true);
        rbCw = (RadioButton) view.findViewById(R.id.rb_cw_card1_fredicaoserie);
        rbHbo = (RadioButton) view.findViewById(R.id.rb_hbo_card1_fredicaoserie);
        rbNetflix = (RadioButton) view.findViewById(R.id.rb_netflix_card1_fredicaoserie);

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
        etNome = (EditText) view.findViewById(R.id.etNome_card2_fredicaoserie);
        etDataIni = (EditText) view.findViewById(R.id.etDataIni_card2_fredicaoserie);
        etDataFim = (EditText) view.findViewById(R.id.etDataFim_card2_fredicaoserie);
        etTemporadas = (EditText) view.findViewById(R.id.etTemporadas_card2_fredicaoserie);

        etNome.setText(serie.nome);
        etDataIni.setText(serie.ano_inicio);
        etDataFim.setText(serie.ano_fim);
        etTemporadas.setText(serie.temporadas);

        //Card4
        etUrlVideo = (EditText) view.findViewById(R.id.etURLVideo__card4_fredicaoserie);
        if(serie.urlVideo != null){
            etUrlVideo.setText(serie.urlVideo);
        }
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
        inflater.inflate(R.menu.menu_fragment_edicaoserie, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuitem_salvar:
                //Toast.makeText(getContext(), "salvo", Toast.LENGTH_SHORT).show();
                if(serie.urlFoto != null &&
                        !etNome.getText().toString().isEmpty() &&
                        !etDataIni.getText().toString().isEmpty() &&
                        !etDataFim.getText().toString().isEmpty() &&
                        !etTemporadas.getText().toString().isEmpty() &&
                        serie.urlVideo != null){

                    serie.nome = etNome.getText().toString();
                    serie.ano_inicio = etDataIni.getText().toString();
                    serie.ano_fim = etDataFim.getText().toString();
                    serie.temporadas = etTemporadas.getText().toString();

                    if(rbAbc.isChecked()){
                        serie.emissora = getContext().getResources().getString(R.string.tipo_abc);
                    }else if(rbCw.isChecked()){
                        serie.emissora = getContext().getResources().getString(R.string.tabs_cw);
                    }else if(rbHbo.isChecked()){
                        serie.emissora = getContext().getResources().getString(R.string.tipo_hbo);
                    }
                    else if(rbNetflix.isChecked()){
                        serie.emissora = getContext().getResources().getString(R.string.tipo_netflix);
                    }
                    new SeriesTask().execute(SAVE); //executa a operação CREATE em uma thread AsyncTask
                }
                else{
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.val_dadosinputs),
                            Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.menuitem_excluir:{
                new SeriesTask().execute(DELETE);
                break;
            }

            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return true;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    /**
     * Método que recebe o retorno da Activity de galeria de imagens.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK){
            Log.d(TAG, data.toString());
            Uri arquivoUri = data.getData(); //obtém o URI
            Log.d(TAG, "URI do arquivo: " + arquivoUri);
            if(arquivoUri.toString().contains("images")) {
                ivFoto.setImageURI(arquivoUri); //coloca a imagem no ImageView
                serie.urlFoto = arquivoUri.toString(); //armazena o Uri da imagem no objeto do modelo
            }else if(arquivoUri.toString().contains("video")) {
                etUrlVideo.setText(arquivoUri.toString()); //coloca a URL do vídeo no EditText
                serie.urlVideo = arquivoUri.toString(); //armazena o Uri do vídeo no objeto do modelo
            }
        }
    }

    private class SeriesTask extends AsyncTask<String, Void, Long> {

        @Override
        protected Long doInBackground(String... strings) {
            if (strings[0].equals(SAVE)) {
                return serieServiceBD.save(serie);
            }
            else {
                if (strings[0].equals(DELETE)){
                    return serieServiceBD.delete(serie);
                }
            }
            return 0L;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //emite uma caixa processando
            alertWait(R.string.alert_title_wait, R.string.alert_message_processando);
        }


        @Override
        protected void onPostExecute(Long cont) {
            super.onPostExecute(cont);
            //alertWaitDismiss();
            if(cont > 0){
                //faz aparecer uma caixa de diálogo confirmando a operação
                alertOk(R.string.alert_title_resultadodaoperacao, R.string.alert_message_realizadacomsucesso);
            }else{
                //faz aparecer uma caixa de diálogo confirmando a operação
                alertOk(R.string.alert_title_resultadodaoperacao, R.string.alert_message_erroaorealizaroperacao);
            }
        }
    }//fim classe interna
}
