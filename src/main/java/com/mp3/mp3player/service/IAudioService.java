package com.mp3.mp3player.service;

import com.mp3.mp3player.exceptions.ArquivoAudioInvalidoException;

public interface IAudioService {
    void load(String caminhoArquivo) throws ArquivoAudioInvalidoException;
    void play();
}