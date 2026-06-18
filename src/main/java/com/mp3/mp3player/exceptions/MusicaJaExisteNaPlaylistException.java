package com.mp3.mp3player.exceptions;

public class MusicaJaExisteNaPlaylistException extends Exception {
    private String mensagem;

    public MusicaJaExisteNaPlaylistException(String mensagem) {
        this.mensagem = mensagem;
    }

    public MusicaJaExisteNaPlaylistException(int musicaId, int playlistId) {
        this.mensagem = "Música ID " + musicaId + " já existe na playlist ID " + playlistId;
    }

    public String getMessage() {
        return mensagem;
    }
}
