package com.mp3.mp3player.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import com.mp3.mp3player.model.Musica;
import com.mp3.mp3player.repository.MusicaRepository;
import com.mp3.mp3player.exceptions.DataAccessException;
import com.mp3.mp3player.controller.PlayerControl;
import com.mp3.mp3player.controller.FavoritesManager;

import java.util.List;
import java.util.Optional;

public class MusicasView {
    @FXML private ListView<Musica> listView;
    @FXML private Button btnAtualizar;
    @FXML private Button btnAdicionar;
    @FXML private Button btnExcluir;
    @FXML private Button btnFavoritar;

    private MusicaRepository musicaRepository = new MusicaRepository();
    private PlayerControl playerControl = new PlayerControl();
    private FavoritesManager favoritesManager = new FavoritesManager();
    private int usuarioId = 1;

    @FXML
    public void initialize() {
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

        carregarMusicas();

        btnAtualizar.setOnAction(e -> carregarMusicas());
        btnAdicionar.setOnAction(e -> adicionarMusica());
        btnExcluir.setOnAction(e -> excluirMusicaSelecionada());
        btnFavoritar.setOnAction(e -> favoritarOuDesfavoritar());

        listView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                Musica selecionada = listView.getSelectionModel().getSelectedItem();
                if (selecionada != null) {
                    playerControl.setFila(List.of(selecionada));
                    playerControl.play();
                }
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            atualizarTextoBotaoFavoritar(newVal);
        });
    }

    public void carregarMusicas() {
        try {
            listView.getItems().clear();
            listView.getItems().addAll(musicaRepository.findAll());
            Musica selecionada = listView.getSelectionModel().getSelectedItem();
            atualizarTextoBotaoFavoritar(selecionada);
        } catch (DataAccessException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao carregar músicas");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void adicionarMusica() {
        AdicionarMusicaDialog.showDialog();
        carregarMusicas();
    }

    private void excluirMusicaSelecionada() {
        Musica selecionada = listView.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhuma seleção");
            alert.setHeaderText(null);
            alert.setContentText("Selecione uma música para excluir.");
            alert.showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar exclusão");
        confirm.setHeaderText("Tem certeza que deseja excluir a música?");
        confirm.setContentText("Música: " + selecionada.getTitulo() + " - " + selecionada.getArtista());
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                musicaRepository.delete(selecionada.getId());
                carregarMusicas();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Falha ao excluir música");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    private void favoritarOuDesfavoritar() {
        Musica selecionada = listView.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhuma seleção");
            alert.setHeaderText(null);
            alert.setContentText("Selecione uma música para favoritar ou desfavoritar.");
            alert.showAndWait();
            return;
        }
        try {
            if (isFavorita(selecionada.getId())) {
                favoritesManager.removerFavorita(usuarioId, selecionada.getId());
            } else {
                favoritesManager.favoritarMusica(usuarioId, selecionada.getId());
            }
            carregarMusicas();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao alterar favorito");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private boolean isFavorita(int musicaId) {
        try {
            List<Musica> favoritas = favoritesManager.listarFavoritas(usuarioId);
            if (favoritas == null) return false;
            return favoritas.stream().anyMatch(m -> m.getId() == musicaId);
        } catch (Exception e) {
            return false;
        }
    }

    private void atualizarTextoBotaoFavoritar(Musica musica) {
        if (musica == null) {
            btnFavoritar.setText("Favoritar");
            return;
        }
        if (isFavorita(musica.getId())) {
            btnFavoritar.setText("Desfavoritar");
        } else {
            btnFavoritar.setText("Favoritar");
        }
    }
}