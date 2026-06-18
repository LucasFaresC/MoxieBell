package com.mp3.mp3player.repository;

import com.mp3.mp3player.database.ConnectionFactory;
import com.mp3.mp3player.exceptions.DataAccessException;
import com.mp3.mp3player.model.Musica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoritoRepository implements IFavoritoRepository {

    public void adicionar(int usuarioId, int musicaId) throws DataAccessException {
        String sql = "INSERT INTO favorito (usuario_id, musica_id) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, musicaId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao inserir favorito", e);
        }
    }

    public void remover(int usuarioId, int musicaId) throws DataAccessException {
        String sql = "DELETE FROM favorito WHERE usuario_id = ? AND musica_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, musicaId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao remover favorito", e);
        }
    }

    public boolean isFavorita(int usuarioId, int musicaId) throws DataAccessException {
        String sql = "SELECT 1 FROM favorito WHERE usuario_id = ? AND musica_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, musicaId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao verificar favorito", e);
        }
    }

    public List<Musica> listarPorUsuario(int usuarioId) throws DataAccessException {
        String sql = "SELECT m.* FROM musica m " +
                     "JOIN favorito f ON m.id = f.musica_id " +
                     "WHERE f.usuario_id = ? ORDER BY f.data_favoritou DESC";
        List<Musica> favoritas = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                favoritas.add(extractMusica(rs));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao listar favoritas", e);
        }
        return favoritas;
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