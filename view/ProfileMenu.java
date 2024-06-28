package view;

import controller.Controller;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {
    private Controller controller;
    private String currentUsername;

    public void run(Scanner scanner, Controller controller, String username) {
        this.controller = controller;
        this.currentUsername = username;
        String input;
        Matcher matcher;
        while (true) {
            input = scanner.nextLine();
            if ((matcher = Command.getMatcher(input, Command.BACK)) != null) {
                System.out.println("Entered main menu!");
                break;
            } else if ((matcher = Command.getMatcher(input, Command.SHOW_CURRENT_MENU)) != null) {
                System.out.println("Profile Menu");
            } else if ((matcher = Command.getMatcher(input, Command.CHANGE_PASSWORD)) != null) {
                System.out.println(changePassword(matcher));
            } else if ((matcher = Command.getMatcher(input, Command.INFO)) != null) {
                System.out.println(controller.info(username));
            } else if ((matcher = Command.getMatcher(input, Command.REMOVE_FROM_DECK)) != null) {
                System.out.println(removeFromDeck(matcher));
            } else if ((matcher = Command.getMatcher(input, Command.ADD_TO_DECK)) != null) {
                System.out.println(addToDeck(matcher));
            } else if ((matcher = Command.getMatcher(input, Command.SHOW_DECK)) != null) {
                System.out.print(controller.showDeck(currentUsername));
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private String changePassword(Matcher matcher) {
        if (controller.passwordIsWrong(currentUsername, matcher.group("oldPassword"))) {
            return "Incorrect password!";
        } else if (controller.passwordFormatIsBad(matcher.group("newPassword"))) {
            return "Incorrect format for new password!";
        } else {
            controller.changePassword(currentUsername, matcher.group("newPassword"));
            return "Password changed successfully!";
        }
    }

    private String removeFromDeck(Matcher matcher) {
        if (controller.isCardNameWrong(matcher.group("cardName"))) {
            return "Invalid card name!";
        } else if (controller.getCardFromDeck(currentUsername, matcher.group("cardName")) == null) {
            return "This card isn't in your battle deck!";
        }
         else {
            controller.removeFromDeck(currentUsername, matcher.group("cardName"));
            return "Card " + matcher.group("cardName") + " removed successfully!";
        }
    }

    private String addToDeck(Matcher matcher) {
        if (controller.isCardNameWrong(matcher.group("cardName"))) {
            return "Invalid card name!";
        } else if (!controller.userHasCard(currentUsername, matcher.group("cardName"))) {
            return "You don't have this card!";
        } else if (controller.getCardFromDeck(currentUsername, matcher.group("cardName")) != null) {
            return "This card is already in your battle deck!";
        }  else {
            controller.addToDeck(currentUsername, matcher.group("cardName"));
            return "Card " + matcher.group("cardName") + " added successfully!";
        }
    }
}
