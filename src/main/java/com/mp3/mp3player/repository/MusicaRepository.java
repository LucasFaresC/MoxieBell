package com.mp3.mp3player.repository;

import com.mp3.mp3player.model.Musica;
import com.mp3.mp3player.database.ConnectionFactory;
import com.mp3.mp3player.exceptions.MusicaNaoEncontradaException;
import com.mp3.mp3player.exceptions.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MusicaRepository implements IMusicaRepository {

    public void save(Musica musica) throws DataAccessException {
        String sql = "INSERT INTO musica (titulo, artista, album, genero, duracao, caminho_arquivo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, musica.getTitulo());
            stmt.setString(2, musica.getArtista());
            stmt.setString(3, musica.getAlbum());
            stmt.setString(4, musica.getGenero());
            stmt.setDouble(5, musica.getDuracao());
            stmt.setString(6, musica.getCaminhoArquivo());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                musica.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro: salvar música", e);
        }
    }

    public void delete(int id) throws MusicaNaoEncontradaException, DataAccessException {
        String sql = "DELETE FROM musica WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new MusicaNaoEncontradaException(id);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro: remover música", e);
        }
    }

    public List<Musica> findAll() throws DataAccessException {
        String sql = "SELECT * FROM musica ORDER BY id";
        List<Musica> musicas = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                musicas.add(extractMusica(rs));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro: listar músicas.", e);
        }
        return musicas;
    }

    public Musica findById(int id) throws MusicaNaoEncontradaException, DataAccessException {
        String sql = "SELECT * FROM musica WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractMusica(rs);
            } else {
                throw new MusicaNaoEncontradaException(id);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro: buscar música.", e);
        }
    }

    public Musica findByCaminho(String caminho) throws DataAccessException {
        String sql = "SELECT * FROM musica WHERE caminho_arquivo = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, caminho);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractMusica(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Erro: buscar por caminho", e);
        }
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