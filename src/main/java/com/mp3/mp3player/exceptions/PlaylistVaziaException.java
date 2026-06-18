package com.mp3.mp3player.exceptions;

public class PlaylistVaziaException extends Exception {
    private String mensagem;

    public PlaylistVaziaException(String mensagem) {
        this.mensagem = mensagem;
    }

    public PlaylistVaziaException() {
        this.mensagem = "A playlist está vazia.";
    }

    public String getMessage() {
        return mensagem;
    }
}
