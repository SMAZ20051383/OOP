package model;

import java.util.ArrayList;
import java.util.Scanner;

public class ShopMenu {
    private static Scanner scanner = new Scanner(System.in);

    public static void showGameStore(User user) {
        System.out.println("Welcome to the Game Store!");
        System.out.println("1. Buy Cards");
        System.out.println("2. Upgrade Cards");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        switch (choice) {
            case 1:
                buyCards(user);
                break;
            case 2:
                upgradeCards(user);
                break;
            case 3:
                System.out.println("Exiting Game Store...");
                break;
            default:
                System.out.println("Invalid choice. Please choose again.");
                showGameStore(user);
        }
    }

    private static void buyCards(User user) {
        ArrayList<Card> availableCards = Card.getAvailableCards(user);
        System.out.println("\nAvailable Cards for Purchase:");
        for (Card card : availableCards) {
            System.out.println("Name: " + card.getName() + ", Cost: " + card.getCost());
        }
        System.out.print("\nEnter the name of the card you want to buy (or 'back' to return): ");
        String cardName = scanner.nextLine();

        if (cardName.equalsIgnoreCase("back")) {
            showGameStore(user);
            return;
        }

        Card selectedCard = null;
        for (Card card : availableCards) {
            if (card.getName().equalsIgnoreCase(cardName)) {
                selectedCard = card;
                break;
            }
        }

        if (selectedCard == null) {
            System.out.println("Card not found. Please try again.");
            buyCards(user);
            return;
        }

        if (user.getGold() >= selectedCard.getCost()) {
            user.addToCard(selectedCard);
            user.changeGold(-selectedCard.getCost());
            System.out.println("Congratulations! You've successfully bought the card: " + selectedCard.getName());
        } else {
            System.out.println("You don't have enough gold to buy this card.");
        }

        showGameStore(user);
    }

    private static void upgradeCards(User user) {
        ArrayList<Card> upgradableCards = Card.getUpgradableCards(user);
        System.out.println("\nUpgradable Cards:");
        for (Card card : upgradableCards) {
            System.out.println("Name: " + card.getName() + ", Upgrade Cost: " + ((Upgradable) card).getUpgradeCost());
        }
        System.out.print("\nEnter the name of the card you want to upgrade (or 'back' to return): ");
        String cardName = scanner.nextLine();

        if (cardName.equalsIgnoreCase("back")) {
            showGameStore(user);
            return;
        }

        Card selectedCard = null;
        for (Card card : upgradableCards) {
            if (card.getName().equalsIgnoreCase(cardName)) {
                selectedCard = card;
                break;
            }
        }

        if (selectedCard == null) {
            System.out.println("Card not found. Please try again.");
            upgradeCards(user);
            return;
        }

        if (user.getGold() >= ((Upgradable) selectedCard).getUpgradeCost()) {
            ((Upgradable) selectedCard).upgrade(user);
            user.changeGold(-((Upgradable) selectedCard).getUpgradeCost());
            System.out.println("Congratulations! You've successfully upgraded the card: " + selectedCard.getName());
        } else {
            System.out.println("You don't have enough gold to upgrade this card.");
        }

        showGameStore(user);
    }
}
