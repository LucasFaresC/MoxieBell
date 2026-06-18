package com.mp3.mp3player.exceptions;

public class MusicaJaFavoritadaException extends Exception {
    private String mensagem;

    public MusicaJaFavoritadaException(String mensagem) {
        this.mensagem = mensagem;
    }

    public MusicaJaFavoritadaException(int musicaId, int usuarioId) {
        this.mensagem = "Música ID " + musicaId + " já está favoritada pelo usuário ID " + usuarioId;
    }

    public String getMessage() {
        return mensagem;
    }
}
