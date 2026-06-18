package com.mp3.mp3player.service;

import com.mp3.mp3player.exceptions.ArquivoAudioInvalidoException;

public class AudioService implements IAudioService {
    private final IAudioFileLoader fileLoader;
    private final IAudioPlayerEngine playerEngine;

    public AudioService() {
        this(new AudioFileLoader(), new AudioPlayerEngine());
    }

    public AudioService(IAudioFileLoader fileLoader, IAudioPlayerEngine playerEngine) {
        this.fileLoader = fileLoader;
        this.playerEngine = playerEngine;
    }

    @Override
    public void load(String caminhoArquivo) throws ArquivoAudioInvalidoException {
        fileLoader.load(caminhoArquivo);
    }

    @Override
    public void play() {
        playerEngine.play(fileLoader.getCurrentFile());
    }
}