package view;

import controller.Controller;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu {
    private Controller controller;

    public void run(Scanner scanner, Controller controller, String username) {
        this.controller = controller;
        String input;
        Matcher matcher;
        while (true) {
            input = scanner.nextLine();
            if ((matcher = Command.getMatcher(input, Command.LOGOUT)) != null) {
                System.out.println("User " + username + " logged out successfully!");
                break;
            } else if ((matcher = Command.getMatcher(input, Command.SHOW_CURRENT_MENU)) != null) {
                System.out.println("Main Menu");
            } else if ((matcher = Command.getMatcher(input, Command.LIST_OF_USERS)) != null) {
                System.out.print(controller.listOfUsers());
            } else if ((matcher = Command.getMatcher(input, Command.SCOREBOARD)) != null) {
                System.out.print(controller.scoreboard());
            } else if ((matcher = Command.getMatcher(input, Command.PROFILE_MENU)) != null) {
                System.out.println("Entered profile menu!");
                new ProfileMenu().run(scanner, controller, username);
            } else if ((matcher = Command.getMatcher(input, Command.SHOP_MENU)) != null) {
                System.out.println("Entered shop menu!");
                new ShopMenu().run(scanner, controller, username);
            } else if ((matcher = Command.getMatcher(input, Command.START_GAME)) != null) {
                String result = startGame(matcher);
                System.out.println(result);
                if (result.matches("Battle.+")) {
                    new GameMenu().run(scanner,
                            controller,
                            username,
                            matcher.group("username"),
                            Integer.parseInt(matcher.group("turnsCount")));
                }
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private String startGame(Matcher matcher) {
        int turns = Integer.parseInt(matcher.group("turnsCount"));
        if (turns < 5 || turns > 30) {
            return "Invalid turns count!";
        } else if (!matcher.group("username").matches("[a-zA-Z]+")) {
            return "Incorrect format for username!";
        } else if (controller.getUserByUsername(matcher.group("username")) == null) {
            return "Username doesn't exist!";
        } else {
            return "Battle started with user " + matcher.group("username");
        }
    }
}
