package com.mp3.mp3player.controller;

import com.mp3.mp3player.model.Musica;
import java.util.List;

public interface IPlayerStarter {
    void setFila(List<Musica> musicas);
    void play();
}