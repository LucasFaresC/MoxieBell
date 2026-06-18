package com.mp3.mp3player.repository;

import com.mp3.mp3player.model.Musica;
import com.mp3.mp3player.exceptions.MusicaNaoEncontradaException;
import com.mp3.mp3player.exceptions.DataAccessException;
import java.util.List;

public interface IMusicaRepository {
    void save(Musica musica) throws DataAccessException;
    void delete(int id) throws MusicaNaoEncontradaException, DataAccessException;
    List<Musica> findAll() throws DataAccessException;
    Musica findById(int id) throws MusicaNaoEncontradaException, DataAccessException;
    Musica findByCaminho(String caminho) throws DataAccessException;
}