package com.mp3.mp3player.exceptions;

public class MusicaNaoFavoritadaException extends Exception {
    private String mensagem;

    public MusicaNaoFavoritadaException(String mensagem) {
        this.mensagem = mensagem;
    }

    public MusicaNaoFavoritadaException(int musicaId, int usuarioId) {
        this.mensagem = "Música ID " + musicaId + " não está favoritada pelo usuário ID " + usuarioId;
    }

    public String getMessage() {
        return mensagem;
    }
}
