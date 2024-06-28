package view;

import controller.Controller;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu {
    private Controller controller;
    private String currentUsername;

    public void run(Scanner scanner, Controller controller, String username) {
        this.controller = controller;
        currentUsername = username;
        String input;
        Matcher matcher;
        while (true) {
            input = scanner.nextLine();
            if ((matcher = Command.getMatcher(input, Command.BACK)) != null) {
                System.out.println("Entered main menu!");
                break;
            } else if ((matcher = Command.getMatcher(input, Command.SHOW_CURRENT_MENU)) != null) {
                System.out.println("Shop Menu");
            } else if ((matcher = Command.getMatcher(input, Command.BUY_CARD)) != null) {
                System.out.println(buyCard(matcher));
            } else if ((matcher = Command.getMatcher(input, Command.SELL_CARD)) != null) {
                System.out.println(sellCard(matcher));
            } else {
                System.out.println("Invalid command!");
            }
        }
    }


    private String buyCard(Matcher matcher) {
        return "1"; // باید نوشته بشه
    }

    private String sellCard(Matcher matcher) {
        if (controller.isCardNameWrong(matcher.group("cardName"))) {
            return "Invalid card name!";
        } else if (controller.getCardFromCards(currentUsername, matcher.group("cardName")) == null) {
            return "You don't have this card!";
        } else if (controller.getCardFromDeck(currentUsername, matcher.group("cardName")) != null) {
            return "You cannot sell a card from your battle deck!";
        } else {
            controller.sellCard(currentUsername, matcher.group("cardName"));
            return "Card " + matcher.group("cardName") + " sold successfully!";
        }
    }

}
