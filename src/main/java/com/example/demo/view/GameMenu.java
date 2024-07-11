package com.example.demo.view;

import com.example.demo.controller.Controller;
import com.example.demo.controller.GameScoreDatabase;
import com.example.demo.model.Card;
import com.example.demo.model.CharacterGame;
import com.example.demo.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    private Controller controller;
    private String playingUsername;
    private String stoppingUsername;
    private GameScoreDatabase database;
    private int[][] player1Board; // Game board for player 1
    private int[][] player2Board; // Game board for player 2
    private int [] player1_background;
    private int [] player2_background;
    User user_1 ;
    User user_2 ;

    //1 full
    //0 empty
    //-1 break


    private CharacterGame HostPlayerCharactor ;
    private CharacterGame GuestPlayerCharactor ;
    public GameMenu() {
        initializePlayerBoard();
    }

    public void run(Scanner scanner, Controller controller, User user_1 , int mode) {
        boolean is_check = true ;
        while (is_check){
            System.out.println("--------------------------------------");
            System.out.println("now login the second user");
            System.out.println("for login enter: user login -u (?<username>.+) -p (?<password>.*\\S)");
            System.out.println("--------------------------------------");
            String input = scanner.nextLine() ;
            Matcher matcher = Command.getMatcher(input,Command.LOGIN);
            RegisterMenu registerMenu = new RegisterMenu();
            if(matcher!=null){
                if(registerMenu.login(matcher)==true){
                    user_2 = controller.getUserByUsername(matcher.group("username"));
                    if(user_2.getUsername().equals(user_1.getUsername())){
                        System.out.println("same user. try again");
                    }
                    else {
                        switch (mode){
                            case 1:
                                MainMenu.setCard(user_1);
                                user_1.addRandomCardsToBattleDeck();
                                MainMenu.setCard(user_2);
                                user_2.addRandomCardsToBattleDeck();
                                two_player_game(user_1 ,user_2);
                                is_check = false ;
                                break;
                            case 2:
                                MainMenu.setCard(user_1);
                                user_1.addrandomcardtobattledeck();
                                MainMenu.setCard(user_2);
                                user_2.addrandomcardtobattledeck();
                                betting_game(user_1,user_2);
                                is_check =false ;
                                break;
                        }
                    }
                }
            }
            else {
                System.out.println("try again");
            }
        }
    }
    private User prepareGame(User user1, User user2) {
        System.out.println("Preparing game...");

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
    private void initializePlayerBoard() {
        CharacterGame character1 = new CharacterGame("character1");
        CharacterGame character2 = new CharacterGame("character2");
        CharacterGame character3 = new CharacterGame("character3");
        CharacterGame character4 = new CharacterGame("character4");
        ArrayList<CharacterGame>charactors = new ArrayList<>();
        charactors.add(character1);
        charactors.add(character2);
        charactors.add(character3);
        charactors.add(character4);
        HostPlayerCharactor = CharacterGame.selectRandomCharacterGame(charactors);
        GuestPlayerCharactor = CharacterGame.selectRandomCharacterGame(charactors);
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

    private User playGame(User currentPlayer, User opponentPlayer, CharacterGame currentCharacter, CharacterGame opponentCharacter, int[][] currentBoard, int[][] opponentBoard) {
        Scanner scanner = new Scanner(System.in);
        int round = 1;

        while (round <= 7) {
            System.out.println("Round " + round + " - Player: " + currentPlayer.getUsername());
            printCardArrays(player1Board, player2Board, player1_background, player2_background, user_1, user_2, HostPlayerCharactor, GuestPlayerCharactor);

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


    public  void printCardArrays(int[][] player1Board , int[][] player2Board , int [] player1_background , int [] player2_background , User HostUser , User GuestUser , CharacterGame Host , CharacterGame guest) {
        System.out.println("player1 cards:");
        System.out.println("---------------------------------------------------");
        System.out.println(Controller.showDeck(user_1.getUsername()));
        System.out.println("player2 cards:");
        System.out.println("---------------------------------------------------");
        System.out.println(Controller.showDeck(user_2.getUsername()));
        System.out.println("---------------------------------------------------");
        System.out.print(user_1.getUsername()+" attack\t");
        for (int i = 0; i < 21; i++) {
            if(player1_background[i]==1){
                System.out.print(player1Board[i][0] + "\t");
            }
            if(player1_background[i]==0){
                System.out.print("empty\t");
            }
            if(player1_background[i]==-1){
                System.out.print("broken\t");
            }
        }
        System.out.println();

        System.out.print(user_1.getUsername()+ " strong\t");
        for (int i = 0; i < 21; i++) {
            if(player1_background[i]==1) {
                System.out.print(player1Board[i][1] + "\t");
            }
            if(player1_background[i]==0){
                System.out.print("empty\t");
            }
            if(player1_background[i]==-1){
                System.out.print("broken\t");
            }

        }
        System.out.println();

        System.out.print(user_2.getUsername()+" strong\t");
        for (int i = 0; i < 21; i++) {
            if(player2_background[i]==1){
                System.out.print(player2Board[i][1] + "\t");
            }

            if(player2_background[i]==0){
                System.out.print("empty\t");
            }
            if(player2_background[i]==-1){
                System.out.print("broken\t");
            }

        }
        System.out.println();
        System.out.print(user_2.getUsername()+" attack\t");
        for (int i = 0; i < 21; i++) {
            if(player2_background[i]==1) {
                System.out.print(player2Board[i][0] + "\t");
            }
            if(player2_background[i]==0){
                System.out.print("empty\t");
            }
            if(player2_background[i]==-1){
                System.out.print("broken\t");
            }
        }
        System.out.println();
        System.out.println("player1 Hp :"+HostUser.getHP());
        System.out.println("player2 Hp :"+GuestUser.getHP());
        System.out.println("Host caracter: "+ Host.getName());
        System.out.println("Guest caracter: "+ guest.getName());
//        System.out.println(round);

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
    public User two_player_game( User user_1 , User user_2){
        return prepareGame(user_1,user_2);
    }
    public void betting_game( User user_1 , User user_2){
        Scanner scanner = new Scanner(System.in);
        int number = 0 ;
        while (true){
            System.out.println("enter coin:");
            String input = scanner.nextLine();
             number = Integer.parseInt(input);
            if(user_1.getGold()>=number && user_2.getGold()>=number){
                user_1.changeGold(-1*number);
                user_2.changeGold(-1*number);
                System.out.println("decrease succssful");
                break;
            }
            else if ( user_1.getGold()<number) {
                System.out.println("user1 hasnot enough");
            }
            else if(user_2.getGold()<number){
                System.out.println("user2 hasnot enough");
            }
        }
        User user = two_player_game(user_1,user_2);
        if(user_1.getUsername().equals(user.getUsername())){
            user_1.changeGold(2*number);
        }
        else {
            user_2.changeGold(2*number);
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

}
