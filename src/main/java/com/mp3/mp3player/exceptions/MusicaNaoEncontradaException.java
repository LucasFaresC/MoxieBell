package com.mp3.mp3player.exceptions;

public class MusicaNaoEncontradaException extends Exception {
    private String mensagem;

    public MusicaNaoEncontradaException(String mensagem) {
        this.mensagem = mensagem;
    }

    public MusicaNaoEncontradaException(int id) {
        this.mensagem = "Música com ID " + id + " não encontrada.";
    }

    public String getMessage() {
        return mensagem;
    }
}
