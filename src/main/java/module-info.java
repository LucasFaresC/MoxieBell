module com.mp3.mp3player {
    requires java.sql;
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.postgresql.jdbc;
    requires jaudiotagger;
    requires jlayer;
    requires mp3spi;

    exports com.mp3.mp3player;
    opens com.mp3.mp3player.view to javafx.fxml;
    opens com.mp3.mp3player.controller to javafx.fxml;
}