package com.mp3.mp3player.controller;

import com.mp3.mp3player.model.Musica;
import com.mp3.mp3player.service.IPlayerService;
import com.mp3.mp3player.service.PlayerService;
import com.mp3.mp3player.exceptions.PlaylistVaziaException;
import com.mp3.mp3player.exceptions.ArquivoAudioInvalidoException;
import javafx.scene.control.Alert;

import java.util.List;

public class PlayerControl implements IPlaybackControl, IPlayerStarter {
    private final IPlayerService playerService;

    public PlayerControl() {
        this(new PlayerService());
    }

    public PlayerControl(IPlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public void setFila(List<Musica> musicas) {
        playerService.setFila(musicas);
    }

    @Override
    public void play() {
        try {
            playerService.play();
        } catch (PlaylistVaziaException | ArquivoAudioInvalidoException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao reproduzir");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}