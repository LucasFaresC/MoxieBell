package com.mp3.mp3player.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import com.mp3.mp3player.controller.PlaylistManager;
import com.mp3.mp3player.model.Playlist;
import com.mp3.mp3player.model.Musica;
import com.mp3.mp3player.repository.MusicaRepository;
import com.mp3.mp3player.exceptions.DataAccessException;

import java.util.List;
import java.util.Optional;

public class PlaylistScreen {
    @FXML private ListView<Playlist> listViewPlaylists;
    @FXML private ListView<Musica> listViewMusicas;
    @FXML private Button btnCriar;
    @FXML private Button btnRemover;
    @FXML private Button btnAtualizar;
    @FXML private Button btnVoltar;
    @FXML private Button btnAdicionarMusica;

    private PlaylistManager playlistManager = new PlaylistManager();
    private MusicaRepository musicaRepository = new MusicaRepository();
    private int usuarioId = 1;
    private Playlist playlistAtual;

    @FXML
    public void initialize() {
        carregarPlaylists();

        listViewPlaylists.setCellFactory(lv -> new ListCell<Playlist>() {
            @Override
            protected void updateItem(Playlist playlist, boolean empty) {
                super.updateItem(playlist, empty);
                if (empty || playlist == null) {
                    setText(null);
                } else {
                    setText(playlist.getNome());
                }
            }
        });

        listViewMusicas.setCellFactory(lv -> new ListCell<Musica>() {
            @Override
            protected void updateItem(Musica musica, boolean empty) {
                super.updateItem(musica, empty);
                if (empty || musica == null) {
                    setText(null);
                } else {
                    setText(musica.getTitulo() + " - " + musica.getArtista());
                }
            }
        });

        listViewPlaylists.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                Playlist selecionada = listViewPlaylists.getSelectionModel().getSelectedItem();
                if (selecionada != null) {
                    abrirPlaylist(selecionada);
                }
            }
        });

        btnCriar.setOnAction(e -> criarPlaylist());
        btnRemover.setOnAction(e -> removerPlaylist());
        btnAtualizar.setOnAction(e -> {
            if (playlistAtual != null) {
                carregarMusicasDaPlaylist(playlistAtual.getId());
            } else {
                carregarPlaylists();
            }
        });
        btnVoltar.setOnAction(e -> voltarListaPlaylists());
        btnAdicionarMusica.setOnAction(e -> adicionarMusicaNaPlaylist());
    }

    public void carregarPlaylists() {
        try {
            listViewPlaylists.getItems().clear();
            listViewPlaylists.getItems().addAll(playlistManager.listarPlaylistsPorUsuario(usuarioId));
        } catch (Exception e) {
            mostrarErro("Erro ao carregar playlists", e.getMessage());
        }
    }

    private void criarPlaylist() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nova Playlist");
        dialog.setHeaderText("Digite o nome da nova playlist:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nome -> {
            Playlist p = new Playlist(nome, "", usuarioId);
            try {
                playlistManager.criarPlaylist(p);
                carregarPlaylists();
            } catch (Exception e) {
                mostrarErro("Erro ao criar playlist", e.getMessage());
            }
        });
    }

    private void removerPlaylist() {
        if (playlistAtual != null) {
            Playlist selecionada = playlistAtual;
            try {
                playlistManager.removerPlaylist(selecionada.getId());
                voltarListaPlaylists();
                carregarPlaylists();
            } catch (Exception e) {
                mostrarErro("Erro ao remover playlist", e.getMessage());
            }
            return;
        }
        Playlist selecionada = listViewPlaylists.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            try {
                playlistManager.removerPlaylist(selecionada.getId());
                carregarPlaylists();
            } catch (Exception e) {
                mostrarErro("Erro ao remover playlist", e.getMessage());
            }
        }
    }

    private void abrirPlaylist(Playlist playlist) {
        playlistAtual = playlist;
        carregarMusicasDaPlaylist(playlist.getId());
        listViewPlaylists.setVisible(false);
        listViewPlaylists.setManaged(false);
        listViewMusicas.setVisible(true);
        listViewMusicas.setManaged(true);
        btnVoltar.setVisible(true);
        btnVoltar.setManaged(true);
        btnAdicionarMusica.setVisible(true);
        btnAdicionarMusica.setManaged(true);
    }

    private void voltarListaPlaylists() {
        playlistAtual = null;
        listViewPlaylists.setVisible(true);
        listViewPlaylists.setManaged(true);
        listViewMusicas.setVisible(false);
        listViewMusicas.setManaged(false);
        btnVoltar.setVisible(false);
        btnVoltar.setManaged(false);
        btnAdicionarMusica.setVisible(false);
        btnAdicionarMusica.setManaged(false);
        carregarPlaylists();
    }

    private void carregarMusicasDaPlaylist(int playlistId) {
        try {
            List<Musica> musicas = playlistManager.listarMusicasDaPlaylist(playlistId);
            listViewMusicas.getItems().clear();
            if (musicas != null) {
                listViewMusicas.getItems().addAll(musicas);
            }
        } catch (Exception e) {
            mostrarErro("Erro ao carregar músicas da playlist", e.getMessage());
        }
    }

    private void adicionarMusicaNaPlaylist() {
        if (playlistAtual == null) return;
        Dialog<Musica> dialog = new Dialog<>();
        dialog.setTitle("Adicionar Música");
        dialog.setHeaderText("Selecione uma música para adicionar à playlist: " + playlistAtual.getNome());

        ButtonType adicionarButtonType = new ButtonType("Adicionar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(adicionarButtonType, ButtonType.CANCEL);

        ListView<Musica> listView = new ListView<>();
        try {
            List<Musica> todas = musicaRepository.findAll();
            List<Musica> jaPresentes = playlistManager.listarMusicasDaPlaylist(playlistAtual.getId());
            if (jaPresentes != null) {
                todas.removeAll(jaPresentes);
            }
            listView.getItems().addAll(todas);
        } catch (DataAccessException e) {
            mostrarErro("Erro ao carregar músicas", e.getMessage());
            return;
        }

        listView.setCellFactory(lv -> new ListCell<Musica>() {
            @Override
            protected void updateItem(Musica musica, boolean empty) {
                super.updateItem(musica, empty);
                if (empty || musica == null) {
                    setText(null);
                } else {
                    setText(musica.getTitulo() + " - " + musica.getArtista());
                }
            }
        });

        dialog.getDialogPane().setContent(listView);
        dialog.setResultConverter(buttonType -> {
            if (buttonType == adicionarButtonType) {
                return listView.getSelectionModel().getSelectedItem();
            }
            return null;
        });

        Optional<Musica> result = dialog.showAndWait();
        result.ifPresent(musica -> {
            try {
                playlistManager.adicionarMusicaNaPlaylist(playlistAtual.getId(), musica.getId(), 0);
                carregarMusicasDaPlaylist(playlistAtual.getId());
            } catch (Exception e) {
                mostrarErro("Erro ao adicionar música", e.getMessage());
            }
        });
    }

    private void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}