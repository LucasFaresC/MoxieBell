package com.fares.MoxieBell.service;

import com.fares.MoxieBell.model.Musica;
import com.fares.MoxieBell.model.Playlist;
import com.fares.MoxieBell.model.User;
import com.fares.MoxieBell.repository.MusicaRepository;
import com.fares.MoxieBell.repository.PlaylistRepository;
import com.fares.MoxieBell.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final MusicaRepository musicaRepository;
    private final UserRepository userRepository;

    public PlaylistService(PlaylistRepository playlistRepository, MusicaRepository musicaRepository, UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.musicaRepository = musicaRepository;
        this.userRepository = userRepository;
    }

    // RF09: Consultar playlists
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    // RF06: Criar playlists
    public Playlist createPlaylist(String nome, String descricao, Long userId) {
        // Fetch the single user (since it's a local app, ID is likely 1)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        Playlist novaPlaylist = new Playlist(nome, descricao, user);
        return playlistRepository.save(novaPlaylist);
    }

    // RF07: Organizar/Adicionar músicas em playlists
    public Playlist addMusicaToPlaylist(Long playlistId, Long musicaId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist não encontrada!"));

        Musica musica = musicaRepository.findById(musicaId)
                .orElseThrow(() -> new RuntimeException("Música não encontrada!"));

        playlist.addMusica(musica); // This uses the method we wrote in MediaList!
        return playlistRepository.save(playlist);
    }

    // RF08: Remover playlists
    public void deletePlaylist(Long id) {
        playlistRepository.deleteById(id);
    }
}