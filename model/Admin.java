package model;

import java.util.ArrayList;
import java.util.Scanner;

public class Admin {
    public static String password = "1234";

    public static void run() {
        showMenu();
    }

    private static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("--------------------------------------");
            System.out.println("Admin menu");
            System.out.println("1. Add Card");
            System.out.println("2. Edit Card");
            System.out.println("3. Delete Card");
            System.out.println("4. View All Players");
            System.out.println("5. Exit");
            System.out.println("6. show all cards name");
            System.out.println("--------------------------------------");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addCard(scanner);
                    break;
                case 2:
                    editCard(scanner);
                    break;
                case 3:
                    deleteCard(scanner);
                    break;
                case 4:
                    viewAllPlayers();
                    break;
                case 5:
                    isRunning = false;
                    break;
                case 6:
                    print_all_cards();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addCard(Scanner scanner) {
        System.out.print("Enter card name: ");
        String name = scanner.nextLine();
        System.out.print("Enter card cost: ");
        int cost = Integer.parseInt(scanner.nextLine());
        System.out.print("Is it a normal card? (yes/no): ");
        String isNormal = scanner.nextLine();

        if (isNormal.equalsIgnoreCase("yes")) {
            System.out.print("Enter attack: ");
            int attack = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter defense: ");
            int defense = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter duration: ");
            int duration = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter damage to player: ");
            int damagePlayer = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter level upgrade: ");
            int levelUpgrade = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter cost upgrade: ");
            int costUpgrade = Integer.parseInt(scanner.nextLine());

            Card card = new NormalCard(name, cost, attack, defense, duration, damagePlayer, levelUpgrade, costUpgrade);
            Card.cards.add(card);
            CardDatabase.addCard(card); // ذخیره کارت در دیتابیس
        } else {
            System.out.println("Enter the type of special card:");
            System.out.println("1. Shield");
            System.out.println("2. Heal");
            System.out.println("3. Power Boost");
            System.out.println("4. Change Hole Location");
            System.out.println("5. Repair");
            System.out.println("6. Reduce Round");
            System.out.println("7. Steal");
            System.out.println("8. Weaken Opponent");
            System.out.println("9. Duplicate");
            System.out.println("10. Hide Opponent Cards");
            int type = Integer.parseInt(scanner.nextLine());

            Card card = null;
            switch (type) {
                case 1:
                    card = new ShieldCard(name, cost);
                    break;
                case 2:
                    System.out.print("Enter heal amount: ");
                    int healAmount = Integer.parseInt(scanner.nextLine());
                    card = new HealCard(name, cost, healAmount);
                    break;
                case 3:
                    card = new PowerBoostCard(name, cost);
                    break;
                case 4:
                    card = new ChangeHoleLocationCard(name, cost);
                    break;
                case 5:
                    card = new RepairCard(name, cost);
                    break;
                case 6:
                    card = new ReduceRoundCard(name, cost);
                    break;
                case 7:
                    card = new StealCard(name, cost);
                    break;
                case 8:
                    card = new WeakenOpponentCard(name, cost);
                    break;
                case 9:
                    card = new DuplicateCard(name, cost);
                    break;
                case 10:
                    card = new HideOpponentCardsCard(name, cost);
                    break;
                default:
                    System.out.println("Invalid type.");
            }

            if (card != null) {
                Card.cards.add(card);
            }
        }

        System.out.println("Card added successfully.");
    }

    private static void editCard(Scanner scanner) {
        System.out.print("Enter card name to edit: ");
        String name = scanner.nextLine();
        Card card = getCardByName(name);

        if (card == null) {
            System.out.println("Card not found.");
            return;
        }

        System.out.println("Editing card: " + card.getName());
        System.out.print("Enter new cost: ");
        int cost = Integer.parseInt(scanner.nextLine());
        card.setCost(cost);

        if (card instanceof NormalCard) {
            NormalCard normalCard = (NormalCard) card;
            System.out.print("Enter new attack: ");
            int attack = Integer.parseInt(scanner.nextLine());
            normalCard.setAttack(attack);
            System.out.print("Enter new defense: ");
            int defense = Integer.parseInt(scanner.nextLine());
            normalCard.setDefense(defense);
            System.out.print("Enter new duration: ");
            int duration = Integer.parseInt(scanner.nextLine());
            normalCard.setDuration(duration);
            System.out.print("Enter new damage to player: ");
            int damagePlayer = Integer.parseInt(scanner.nextLine());
            normalCard.setDamagePlayer(damagePlayer);
            System.out.print("Enter new level upgrade: ");
            int levelUpgrade = Integer.parseInt(scanner.nextLine());
            normalCard.setLevelUpgrade(levelUpgrade);
            System.out.print("Enter new cost upgrade: ");
            int costUpgrade = Integer.parseInt(scanner.nextLine());
            normalCard.setCostUpgrade(costUpgrade);
        }

        CardDatabase.updateCard(card); // به‌روزرسانی کارت در دیتابیس
        System.out.println("Card edited successfully.");
    }

    private static void deleteCard(Scanner scanner) {
        System.out.print("Enter card name to delete: ");
        String name = scanner.nextLine();
        Card card = getCardByName(name);

        if (card == null) {
            System.out.println("Card not found.");
            return;
        }

        Card.cards.remove(card);
        CardDatabase.deleteCard(name); // حذف کارت از دیتابیس
        System.out.println("Card deleted successfully.");
    }

    private static void viewAllPlayers() {
        for (User user : User.getAllUsers()) {
            System.out.println(user);
            System.out.println("-----------------");
        }
    }

    private static Card getCardByName(String name) {
        for (Card card : Card.cards) {
            if (card.getName().equals(name)) {
                return card;
            }
        }
        return null;
    }
    private static void print_all_cards(){
        for(Card card : Card.cards){
            System.out.println(card.getName());
        }
    }
}
