package com.mp3.mp3player.controller;

import com.mp3.mp3player.model.Musica;
import com.mp3.mp3player.service.IFavoritesService;
import com.mp3.mp3player.service.FavoritesService;
import com.mp3.mp3player.exceptions.*;

import java.util.List;

public class FavoritesManager {
    private IFavoritesService favoritesService;

    public FavoritesManager() {
        this(new FavoritesService());
    }

    public FavoritesManager(IFavoritesService favoritesService) {
        this.favoritesService = favoritesService;
    }

    public void favoritarMusica(int usuarioId, int musicaId) {
        try {
            favoritesService.favoritarMusica(usuarioId, musicaId);
        } catch (MusicaNaoEncontradaException | MusicaJaFavoritadaException | DataAccessException e) {
            System.err.println("Erro ao favoritar: " + e.getMessage());
        }
    }

    public void removerFavorita(int usuarioId, int musicaId) {
        try {
            favoritesService.removerFavorita(usuarioId, musicaId);
        } catch (MusicaNaoFavoritadaException | DataAccessException e) {
            System.err.println("Erro ao remover favorita: " + e.getMessage());
        }
    }

    public List<Musica> listarFavoritas(int usuarioId) {
        try {
            return favoritesService.listarFavoritas(usuarioId);
        } catch (DataAccessException e) {
            System.err.println("Erro ao listar favoritas: " + e.getMessage());
            return null;
        }
    }
}