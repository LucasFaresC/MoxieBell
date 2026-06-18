package com.mp3.mp3player.service;

import com.mp3.mp3player.model.Musica;
import com.mp3.mp3player.repository.IMusicaRepository;
import com.mp3.mp3player.repository.IFavoritoRepository;
import com.mp3.mp3player.repository.MusicaRepository;
import com.mp3.mp3player.repository.FavoritoRepository;
import com.mp3.mp3player.exceptions.MusicaJaFavoritadaException;
import com.mp3.mp3player.exceptions.MusicaNaoFavoritadaException;
import com.mp3.mp3player.exceptions.DataAccessException;
import com.mp3.mp3player.exceptions.MusicaNaoEncontradaException;

import java.util.List;

public class FavoritesService implements IFavoritesService {
    private final IMusicaRepository musicaRepository;
    private final IFavoritoRepository favoritoRepository;

    public FavoritesService() {
        this(new MusicaRepository(), new FavoritoRepository());
    }

    public FavoritesService(IMusicaRepository musicaRepository, IFavoritoRepository favoritoRepository) {
        this.musicaRepository = musicaRepository;
        this.favoritoRepository = favoritoRepository;
    }

    public void favoritarMusica(int usuarioId, int musicaId) throws MusicaJaFavoritadaException, DataAccessException, MusicaNaoEncontradaException {
        musicaRepository.findById(musicaId);
        if (favoritoRepository.isFavorita(usuarioId, musicaId)) {
            throw new MusicaJaFavoritadaException(musicaId, usuarioId);
        }
        favoritoRepository.adicionar(usuarioId, musicaId);
    }

    public void removerFavorita(int usuarioId, int musicaId) throws MusicaNaoFavoritadaException, DataAccessException {
        if (!favoritoRepository.isFavorita(usuarioId, musicaId)) {
            throw new MusicaNaoFavoritadaException(musicaId, usuarioId);
        }
        favoritoRepository.remover(usuarioId, musicaId);
    }

    public List<Musica> listarFavoritas(int usuarioId) throws DataAccessException {
        return favoritoRepository.listarPorUsuario(usuarioId);
    }
}