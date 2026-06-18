package com.mp3.mp3player.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import com.mp3.mp3player.controller.PlayerControl;
import com.mp3.mp3player.controller.PlaylistManager;
import com.mp3.mp3player.controller.FavoritesManager;

import java.io.IOException;

public class MainScreen {
    @FXML private VBox sidebar;
    @FXML private StackPane contentArea;
    @FXML private Button btnFavoritos;
    @FXML private Button btnPlaylists;
    @FXML private Button btnMusicas;

    private PlayerControl playerControl;
    private PlaylistManager playlistManager;
    private FavoritesManager favoritesManager;

    private Parent musicasView;
    private Parent favoritosView;
    private Parent playlistsView;

    private MusicasView musicasController;
    private FavoritosView favoritosController;
    private PlaylistScreen playlistsController;

    @FXML
    public void initialize() {
        playerControl = new PlayerControl();
        playlistManager = new PlaylistManager();
        favoritesManager = new FavoritesManager();

        try {
            FXMLLoader loaderMus = new FXMLLoader(getClass().getResource("/fxml/MusicasView.fxml"));
            musicasView = loaderMus.load();
            musicasController = loaderMus.getController();

            FXMLLoader loaderFav = new FXMLLoader(getClass().getResource("/fxml/FavoritosView.fxml"));
            favoritosView = loaderFav.load();
            favoritosController = loaderFav.getController();

            FXMLLoader loaderPlay = new FXMLLoader(getClass().getResource("/fxml/PlaylistsView.fxml"));
            playlistsView = loaderPlay.load();
            playlistsController = loaderPlay.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }

        btnFavoritos.setOnAction(e -> showView(favoritosView, favoritosController::carregarFavoritos));
        btnPlaylists.setOnAction(e -> showView(playlistsView, playlistsController::carregarPlaylists));
        btnMusicas.setOnAction(e -> showView(musicasView, musicasController::carregarMusicas));
        showView(musicasView, musicasController::carregarMusicas);
    }

    private void showView(Parent view, Runnable atualizador) {
        contentArea.getChildren().clear();
        if (view != null) {
            contentArea.getChildren().add(view);
        }
        if (atualizador != null) {
            atualizador.run();
        }
    }
}