package com.example.demo.view;

import com.example.demo.controller.Controller;
import com.example.demo.model.Card;
import com.example.demo.model.CardDatabase;
import com.example.demo.model.ShopMenu;
import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu {
    private Controller controller;
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
    public void run(Scanner scanner, Controller controller, User user) {
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
}
