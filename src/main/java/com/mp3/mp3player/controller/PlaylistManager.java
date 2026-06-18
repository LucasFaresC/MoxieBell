package com.mp3.mp3player.controller;

import com.mp3.mp3player.model.Playlist;
import com.mp3.mp3player.model.Musica;
import com.mp3.mp3player.service.IPlaylistService;
import com.mp3.mp3player.service.PlaylistService;
import com.mp3.mp3player.exceptions.*;

import java.util.List;

public class PlaylistManager {
    private final IPlaylistService playlistService;

    public PlaylistManager() {
        this(new PlaylistService());
    }

    public PlaylistManager(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    public void criarPlaylist(Playlist playlist) throws PlaylistNaoEncontradaException {
        try {
            playlistService.criarPlaylist(playlist);
        } catch (PlaylistJaExisteException | DataAccessException e) {
            System.err.println("Erro ao criar playlist: " + e.getMessage());
        }
    }

    public void removerPlaylist(int id) {
        try {
            playlistService.removerPlaylist(id);
        } catch (PlaylistNaoEncontradaException | DataAccessException e) {
            System.err.println("Erro ao remover playlist: " + e.getMessage());
        }
    }

    public Playlist buscarPlaylist(int id) {
        try {
            return playlistService.buscarPlaylist(id);
        } catch (PlaylistNaoEncontradaException | DataAccessException e) {
            System.err.println("Erro ao buscar playlist: " + e.getMessage());
            return null;
        }
    }

    public List<Playlist> listarPlaylistsPorUsuario(int usuarioId) {
        try {
            return playlistService.listarPlaylistsPorUsuario(usuarioId);
        } catch (DataAccessException e) {
            System.err.println("Erro ao listar playlists: " + e.getMessage());
            return null;
        }
    }

    public void adicionarMusicaNaPlaylist(int playlistId, int musicaId, int ordem) {
        try {
            playlistService.adicionarMusicaNaPlaylist(playlistId, musicaId, ordem);
        } catch (PlaylistNaoEncontradaException | MusicaNaoEncontradaException | MusicaJaExisteNaPlaylistException | DataAccessException e) {
            System.err.println("Erro ao adicionar música: " + e.getMessage());
        }
    }

    public void removerMusicaDaPlaylist(int playlistId, int musicaId) {
        try {
            playlistService.removerMusicaDaPlaylist(playlistId, musicaId);
        } catch (PlaylistNaoEncontradaException | MusicaNaoEncontradaException | MusicaNaoEstaNaPlaylistException | DataAccessException e) {
            System.err.println("Erro ao remover música: " + e.getMessage());
        }
    }

    public List<Musica> listarMusicasDaPlaylist(int playlistId) {
        try {
            return playlistService.listarMusicasDaPlaylist(playlistId);
        } catch (PlaylistNaoEncontradaException | DataAccessException e) {
            System.err.println("Erro ao listar músicas: " + e.getMessage());
            return null;
        }
    }
}