package com.example.demo;

import com.example.demo.App;
import com.example.demo.controller.Controller;
import com.example.demo.model.User;
import com.example.demo.view.MainMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Setting_Menu {
    public User user ;

    public MediaPlayer mediaPlayer;
    public File selectedFile;
    @FXML
    private ColorPicker colorPicker;

    private String savedColor;
    private Color color ;
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
    void stopMusic(ActionEvent event) {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.setVolume(0);
        }
    }
    @FXML
    public void play(ActionEvent actionEvent) {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.setVolume(.5);
        }
    }


    @FXML
    void back(ActionEvent event) throws IOException {
        color = colorPicker.getValue();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Main_Menu.fxml"));
        Parent root = loader.load();
        MainMenu controller = loader.getController();
        controller.setUser(Controller.getUserByUsername(user.getUsername()));
        controller.color = color ;
        System.out.println(color);
        controller.update();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));

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
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"));
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
