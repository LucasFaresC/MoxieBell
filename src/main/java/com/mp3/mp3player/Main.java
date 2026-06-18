package com.mp3.mp3player;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    public void start(Stage primaryStage) throws Exception {
        Logger.getLogger("org.jaudiotagger").setLevel(Level.SEVERE);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainScreen.fxml"));
        primaryStage.setTitle("MoxieBell MP3");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}