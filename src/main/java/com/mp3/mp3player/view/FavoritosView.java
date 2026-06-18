package com.mp3.mp3player.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import com.mp3.mp3player.controller.IPlayerStarter;
import com.mp3.mp3player.controller.PlayerControl;
import com.mp3.mp3player.controller.FavoritesManager;
import com.mp3.mp3player.model.Musica;

import java.util.List;
import java.util.Optional;

public class FavoritosView {
    @FXML private ListView<Musica> listView;
    @FXML private Button btnAtualizar;
    @FXML private Button btnRemover;

    private FavoritesManager favoritesManager = new FavoritesManager();
    private IPlayerStarter playerControl = new PlayerControl();
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

        carregarFavoritos();

        btnAtualizar.setOnAction(e -> carregarFavoritos());
        btnRemover.setOnAction(e -> removerFavoritoSelecionado());

        listView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                Musica selecionada = listView.getSelectionModel().getSelectedItem();
                if (selecionada != null) {
                    playerControl.setFila(List.of(selecionada));
                    playerControl.play();
                }
            }
        });
    }

    public void carregarFavoritos() {
        listView.getItems().clear();
        listView.getItems().addAll(favoritesManager.listarFavoritas(usuarioId));
    }

    private void removerFavoritoSelecionado() {
        Musica selecionada = listView.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhuma seleção");
            alert.setHeaderText(null);
            alert.setContentText("Selecione uma música para remover dos favoritos.");
            alert.showAndWait();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar remoção");
        confirm.setHeaderText("Remover dos favoritos?");
        confirm.setContentText("Deseja remover '" + selecionada.getTitulo() + " - " + selecionada.getArtista() + "' dos favoritos?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                favoritesManager.removerFavorita(usuarioId, selecionada.getId());
                carregarFavoritos();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Falha ao remover dos favoritos");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }
}