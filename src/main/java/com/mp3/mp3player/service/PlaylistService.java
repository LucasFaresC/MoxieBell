package com.mp3.mp3player.service;

import com.mp3.mp3player.model.Playlist;
import com.mp3.mp3player.model.Musica;
import com.mp3.mp3player.repository.IPlaylistRepository;
import com.mp3.mp3player.repository.IMusicaRepository;
import com.mp3.mp3player.repository.PlaylistRepository;
import com.mp3.mp3player.repository.MusicaRepository;
import com.mp3.mp3player.exceptions.*;

import java.util.List;

public class PlaylistService implements IPlaylistService {
    private final IPlaylistRepository playlistRepository;
    private final IMusicaRepository musicaRepository;

    public PlaylistService() {
        this(new PlaylistRepository(), new MusicaRepository());
    }

    public PlaylistService(IPlaylistRepository playlistRepository, IMusicaRepository musicaRepository) {
        this.playlistRepository = playlistRepository;
        this.musicaRepository = musicaRepository;
    }

    public void criarPlaylist(Playlist playlist) throws PlaylistJaExisteException, DataAccessException, PlaylistNaoEncontradaException {
        List<Playlist> existentes = playlistRepository.findAllByUsuario(playlist.getUsuarioId());
        for (Playlist p : existentes) {
            if (p.getNome().equalsIgnoreCase(playlist.getNome())) {
                throw new PlaylistJaExisteException(playlist.getNome(), playlist.getUsuarioId());
            }
        }
        playlistRepository.save(playlist);
    }

    public void removerPlaylist(int id) throws PlaylistNaoEncontradaException, DataAccessException {
        playlistRepository.delete(id);
    }

    public Playlist buscarPlaylist(int id) throws PlaylistNaoEncontradaException, DataAccessException {
        return playlistRepository.findById(id);
    }

    public List<Playlist> listarPlaylistsPorUsuario(int usuarioId) throws DataAccessException {
        return playlistRepository.findAllByUsuario(usuarioId);
    }

    public void adicionarMusicaNaPlaylist(int playlistId, int musicaId, int ordem) 
            throws PlaylistNaoEncontradaException, MusicaNaoEncontradaException, 
                   MusicaJaExisteNaPlaylistException, DataAccessException {
        Playlist playlist = playlistRepository.findById(playlistId);
        Musica musica = musicaRepository.findById(musicaId);
        List<Musica> musicas = playlist.listarMusicas();
        for (Musica m : musicas) {
            if (m.getId() == musicaId) {
                throw new MusicaJaExisteNaPlaylistException(musicaId, playlistId);
            }
        }
        playlistRepository.addMusica(playlistId, musicaId, ordem);
        playlist.adicionarMusica(musica);
    }

    public void removerMusicaDaPlaylist(int playlistId, int musicaId) 
            throws PlaylistNaoEncontradaException, MusicaNaoEncontradaException, 
                   MusicaNaoEstaNaPlaylistException, DataAccessException {
        Playlist playlist = playlistRepository.findById(playlistId);
        Musica musica = musicaRepository.findById(musicaId);
        List<Musica> musicas = playlist.listarMusicas();
        boolean found = false;
        for (Musica m : musicas) {
            if (m.getId() == musicaId) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new MusicaNaoEstaNaPlaylistException(musicaId, playlistId);
        }
        playlistRepository.removeMusica(playlistId, musicaId);
        playlist.removerMusica(musica);
    }

    public List<Musica> listarMusicasDaPlaylist(int playlistId) throws PlaylistNaoEncontradaException, DataAccessException {
        return playlistRepository.findMusicasByPlaylist(playlistId);
    }
}