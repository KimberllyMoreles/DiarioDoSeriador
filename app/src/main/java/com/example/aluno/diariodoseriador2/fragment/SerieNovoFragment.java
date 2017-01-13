package com.example.aluno.diariodoseriador2.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Created by aluno on 12/12/16.
 */
public class SerieNovoFragment extends BaseFragment {

    private Serie serie;

    private RadioButton rbAbc, rbCw, rbHbo, rbNetflix;
    private EditText etNome, etDataIni, etDataFim, etTemporadas, etUrlVideo;
    private ImageView ivFoto;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        //((SerieActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_novaserie);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nova_serie, container, false);

        //um título para a janela
        ((SerieActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_novaserie);

        //Cria uma instancia da classe de modelo
        serie = new Serie();

        //mapeia e inicializa os componentes da UI
        //Card0
        ivFoto = (ImageView) view.findViewById(R.id.imv_card0_frnovaserie);
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
        rbAbc = (RadioButton) view.findViewById(R.id.rb_abc_card1_frnovaserie);
        //rbAbc.setChecked(true);
        rbCw = (RadioButton) view.findViewById(R.id.rb_cw_card1_frnovaserie);
        rbHbo = (RadioButton) view.findViewById(R.id.rb_hbo_card1_frnovaserie);
        rbNetflix = (RadioButton) view.findViewById(R.id.rb_netflix_card1_frnovaserie);
        rbNetflix.setChecked(true);

        //Card2
        etNome = (EditText) view.findViewById(R.id.etNome_card2_frnovaserie);
        etDataIni = (EditText) view.findViewById(R.id.etDataIni_card2_frnovaserie);
        etDataFim = (EditText) view.findViewById(R.id.etDataFim_card2_frnovaserie);
        etTemporadas = (EditText) view.findViewById(R.id.etTemporadas_card2_frnovaserie);

        //Card4
        etUrlVideo = (EditText) view.findViewById(R.id.etURLVideo__card4_frnovaserie);
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
        inflater.inflate(R.menu.menu_fragment_novaserie, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuitem_salvar:
                //Toast.makeText(getContext(), "salvo", Toast.LENGTH_SHORT).show();
                if(!etNome.getText().toString().isEmpty()||
                        !etDataIni.getText().toString().isEmpty()||
                        !etTemporadas.getText().toString().isEmpty()){

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
                    new SeriesTask().execute(); //executa a operação CREATE em uma thread AsyncTask
                }
                else{
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.val_dadosinputs),
                            Toast.LENGTH_SHORT).show();
                }
                break;

            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return true;
    }

    /**
     * Método que recebe o retorno da Activity de galeria de imagens.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK){
            Log.d(TAG, data.toString());
            Uri arquivoUri = data.getData(); //obtém o URI da imagem
            Log.d(TAG, "URI do arquivo: " + arquivoUri);
            if(arquivoUri.toString().contains("images")) {
                ivFoto.setImageURI(arquivoUri); //coloca a imagem no ImageView
                serie.urlFoto = arquivoUri.toString(); //armazena o Uri para salvar a imagem no objeto imagem
            }else if(arquivoUri.toString().contains("video")) {
                etUrlVideo.setText(arquivoUri.toString()); //coloca a URL do vídeo no EditText
                serie.urlVideo = arquivoUri.toString(); //armazena o URL do vídeo no objeto do modelo
            }
        }
    }

    /*
        Classe interna que extende uma AsyncTask.
        Lembrando: A AsyncTask gerência a thread que realiza CRUD no banco de dados.
    */
    private class SeriesTask extends AsyncTask<Void, Void, Long> {

        @Override
        protected Long doInBackground(Void... voids) {
            return SerieServiceBD.getInstance(getContext()).save(serie);
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
