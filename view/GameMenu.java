package view;

import controller.Controller;
import model.User;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    private Controller controller;
    private String playingUsername;
    private String stoppingUsername;
    private int turnsLeft;
    private int[][] player1Board; // Game board for player 1
    private int[][] player2Board; // Game board for player 2

    public GameMenu() {
        this.player1Board = new int[21][2]; // Initialize player 1 game board
        this.player2Board = new int[21][2]; // Initialize player 2 game board
    }

    public void run(Scanner scanner, Controller controller, User user_1 , int mode) {
        User user_2 ;
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
                                two_player_game(controller,user_1 ,user_2);
                                is_check = false ;
                                break;
                            case 2:
                                betting_game(controller,user_1,user_2);
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
    private void prepareGame(User user1, User user2) {
        System.out.println("Preparing game...");
        Character character1 = selectCharacter(user1);
        Character character2 = selectCharacter(user2);

        initializePlayerBoard(player1Board);
        initializePlayerBoard(player2Board);

        // Randomly decide who starts the game
        Random random = new Random();
        boolean player1Starts = random.nextBoolean();

        System.out.println("Game starts!");

        if (player1Starts) {
            playGame(user1, user2, character1, character2, player1Board, player2Board);
        } else {
            playGame(user2, user1, character2, character1, player2Board, player1Board);
        }
    }

    private Character selectCharacter(User user) {
        // Simulate character selection (you can implement your logic here)
        Character character = new Character("Default Character", "Default Card");
        // Replace with your character selection logic based on user's choice or default
        return character;
    }

    private void initializePlayerBoard(int[][] board) {
        // Initialize the game board for a player
        for (int i = 0; i < board.length; i++) {
            // Initialize damagePlayer and attack to default values or as needed
            board[i][0] = 0; // damagePlayer
            board[i][1] = 0; // attack
        }
    }

    private void playGame(User currentPlayer, User opponentPlayer, Character currentCharacter, Character opponentCharacter,
                          int[][] currentBoard, int[][] opponentBoard) {
        Scanner scanner = new Scanner(System.in);
        int round = 1;

        while (round <= 7) {
            System.out.println("Round " + round + " - Player: " + currentPlayer.getUsername());

            // Display game boards
            displayGameBoard(currentPlayer, currentBoard);
            displayGameBoard(opponentPlayer, opponentBoard);

            // Example of placing a card
            placeCard(scanner, currentPlayer, currentCharacter, currentBoard);

            // Example of resolving attacks
            resolveAttacks(currentPlayer, opponentPlayer, currentBoard, opponentBoard);

            // Swap players for the next round
            User temp = currentPlayer;
            currentPlayer = opponentPlayer;
            opponentPlayer = temp;

            int[][] tempBoard = currentBoard;
            currentBoard = opponentBoard;
            opponentBoard = tempBoard;

            round++;
        }

        // Game end logic (calculate scores, update XP, coins, etc.)
        endGame(currentPlayer, opponentPlayer);
    }

    private void placeCard(Scanner scanner, User currentPlayer, Character currentCharacter, int[][] currentBoard) {
        System.out.println(currentPlayer.getUsername() + ", select a card to play:");

        // Example: Simulate card selection and placement
        Random random = new Random();
        int cardIndex = random.nextInt(5); // Assuming the player has 5 cards

        // Example: Simulate placing the card on the board
        int startPosition = random.nextInt(18); // Starting position on the board (0 to 18)
        int duration = random.nextInt(4) + 1; // Duration of the card (1 to 4)

        // Update board with card details
        for (int i = startPosition; i < startPosition + duration; i++) {
            currentBoard[i][0] += currentCharacter.cards.get(cardIndex).getPlayerDamage(); // Update damagePlayer
            currentBoard[i][1] += currentCharacter.getAttack(); // Update attack
        }

        System.out.println("Card placed successfully.");
    }

    private void resolveAttacks(User currentPlayer, User opponentPlayer, int[][] currentBoard, int[][] opponentBoard) {
        // Example: Resolve attacks between current player and opponent player
        // Update board based on game rules and logic
    }

    private void displayGameBoard(User player, int[][] board) {
        System.out.println(player.getUsername() + "'s Game Board:");
        for (int i = 0; i < board.length; i++) {
            System.out.println("Block " + i + ": damagePlayer=" + board[i][0] + ", attack=" + board[i][1]);
        }
    }

    private void endGame(User currentPlayer, User opponentPlayer) {
        // Implement your end game logic here
        // Calculate scores, update XP, coins, etc.
        System.out.println("Game over!");
    }
    public User two_player_game(Controller controller , User user_1 , User user_2){
        return null ;
    }
    public void betting_game(Controller controller , User user_1 , User user_2){
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
        User user = two_player_game(controller,user_1,user_2);
        if(user_1.getUsername().equals(user.getUsername())){
            user_1.changeGold(2*number);
        }
        else {
            user_2.changeGold(2*number);
        }
    }

}
