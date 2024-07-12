package com.example.demo.view;

import com.example.demo.Setting_Menu;
import com.example.demo.controller.Controller;
import com.example.demo.model.Card;
import com.example.demo.model.CardDatabase;
import com.example.demo.model.ShopMenu;
import com.example.demo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu {
    private Controller controller;
    private MediaPlayer mediaPlayer;
    public static User user ;
    public static User user_2 = null ;
    public static void setCard(User user){
        if(user.cards.size()==0){
            int number = CardDatabase.getNumberOfCards()-1;
            List<Integer> random_numbers = selectRandomNumbers(number);
            for(int i = 0 ; i<20 ;i++){
                user.cards.add(CardDatabase.getCardByIndex(random_numbers.get(i)));
            }
            System.out.println("20 cards add successfully to your cards");
        }
    }
    public void update(){
        CoinGet.setText(String.valueOf(user.getGold()));
        XPGet.setText(String.valueOf(user.getExperience()));
        HPGet.setText(String.valueOf(user.getHP()));
        LevelGet.setText(String.valueOf(user.getLevel()));
        Name.setText("WELCOME "+ user.getUsername());
        playBackgroundMusic("Hans Zimmer - Mountains (320).mp3");

    }
    public Color color ;
    @FXML
    private Label CoinGet ;
    @FXML
    private Label XPGet ;
    @FXML
    private Label HPGet ;

    @FXML
    private Label LevelGet ;
    @FXML
    private Label Name ;
    public static void runGame(ActionEvent event) throws IOException {
        if (user_2 != null) {
            System.out.println(user_2.getUsername());
            FXMLLoader loader = new FXMLLoader(MainMenu.class.getResource("/com/example/demo/chooseGame.fxml"));
            Parent root = loader.load();
            ChooseGameController controller = loader.getController();
            controller.setUser_2(user_2);
            controller.setUser_1(user);
            controller.initialize_1();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    public void Game_Start_MainMenu(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Login.fxml"));
        Parent root = loader.load();
        RegisterMenu controller = loader.getController();
        controller.rememberPassword.setVisible(false);
        controller.register.setVisible(false);
        user_2 = user ;
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }
    @FXML
    public void Log_out(MouseEvent event) throws IOException {
        user = null ;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Login.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }
    @FXML
    public void Setting(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/SettingMenu.fxml"));
        Parent root = loader.load();
        Setting_Menu controller = loader.getController();
        controller.user = user ;
        controller.mediaPlayer =mediaPlayer ;
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    public void Profil(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/ProfileMenu.fxml"));
        Parent root = loader.load();
        ProfileMenu controller = loader.getController();
        controller.setUser(user);
        controller.update();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }
    @FXML
    public void Shop(ActionEvent actionEvent) {
    }
    @FXML
    public void Game_History(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Historymenu.fxml"));
        Parent root = loader.load();
        HistoryMenu controller = loader.getController();
        controller.user = user ;
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }
    public void run(Scanner scanner, Controller controller, User user_1) {
        this.user = user_1 ;
        this.controller = controller;
        String input;
        Matcher matcher;
        while (true) {

            System.out.println("--------------------------------------");
            System.out.println("you are now in MainMenu");
            System.out.println("for see your profile enter: profile menu");
            System.out.println("for Start play game enter: go to game");
            System.out.println("for show cards enter: show cards");
            System.out.println("for go to shop enter: shop menu");
            System.out.println("for logout enter: logout");
            System.out.println("for see history enter: history");
            System.out.println("--------------------------------------");
            input = scanner.nextLine();
            if ((matcher = Command.getMatcher(input, Command.LOGOUT)) != null) {
                System.out.println("User " + user.getUsername() + " logged out successfully!");
                break;
            }
            else if ((matcher = Command.getMatcher(input, Command.SHOW_CURRENT_MENU)) != null) {
                System.out.println("Main Menu");
            }
            else if ((matcher = Command.getMatcher(input, Command.LIST_OF_USERS)) != null) {
                System.out.print(controller.listOfUsers());
            }
            else if ((matcher = Command.getMatcher(input, Command.SCOREBOARD)) != null) {
                System.out.print(controller.scoreboard());
            } else if ((matcher = Command.getMatcher(input, Command.PROFILE_MENU)) != null) {
                System.out.println("Entered profile menu!");
                new ProfileMenu().run(scanner, controller, user);
            }
            else if ((matcher = Command.getMatcher(input, Command.SHOP_MENU)) != null) {
                System.out.println("Entered shop menu!");
                new ShopMenu().showGameStore(user);
            }
            else if ((matcher = Command.getMatcher(input, Command.Show_cards)) != null) {
                for(Card card:user.cards){
                    System.out.print("card name: ");
                    System.out.println(card.getName());
                }
            }
            else if ((matcher = Command.getMatcher(input, Command.HISTORY)) != null) {
                System.out.println("Entered history menu!");
                new HistoryMenu();
            }
            else if ((matcher = Command.getMatcher(input, Command.START_GAME)) != null) {
                    boolean is_true = true ;
                    while (is_true){
                        System.out.println("choose mode of game");
                        System.out.println("1- Two player game");
                        System.out.println("2-Betting game");
                        input =scanner.nextLine();
                        switch (input){
                            case "1":
                                new GameMenu().run(scanner, controller, user , 1);
                                //1 for two player
                                is_true = false ;
                                break;
                            case "2":
                                new GameMenu().run(scanner, controller, user, 2);
                                //2 for betting game
                                is_true =false ;
                                break;
                            default:
                                System.out.println("invalid");
                                break;
                        }
                    }
            } else {
                System.out.println("Invalid command!");
            }
        }
    }
    public static List<Integer> selectRandomNumbers(int n) {
        List<Integer> selectedNumbers = new ArrayList<>();

        // اضافه کردن اعداد به لیست
        for (int i = 1; i <= n; i++) {
            selectedNumbers.add(i);
        }

        // تصادفی کردن اعضای لیست
        Collections.shuffle(selectedNumbers);

        // انتخاب اعداد
        List<Integer> result = selectedNumbers.subList(0, Math.min(20, n));

        return result;
    }

    public void setUser(User user) {
        this.user = user;
    }
    private void playBackgroundMusic(String musicFileName) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        String musicFile = "C:/Users/amira/intellij_project/graphic_proj/tokyo/demo/src/main/resources/com/example/demo/" + musicFileName;
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // برای تکرار بی‌نهایت
        mediaPlayer.play();
    }
    public void changeMusic(String musicFileName) {
        playBackgroundMusic(musicFileName);
    }

}
