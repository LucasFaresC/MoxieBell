package com.mp3.mp3player.model;

import java.util.ArrayList;
import java.util.List;

public abstract class MediaList {
    protected String nome;
    protected List<Musica> musicas;

    public MediaList() {
        this.musicas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }

    public abstract void adicionarMusica(Musica musica);
    public abstract void removerMusica(Musica musica);
    public abstract List<Musica> listarMusicas();
}
