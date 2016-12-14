package com.example.aluno.diariodoseriador2.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aluno.diariodoseriador2.R;
import com.example.aluno.diariodoseriador2.model.Serie;

import java.util.List;

/**
 * Created by aluno on 14/12/16.
 */
public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder>{
    protected static final String TAG = "SeriesAdapter";
    private final List<Serie> series;
    private final Context context;

    private SerieOnClickListener serieOnClickListener;

    public SeriesAdapter(Context context, List<Serie> series, SerieOnClickListener serieOnClickListener) {
        this.context = context;
        this.series = series;
        this.serieOnClickListener = serieOnClickListener;
    }

    @Override
    public int getItemCount() {
        return this.series != null ? this.series.size() : 0;
    }



    @Override
    public SeriesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Infla a view do layout
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_series, viewGroup, false);

        // Cria o ViewHolder
        SeriesViewHolder holder = new SeriesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final SeriesViewHolder holder, final int position) {
        // Atualiza a view
        Serie c = series.get(position);
        Log.d(TAG, "Serie no Adapter da RecyclerView: " + c.toString());

        Log.d(TAG, c.toString());

        holder.tNome.setText(c.nome);
        holder.progress.setVisibility(View.VISIBLE);
        if(c.urlFoto != null){
            holder.img.setImageURI(Uri.parse(String.valueOf(c.urlFoto)));
        }else{
            holder.img.setImageResource(R.drawable.television);
        }

        // Click
        if (serieOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serieOnClickListener.onClickSerie(holder.itemView, position); // A variável position é final
                }
            });
        }

        holder.progress.setVisibility(View.INVISIBLE);
    }

    public interface SerieOnClickListener {
        public void onClickSerie(View view, int idx);
    }

    // ViewHolder com as views
    public static class SeriesViewHolder extends RecyclerView.ViewHolder {
        public TextView tNome;
        ImageView img;
        ProgressBar progress;

        public SeriesViewHolder(View view) {
            super(view);
            // Cria as views para salvar no ViewHolder
            tNome = (TextView) view.findViewById(R.id.textView_card__adapter_serie);
            img = (ImageView) view.findViewById(R.id.imageView_card_adapter_serie);
            progress = (ProgressBar) view.findViewById(R.id.progressBar);
        }
    }
}
