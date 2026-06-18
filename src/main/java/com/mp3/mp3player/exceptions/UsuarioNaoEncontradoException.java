package com.mp3.mp3player.exceptions;

public class UsuarioNaoEncontradoException extends Exception {
    private String mensagem;

    public UsuarioNaoEncontradoException(String mensagem) {
        this.mensagem = mensagem;
    }

    public UsuarioNaoEncontradoException(int id) {
        this.mensagem = "Usuário com ID " + id + " não encontrado.";
    }

    public String getMessage() {
        return mensagem;
    }
}
