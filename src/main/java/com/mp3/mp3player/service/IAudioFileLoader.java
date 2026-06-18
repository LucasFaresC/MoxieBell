package com.mp3.mp3player.service;

import com.mp3.mp3player.exceptions.ArquivoAudioInvalidoException;
import java.io.File;

public interface IAudioFileLoader {
    void load(String caminhoArquivo) throws ArquivoAudioInvalidoException;
    File getCurrentFile();
    double getDuration();
}