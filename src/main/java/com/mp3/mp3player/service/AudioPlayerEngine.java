package com.mp3.mp3player.service;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AudioPlayerEngine implements IAudioPlayerEngine {
    private Player player;
    private Thread playbackThread;
    private boolean isPlaying;

    @Override
    public void play(File file) {
        if (file == null) return;
        stop();
        playbackThread = new Thread(() -> {
            try (FileInputStream fis = new FileInputStream(file)) {
                player = new Player(fis);
                isPlaying = true;
                player.play();
                isPlaying = false;
            } catch (JavaLayerException | IOException e) {
                e.printStackTrace();
                isPlaying = false;
            }
        });
        playbackThread.setDaemon(true);
        playbackThread.start();
    }

    private void stop() {
        if (player != null) {
            player.close();
            player = null;
        }
        if (playbackThread != null && playbackThread.isAlive()) {
            playbackThread.interrupt();
        }
        isPlaying = false;
    }
}