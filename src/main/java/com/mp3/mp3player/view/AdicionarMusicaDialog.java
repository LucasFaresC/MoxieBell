package com.mp3.mp3player.view;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import com.mp3.mp3player.model.Musica;
import com.mp3.mp3player.repository.MusicaRepository;
import java.io.File;

public class AdicionarMusicaDialog {
    public static void showDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos MP3", "*.mp3"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile == null) return;

        String titulo = selectedFile.getName().replaceFirst("[.][^.]+$", "");
        String artista = "Desconhecido";
        String album = "Desconhecido";
        String genero = "Desconhecido";
        double duracao = 0.0;

        try {
            org.jaudiotagger.audio.AudioFile audioFile = org.jaudiotagger.audio.AudioFileIO.read(selectedFile);
            org.jaudiotagger.tag.Tag tag = audioFile.getTag();
            if (tag != null) {
                titulo = tag.getFirst(org.jaudiotagger.tag.FieldKey.TITLE);
                if (titulo.isEmpty()) titulo = selectedFile.getName().replaceFirst("[.][^.]+$", "");
                artista = tag.getFirst(org.jaudiotagger.tag.FieldKey.ARTIST);
                if (artista.isEmpty()) artista = "Desconhecido";
                album = tag.getFirst(org.jaudiotagger.tag.FieldKey.ALBUM);
                if (album.isEmpty()) album = "Desconhecido";
                genero = tag.getFirst(org.jaudiotagger.tag.FieldKey.GENRE);
                if (genero.isEmpty()) genero = "Desconhecido";
                duracao = audioFile.getAudioHeader().getTrackLength();
            }
        } catch (Exception ignored) {}

        MusicaRepository repository = new MusicaRepository();
        String caminho = selectedFile.getAbsolutePath();

        try {
            Musica existente = repository.findByCaminho(caminho);
            if (existente != null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Música já existe");
                alert.setHeaderText("Esta música já está cadastrada");
                alert.setContentText("Música: " + existente.getTitulo() + " - " + existente.getArtista());
                alert.showAndWait();
                return;
            }

            Musica musica = new Musica(0, titulo, artista, album, genero, duracao, caminho);
            repository.save(musica);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Música adicionada!");
            alert.setContentText("Música: " + titulo + " - " + artista);
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao salvar música");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}