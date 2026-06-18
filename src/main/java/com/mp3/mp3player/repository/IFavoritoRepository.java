package com.mp3.mp3player.repository;

import com.mp3.mp3player.model.Musica;
import com.mp3.mp3player.exceptions.DataAccessException;
import java.util.List;

public interface IFavoritoRepository {
    void adicionar(int usuarioId, int musicaId) throws DataAccessException;
    void remover(int usuarioId, int musicaId) throws DataAccessException;
    boolean isFavorita(int usuarioId, int musicaId) throws DataAccessException;
    List<Musica> listarPorUsuario(int usuarioId) throws DataAccessException;
}