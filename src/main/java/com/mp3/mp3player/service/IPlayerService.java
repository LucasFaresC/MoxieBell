package com.mp3.mp3player.service;

import com.mp3.mp3player.model.Musica;
import com.mp3.mp3player.exceptions.ArquivoAudioInvalidoException;
import com.mp3.mp3player.exceptions.PlaylistVaziaException;
import java.util.List;

public interface IPlayerService {
    void setFila(List<Musica> musicas);
    void play() throws PlaylistVaziaException, ArquivoAudioInvalidoException;
}