package com.mp3.mp3player.service;

import com.mp3.mp3player.model.Musica;
import com.mp3.mp3player.exceptions.ArquivoAudioInvalidoException;
import com.mp3.mp3player.exceptions.PlaylistVaziaException;

import java.util.ArrayList;
import java.util.List;

public class PlayerService implements IPlayerService {
    private List<Musica> fila;
    private int indiceAtual;
    private final IAudioService audioService;

    public PlayerService() {
        this(new AudioService());
    }

    public PlayerService(IAudioService audioService) {
        this.fila = new ArrayList<>();
        this.indiceAtual = -1;
        this.audioService = audioService;
    }

    @Override
    public void setFila(List<Musica> musicas) {
        if (musicas == null) {
            this.fila = new ArrayList<>();
        } else {
            this.fila = new ArrayList<>(musicas);
        }
        this.indiceAtual = this.fila.isEmpty() ? -1 : 0;
    }

    @Override
    public void play() throws PlaylistVaziaException, ArquivoAudioInvalidoException {
        if (fila.isEmpty()) {
            throw new PlaylistVaziaException("Sem músicas na fila");
        }
        if (indiceAtual < 0 || indiceAtual >= fila.size()) {
            indiceAtual = 0;
        }
        Musica musica = fila.get(indiceAtual);
        audioService.load(musica.getCaminhoArquivo());
        audioService.play();
    }
}