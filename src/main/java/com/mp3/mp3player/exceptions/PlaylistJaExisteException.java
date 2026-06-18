package com.mp3.mp3player.exceptions;

public class PlaylistJaExisteException extends Exception {
    private String mensagem;

    public PlaylistJaExisteException(String mensagem) {
        this.mensagem = mensagem;
    }

    public PlaylistJaExisteException(String nome, int usuarioId) {
        this.mensagem = "Playlist '" + nome + "' já existe para o usuário ID " + usuarioId;
    }

    public String getMessage() {
        return mensagem;
    }
}
