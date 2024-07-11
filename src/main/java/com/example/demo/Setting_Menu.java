package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Setting_Menu {

    private MediaPlayer mediaPlayer;
    private File selectedFile;

    @FXML
    private Button back;

    @FXML
    private Button increas;

    @FXML
    private Button decreas;

    @FXML
    private Label musicLabel;

    @FXML
    private Button change;

    @FXML
    void back(ActionEvent event) {
        // Implement back button action if needed
    }

    @FXML
    void increas(ActionEvent event) {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.setVolume(mediaPlayer.getVolume() + 0.1);
        }
    }

    @FXML
    void decreas(ActionEvent event) {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.setVolume(mediaPlayer.getVolume() - 0.1);
        }
    }

    @FXML
    void change(ActionEvent event) {
        Stage stage = (Stage) change.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Music File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"));
        selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            playBackgroundMusic(selectedFile);
            musicLabel.setText(selectedFile.getName());
            // Call App's changeMusic method to change the music in App
            App app = new App();
            app.changeMusic(selectedFile.getAbsolutePath());
        }
    }

    private void playBackgroundMusic(File musicFile) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        Media sound = new Media(musicFile.toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }
}
