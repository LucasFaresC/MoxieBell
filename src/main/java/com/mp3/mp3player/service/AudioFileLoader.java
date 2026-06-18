package com.mp3.mp3player.service;

import com.mp3.mp3player.exceptions.ArquivoAudioInvalidoException;
import java.io.File;

public class AudioFileLoader implements IAudioFileLoader {
    private File currentFile;
    private double duration;

    public void load(String caminhoArquivo) throws ArquivoAudioInvalidoException {
        try {
            File file = new File(caminhoArquivo);
            if (!file.exists()) {
                throw new ArquivoAudioInvalidoException("Arquivo não encontrado: " + caminhoArquivo);
            }
            this.currentFile = file;
            this.duration = 0;
        } catch (Exception e) {
            throw new ArquivoAudioInvalidoException("Erro ao carregar: " + caminhoArquivo, e);
        }
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public double getDuration() {
        return duration;
    }
}