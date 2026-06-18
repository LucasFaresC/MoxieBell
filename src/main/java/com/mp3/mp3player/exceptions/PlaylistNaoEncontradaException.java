package com.mp3.mp3player.exceptions;

public class PlaylistNaoEncontradaException extends Exception {
    private String mensagem;

    public PlaylistNaoEncontradaException(String mensagem) {
        this.mensagem = mensagem;
    }

    public PlaylistNaoEncontradaException(int id) {
        this.mensagem = "Playlist com ID " + id + " não encontrada.";
    }

    public PlaylistNaoEncontradaException(String nome, int usuarioId) {
        this.mensagem = "Playlist '" + nome + "' não encontrada para o usuário ID " + usuarioId;
    }

    public String getMessage() {
        return mensagem;
    }
}
