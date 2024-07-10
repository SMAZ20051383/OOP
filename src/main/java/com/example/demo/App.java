package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.File;

public class App extends Application {

    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("SettingMenu.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        playBackgroundMusic("Hans Zimmer - Mountains (320).mp3");
    }

    private void playBackgroundMusic(String musicFileName) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        String musicFile = "E:/Git_Project4/OOP/src/main/resources/com/example/demo/" + musicFileName; // مسیر فایل موسیقی
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // برای تکرار بی‌نهایت
        mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void changeMusic(String musicFileName) {
        playBackgroundMusic(musicFileName);
    }
}
