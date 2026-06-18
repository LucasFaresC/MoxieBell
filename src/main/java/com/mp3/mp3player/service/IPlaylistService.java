package com.mp3.mp3player.service;

import com.mp3.mp3player.model.Playlist;
import com.mp3.mp3player.model.Musica;
import com.mp3.mp3player.exceptions.*;

import java.util.List;

public interface IPlaylistService {
    void criarPlaylist(Playlist playlist) throws PlaylistJaExisteException, DataAccessException, PlaylistNaoEncontradaException;
    void removerPlaylist(int id) throws PlaylistNaoEncontradaException, DataAccessException;
    Playlist buscarPlaylist(int id) throws PlaylistNaoEncontradaException, DataAccessException;
    List<Playlist> listarPlaylistsPorUsuario(int usuarioId) throws DataAccessException;
    void adicionarMusicaNaPlaylist(int playlistId, int musicaId, int ordem) throws PlaylistNaoEncontradaException, MusicaNaoEncontradaException, MusicaJaExisteNaPlaylistException, DataAccessException;
    void removerMusicaDaPlaylist(int playlistId, int musicaId) throws PlaylistNaoEncontradaException, MusicaNaoEncontradaException, MusicaNaoEstaNaPlaylistException, DataAccessException;
    List<Musica> listarMusicasDaPlaylist(int playlistId) throws PlaylistNaoEncontradaException, DataAccessException;
}