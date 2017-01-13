package com.example.aluno.diariodoseriador2.model;

import java.io.Serializable;

/**
 * Created by aluno on 14/12/16.
 */
public class Serie implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long _id;
    public String nome;
    public String ano_inicio;
    public String ano_fim;
    public String temporadas;
    public String urlFoto;
    public String urlVideo;
    public String emissora;

    @Override
    public String toString() {
        return "Serie{" +
                "_id=" + _id +
                ", nome='" + nome + '\'' +
                ", ano_inicio='" + ano_inicio + '\'' +
                ", ano_fim='" + ano_fim + '\'' +
                ", temporadas='" + temporadas + '\'' +
                ", emissora='" + emissora + '\'' +
                '}';
    }
}
