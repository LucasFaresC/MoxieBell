package com.mp3.mp3player.repository;

import com.mp3.mp3player.model.Playlist;
import com.mp3.mp3player.model.Musica;
import com.mp3.mp3player.database.ConnectionFactory;
import com.mp3.mp3player.exceptions.PlaylistNaoEncontradaException;
import com.mp3.mp3player.exceptions.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistRepository implements IPlaylistRepository {

    public void save(Playlist playlist) throws DataAccessException, PlaylistNaoEncontradaException {
        String sql = "INSERT INTO playlist (nome, descricao, usuario_id) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, playlist.getNome());
            stmt.setString(2, playlist.getDescricao());
            stmt.setInt(3, playlist.getUsuarioId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                playlist.setId(rs.getInt(1));
            }
            for (Musica m : playlist.listarMusicas()) {
                addMusica(playlist.getId(), m.getId(), 0);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro: salvar playlist", e);
        }
    }

    public void delete(int id) throws PlaylistNaoEncontradaException, DataAccessException {
        String sql = "DELETE FROM playlist WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new PlaylistNaoEncontradaException(id);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro: remover playlist", e);
        }
    }

    public Playlist findById(int id) throws PlaylistNaoEncontradaException, DataAccessException {
        String sql = "SELECT * FROM playlist WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Playlist p = extractPlaylist(rs);
                p.setMusicas(findMusicasByPlaylist(id));
                return p;
            } else {
                throw new PlaylistNaoEncontradaException(id);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro: buscar playlist", e);
        }
    }

    public List<Playlist> findAllByUsuario(int usuarioId) throws DataAccessException {
        String sql = "SELECT * FROM playlist WHERE usuario_id = ? ORDER BY id";
        List<Playlist> playlists = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Playlist p = extractPlaylist(rs);
                p.setMusicas(findMusicasByPlaylist(p.getId()));
                playlists.add(p);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro: listar playlists do usuário", e);
        }
        return playlists;
    }

    public void addMusica(int playlistId, int musicaId, int ordem) throws DataAccessException {
        String sql = "INSERT INTO playlist_musica (playlist_id, musica_id, ordem) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playlistId);
            stmt.setInt(2, musicaId);
            stmt.setInt(3, ordem);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao adicionar música à playlist", e);
        }
    }

    public void removeMusica(int playlistId, int musicaId) throws DataAccessException {
        String sql = "DELETE FROM playlist_musica WHERE playlist_id = ? AND musica_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playlistId);
            stmt.setInt(2, musicaId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao remover música da playlist", e);
        }
    }

    public List<Musica> findMusicasByPlaylist(int playlistId) throws DataAccessException {
        String sql = "SELECT m.* FROM musica m " +
                     "JOIN playlist_musica pm ON m.id = pm.musica_id " +
                     "WHERE pm.playlist_id = ? ORDER BY pm.ordem";
        List<Musica> musicas = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playlistId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                musicas.add(extractMusica(rs));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao buscar músicas da playlist", e);
        }
        return musicas;
    }

    private Playlist extractPlaylist(ResultSet rs) throws SQLException {
        Playlist p = new Playlist();
        p.setId(rs.getInt("id"));
        p.setNome(rs.getString("nome"));
        p.setDescricao(rs.getString("descricao"));
        p.setUsuarioId(rs.getInt("usuario_id"));
        return p;
    }

    private Musica extractMusica(ResultSet rs) throws SQLException {
        return new Musica(
            rs.getInt("id"),
            rs.getString("titulo"),
            rs.getString("artista"),
            rs.getString("album"),
            rs.getString("genero"),
            rs.getDouble("duracao"),
            rs.getString("caminho_arquivo")
        );
    }
}