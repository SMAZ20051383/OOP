package com.example.demo.view;

import com.example.demo.controller.Controller;
import com.example.demo.controller.GameScoreDatabase;
import com.example.demo.model.Card;
import com.example.demo.model.CharacterGame;
import com.example.demo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenuGraphic {
    private CharacterGame HostPlayerCharactor ;
    private CharacterGame GuestPlayerCharactor ;
    private int[][] player1Board; // Game board for player 1
    private int[][] player2Board; // Game board for player 2
    private int [] player1_background;
    private int [] player2_background;
    private GameScoreDatabase database;


    public static User user_1 ,user_2;
    public void endGame(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Main_Menu.fxml"));
        Parent root = loader.load();
        MainMenu controller = loader.getController();
        controller.setUser(Controller.getUserByUsername(user_1.getUsername()));
        controller.update();
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
    @FXML
    private Pane GuestCard1 ;
    @FXML
    private Pane GuestCard2 ;
    @FXML
    private Pane GuestCard3 ;
    @FXML
    private Pane GuestCard4 ;
    @FXML
    private Pane GuestCard0 ;
    @FXML
    private Pane HostCard1 ;
    @FXML
    private Pane HostCard2 ;
    @FXML
    private Pane HostCard3 ;
    @FXML
    private Pane HostCard4 ;
    @FXML
    private Pane HostCard0 ;

    @FXML private Label CardName1, CardAttack1, CardStrong1, Duration1;
    @FXML private Label CardName2, CardAttack2, CardStrong2, Duration2;
    @FXML private Label CardName3, CardAttack3, CardStrong3, Duration3;
    @FXML private Label CardName4, CardAttack4, CardStrong4, Duration4;
    @FXML private Label CardName5, CardAttack5, CardStrong5, Duration5;
    @FXML private Label CardName6, CardAttack6, CardStrong6, Duration6;
    @FXML private Label CardName7, CardAttack7, CardStrong7, Duration7;
    @FXML private Label CardName, CardAttack, CardStrong, Duration;
    @FXML private Label CardName12, CardAttack12, CardStrong12, Duration12;
    @FXML private Label CardName11, CardAttack11, CardStrong11, Duration11;
    @FXML
    private GridPane cardpane ;
    @FXML
    private Label PLAYER1HP ;
    @FXML
    private Label PLAYER2HP ;
    @FXML
    private Label Player1Name ;
    @FXML
    private Label Player2Name ;



    public void initialize_1() {
//        for (int i = 2; i <= 5; i++) {
//            for (int j = 0; j <= 20; j++) {
//                Pane pane = getNodeByRowColumnIndex(i, j, cardpane);
//                if (pane != null) {
//                    pane.setStyle("-fx-border-color: black; -fx-border-width: 1;");
//                }
//            }
//        }
        user_1.addrandomcardtobattledeck();
        MainMenu.setCard(user_2);
        user_1.addRandomCardsToBattleDeck();
        MainMenu.setCard(user_2);
        CharacterGame character1 = new CharacterGame("character1");
        CharacterGame character2 = new CharacterGame("character2");
        CharacterGame character3 = new CharacterGame("character3");
        CharacterGame character4 = new CharacterGame("character4");
        ArrayList<CharacterGame> charactors = new ArrayList<>();
        charactors.add(character1);
        charactors.add(character2);
        charactors.add(character3);
        charactors.add(character4);
        HostPlayerCharactor = CharacterGame.setChar(charactors,user_1.character);
        GuestPlayerCharactor = CharacterGame.setChar(charactors, user_2.character);
        this.player1Board = new int[21][2];
        this.player2Board = new int[21][2];
        this.player1_background = new int[21];
        this.player2_background = new int [21];
        for (int i = 0; i < 21; i++) {
            player1Board[i][0] = 0;
            player1Board[i][1] =0 ;
            player2Board[i][0]=0;
            player2Board[i][0]=0;
            player1_background[i]=0;
            player2_background[i]=0;
        }
        Random random = new Random();
        int randomNumber = random.nextInt(21) ;
        player1_background[randomNumber]=-1;
        randomNumber = random.nextInt(21) ;
        player2_background[randomNumber]=-1 ;
        updateCardLabels(cardpane);
        PLAYER1HP.setText(String.valueOf(user_1.getHP()));
        PLAYER2HP.setText(String.valueOf(user_2.getHP()));
        Player1Name.setText(String.valueOf(user_1.getUsername()));
        Player2Name.setText(String.valueOf(user_2.getUsername()));
    }
    private User prepareGame(User user1, User user2) {
        // Randomly decide who starts the game
        Random random = new Random();
        boolean player1Starts = random.nextBoolean();
        user_1 = user1;
        user_2=user2;
        System.out.println("Game starts!");

        if (player1Starts) {
            return playGame(user1, user2, HostPlayerCharactor, GuestPlayerCharactor, player1Board, player2Board);
        } else {
            return playGame(user2, user1, GuestPlayerCharactor, HostPlayerCharactor, player2Board, player1Board);
        }
    }
    private User playGame(User currentPlayer, User opponentPlayer, CharacterGame currentCharacter, CharacterGame opponentCharacter, int[][] currentBoard, int[][] opponentBoard) {
        Scanner scanner = new Scanner(System.in);
        int round = 1;

        while (round <= 7) {
            System.out.println("Round " + round + " - Player: " + currentPlayer.getUsername());

            placeCard(scanner, currentPlayer);

            // Swap players and boards for the next round
            User tempUser = currentPlayer;
            currentPlayer = opponentPlayer;
            opponentPlayer = tempUser;

            int[][] tempBoard = currentBoard;
            currentBoard = opponentBoard;
            opponentBoard = tempBoard;

            CharacterGame tempCharacter = currentCharacter;
            currentCharacter = opponentCharacter;
            opponentCharacter = tempCharacter;

            round++;
        }

        // Game end logic (calculate scores, update XP, coins, etc.)
        return endGame();
    }
    private void placeCard(Scanner scanner, User currentPlayer ) {
        boolean is_true = true ;
        while (is_true) {
            System.out.println(currentPlayer.getUsername() + ", select a card to play:");
            System.out.println("format: -select card number (?<number>.+)");
            String input = scanner.nextLine();
            Matcher matcher = Command.getMatcher(input, Command.Choose_card);
            System.out.println(matcher.group("number"));
            if (matcher != null) {
                try {
                    int number_1 = Integer.parseInt(matcher.group("number"));
                    if (number_1 >= 0 && number_1 < currentPlayer.getBattleDeck().size()) {
                        Card card = currentPlayer.getBattleDeck().get(number_1);
                        System.out.println(card.getName());
                        System.out.println("Card attack: " + card.getAttack());
                        System.out.println("Card duration: " + card.getDuration());
                        System.out.println("Card Strong: " + card.getDamage());
                        System.out.println("for continue enter the position and for back to choose another card enter -1");
                        input = scanner.nextLine();
                        int number = Integer.parseInt(input);
                        switch (number) {
                            case -1:
                                // break the switch to go back to choosing card
                                break;
                            default:
                                if (check_empty(number, card.getDuration(), currentPlayer)) {
                                    add_card_to_array(number, card, currentPlayer);
                                    currentPlayer.getBattleDeck().remove(number_1);
                                    currentPlayer.addrandomcardtobattledeck();
                                    System.out.println("card inserted successfully");
                                    is_true = false;
                                } else {
                                    System.out.println("Position is not empty. Try again.");
                                }
                                break;
                        }
                    } else {
                        System.out.println("Invalid card number. Try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid card number.");
                }
            } else {
                System.out.println("Invalid command. Try again.");
            }
        }
    }
    public boolean check_empty(int num,int duration , User currentUser){
        if(num+duration>21){
            return false ;
        }
        if(currentUser.getUsername().equals(user_1.getUsername())){
            for(int i = num ; i<(num+duration) ; i++){
                if(player1_background[i]!=0){
                    return false ;
                }
            }
            return true ;
        }
        else {
            for(int i = num ; i<(num+duration) ; i++){
                if(player2_background[i]!=0){
                    return false ;
                }
            }
            return true ;

        }
    }
    public void add_card_to_array(int num,Card card,User currentuser){
        if(currentuser.getUsername().equals(user_1.getUsername())){
            int card_attack = card.getAttack();
            if(HostPlayerCharactor.haveThisCard(card)){
                card_attack*=1.25;
            }
            for(int i = num; i<num+card.getDuration() ; i++){
                player1_background[i]=1;
                player1Board[i][0]= card_attack;
                player1Board[i][1]= card.getDamage();
            }
        }
        else {
            int card_attack = card.getAttack();
            if(GuestPlayerCharactor.haveThisCard(card)){
                card_attack*=1.25;
            }
            for(int i = num; i<num+card.getDuration() ; i++){
                player2_background[i]=1;
                player2Board[i][0]= card_attack;
                player2Board[i][1]= card.getDamage();
            }

        }
    }
    private User endGame() {
        int user_1Level = user_1.getLevel();
        int user_2Level = user_2.getLevel();
        int HostuserHp = user_1.getHP();
        int GuestHp = user_2.getHP();
        for(int i = 0 ; i<21;i++){
            System.out.println(user_1.getUsername()+" HP: "+ HostuserHp);
            System.out.println(user_2.getUsername()+" HP: "+ GuestHp);
            int attack_1 = 0 ;
            int attack_2 = 0 ;
            if(player1Board[i][1]>player2Board[i][1]){
                attack_1=player1Board[i][0];
            }
            else if(player2Board[i][1]>player1Board[i][1]){
                attack_2=player2Board[i][0];
            }
            GuestHp-=attack_1;
            HostuserHp-=attack_2;
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String date = now.format(dateFormatter);
            String time = now.format(timeFormatter);


            if(GuestHp<=0 || (i==20&&GuestHp<HostuserHp)){
                System.out.println("user2 died in place :"+ i);
                user_2.addXp(10*(i));
                user_1.addXp(50*(21-i));
                user_2.changeGold(5*i);
                user_1.changeGold(20*i);
                int achive = 20*i;
                database.addGameScore(date, time, user_1.getUsername(), user_2.getUsername(), user_2Level, "won", String.valueOf(achive));
                System.out.println("new user_1 XP is: "+ user_1.getHP());
                System.out.println("new user_2 XP is: "+ user_2.getHP());
                if(user_1.getLevel()>user_1Level){
                    System.out.println("user1 level up :"+ (user_1.getLevel()-user_1Level) +" level");
                }
                if(user_2.getLevel()>user_2Level){
                    System.out.println("user2 level up :"+ (user_2.getLevel()-user_1Level) +" level");
                }
                return user_1;
            }
            if((HostuserHp<=0) || (i==20&&GuestHp>HostuserHp)){
                database = new GameScoreDatabase();
                System.out.println("user1 died in place :" + i);
                user_1.addXp(10*i);
                user_2.addXp(50*(21-i));
                System.out.println("new user_1 XP is: "+ user_1.getExperience());
                System.out.println("new user_2 XP is: "+ user_2.getExperience());
                user_1.changeGold(5*i);
                user_2.changeGold(20*i);
                int achive = 5*i ;
                database.addGameScore(date, time, user_1.getUsername(), user_2.getUsername(), user_2Level, "lose", String.valueOf(achive));
                if(user_1.getLevel()>user_1Level){
                    System.out.println("user1 level up :"+ (user_1.getLevel()-user_1Level) +" level");
                }
                if(user_2.getLevel()>user_2Level){
                    System.out.println("user2 level up :"+ (user_2.getLevel()-user_2Level) +" level");
                }
                return user_2;
            }
        }

        return null ;
    }
    public void updateCardLabels(GridPane cardPane) {
        String paneId = cardPane.getId();

        switch (paneId) {
            case "GuestCard1":
                updateLabelsForGuestCard1();
                break;
            case "GuestCard2":
                updateLabelsForGuestCard2();
                break;
            case "GuestCard3":
                updateLabelsForGuestCard3();
                break;
            case "GuestCard4":
                updateLabelsForGuestCard4();
                break;
            case "GuestCard5":
                updateLabelsForGuestCard5();
                break;
            case "GuestCard6":
                updateLabelsForGuestCard6();
                break;
            case "GuestCard7":
                updateLabelsForGuestCard7();
                break;
            case "GuestCard":
                updateLabelsForGuestCard();
                break;
            case "GuestCard11":
                updateLabelsForGuestCard11();
                break;
            case "GuestCard12":
                updateLabelsForGuestCard12();
                break;
            default:
                System.out.println("Unknown pane ID: " + paneId);
                break;
        }
    }

    private void updateLabelsForGuestCard1() {
        CardName1.setText("Name: " + user_1.battleDeck.get(0).getName());
        CardAttack1.setText("Attack: " + user_1.battleDeck.get(0).getAttack());
        CardStrong1.setText("Strong: " + user_1.battleDeck.get(0).getDamage());
        Duration1.setText("Duration: " + user_1.battleDeck.get(0).getDuration());
    }

    private void updateLabelsForGuestCard2() {
        CardName2.setText("Name: " + user_1.battleDeck.get(1).getName());
        CardAttack2.setText("Attack: " + user_1.battleDeck.get(1).getAttack());
        CardStrong2.setText("Strong: " + user_1.battleDeck.get(1).getDamage());
        Duration2.setText("Duration: " + user_1.battleDeck.get(1).getDuration());
    }

    private void updateLabelsForGuestCard3() {
        CardName3.setText("Name: " + user_1.battleDeck.get(2).getName());
        CardAttack3.setText("Attack: " + user_1.battleDeck.get(2).getAttack());
        CardStrong3.setText("Strong: " + user_1.battleDeck.get(2).getDamage());
        Duration3.setText("Duration: " + user_1.battleDeck.get(2).getDuration());
    }

    private void updateLabelsForGuestCard4() {
        CardName4.setText("Name: " + user_2.battleDeck.get(0).getName());
        CardAttack4.setText("Attack: " + user_2.battleDeck.get(0).getAttack());
        CardStrong4.setText("Strong: " + user_2.battleDeck.get(0).getDamage());
        Duration4.setText("Duration: " + user_2.battleDeck.get(0).getDuration());
    }

    private void updateLabelsForGuestCard5() {
        CardName5.setText("Name: " + user_2.battleDeck.get(1).getName());
        CardAttack5.setText("Attack: " + user_2.battleDeck.get(1).getAttack());
        CardStrong5.setText("Strong: " + user_2.battleDeck.get(1).getDamage());
        Duration5.setText("Duration: " + user_2.battleDeck.get(1).getDuration());
    }

    private void updateLabelsForGuestCard6() {
        CardName6.setText("Name: " + user_2.battleDeck.get(2).getName());
        CardAttack6.setText("Attack: " + user_2.battleDeck.get(2).getAttack());
        CardStrong6.setText("Strong: " + user_2.battleDeck.get(2).getDamage());
        Duration6.setText("Duration: " + user_2.battleDeck.get(2).getDuration());
    }

    private void updateLabelsForGuestCard7() {
        CardName7.setText("Name: " + user_2.battleDeck.get(3).getName());
        CardAttack7.setText("Attack: " + user_2.battleDeck.get(3).getAttack());
        CardStrong7.setText("Strong: " + user_2.battleDeck.get(3).getDamage());
        Duration7.setText("Duration: " + user_2.battleDeck.get(3).getDuration());
    }

    private void updateLabelsForGuestCard() {
        CardName.setText("Name: " + user_2.battleDeck.get(4).getName());
        CardAttack.setText("Attack: " + user_2.battleDeck.get(4).getAttack());
        CardStrong.setText("Strong: " + user_2.battleDeck.get(4).getDamage());
        Duration.setText("Duration: " + user_2.battleDeck.get(4).getDuration());
    }

    private void updateLabelsForGuestCard11() {
        CardName11.setText("Name: " + user_1.battleDeck.get(3).getName());
        CardAttack11.setText("Attack: " + user_1.battleDeck.get(3).getAttack());
        CardStrong11.setText("Strong: " + user_1.battleDeck.get(3).getDamage());
        Duration11.setText("Duration: " + user_1.battleDeck.get(3).getDuration());
    }

    private void updateLabelsForGuestCard12() {
        CardName12.setText("Name: " + user_1.battleDeck.get(4).getName());
        CardAttack12.setText("Attack: " + user_1.battleDeck.get(4).getAttack());
        CardStrong12.setText("Strong: " + user_1.battleDeck.get(4).getDamage());
        Duration12.setText("Duration: " + user_1.battleDeck.get(4).getDuration());
    }
    private Pane getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return (Pane) node;
            }
        }
        return null;
    }

}
