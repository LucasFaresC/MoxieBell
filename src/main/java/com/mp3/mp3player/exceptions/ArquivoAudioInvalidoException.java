package com.mp3.mp3player.exceptions;

public class ArquivoAudioInvalidoException extends Exception {
    private String mensagem;

    public ArquivoAudioInvalidoException(String mensagem) {
        this.mensagem = mensagem;
    }

    public ArquivoAudioInvalidoException(String caminho, Throwable cause) {
        this.mensagem = "Erro ao carregar arquivo: " + caminho;
    }

    public String getMessage() {
        return mensagem;
    }
}
