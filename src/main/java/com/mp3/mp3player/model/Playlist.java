package com.mp3.mp3player.model;

import java.util.List;

public class Playlist extends MediaList {
    private int id;
    private String descricao;
    private int usuarioId;

    public Playlist() {
        this.nome = "";
        this.descricao = "";
    }

    public Playlist(String nome, String descricao, int usuarioId) {
        this.nome = nome;
        this.descricao = descricao;
        this.usuarioId = usuarioId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void adicionarMusica(Musica musica) {
        if (!musicas.contains(musica)) {
            musicas.add(musica);
        }
    }

    public void removerMusica(Musica musica) {
        musicas.remove(musica);
    }

    public List<Musica> listarMusicas() {
        return musicas;
    }
}
