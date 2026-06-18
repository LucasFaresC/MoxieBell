package com.mp3.mp3player.repository;

import com.mp3.mp3player.model.Playlist;
import com.mp3.mp3player.model.Musica;
import com.mp3.mp3player.exceptions.PlaylistNaoEncontradaException;
import com.mp3.mp3player.exceptions.DataAccessException;
import java.util.List;

public interface IPlaylistRepository {
    void save(Playlist playlist) throws DataAccessException, PlaylistNaoEncontradaException;
    void delete(int id) throws PlaylistNaoEncontradaException, DataAccessException;
    Playlist findById(int id) throws PlaylistNaoEncontradaException, DataAccessException;
    List<Playlist> findAllByUsuario(int usuarioId) throws DataAccessException;
    void addMusica(int playlistId, int musicaId, int ordem) throws DataAccessException;
    void removeMusica(int playlistId, int musicaId) throws DataAccessException;
    List<Musica> findMusicasByPlaylist(int playlistId) throws DataAccessException;
}