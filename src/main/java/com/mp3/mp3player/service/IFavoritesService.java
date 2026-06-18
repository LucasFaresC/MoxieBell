package com.mp3.mp3player.service;

import com.mp3.mp3player.model.Musica;
import com.mp3.mp3player.exceptions.MusicaJaFavoritadaException;
import com.mp3.mp3player.exceptions.MusicaNaoFavoritadaException;
import com.mp3.mp3player.exceptions.DataAccessException;
import com.mp3.mp3player.exceptions.MusicaNaoEncontradaException;

import java.util.List;

public interface IFavoritesService {
    void favoritarMusica(int usuarioId, int musicaId) throws MusicaJaFavoritadaException, DataAccessException, MusicaNaoEncontradaException;
    void removerFavorita(int usuarioId, int musicaId) throws MusicaNaoFavoritadaException, DataAccessException;
    List<Musica> listarFavoritas(int usuarioId) throws DataAccessException;
}