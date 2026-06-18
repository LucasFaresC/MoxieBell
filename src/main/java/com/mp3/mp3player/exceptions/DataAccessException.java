package com.mp3.mp3player.exceptions;

public class DataAccessException extends Exception {
    private String mensagem;

    public DataAccessException(String mensagem, Throwable cause) {
        this.mensagem = mensagem;
    }

    public DataAccessException(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMessage() {
        return mensagem;
    }
}
