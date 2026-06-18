package com.mp3.mp3player.exceptions;

public class MusicaNaoEstaNaPlaylistException extends Exception {
    private String mensagem;

    public MusicaNaoEstaNaPlaylistException(String mensagem) {
        this.mensagem = mensagem;
    }

    public MusicaNaoEstaNaPlaylistException(int musicaId, int playlistId) {
        this.mensagem = "Música ID " + musicaId + " não está na playlist ID " + playlistId;
    }

    public String getMessage() {
        return mensagem;
    }
}
