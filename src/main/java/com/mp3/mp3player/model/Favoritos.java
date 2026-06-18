package com.mp3.mp3player.model;

import java.util.List;

public class Favoritos extends MediaList {

    public Favoritos() {
        this.nome = "Favoritos";
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
