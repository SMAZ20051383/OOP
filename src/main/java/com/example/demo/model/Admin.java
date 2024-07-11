package com.example.demo.model;

import java.util.ArrayList;
import java.util.Scanner;

public class Admin {
    public static String password = "1234";

    public static void run() {
//            Card card1 = new NormalCard("Card1", 50, 20, 15, 3, 25, 5, 100);
//            Card card2 = new NormalCard("Card2", 60, 30, 20, 4, 30, 6, 120);
//            Card card3 = new NormalCard("Card3", 70, 40, 25, 2, 35, 7, 140);
//            Card card4 = new NormalCard("Card4", 80, 50, 30, 5, 40, 8, 160);
//            Card card5 = new NormalCard("Card5", 90, 60, 35, 1, 45, 9, 180);
//            Card card6 = new NormalCard("Card6", 55, 22, 18, 3, 26, 5, 105);
//            Card card7 = new NormalCard("Card7", 65, 32, 23, 4, 31, 6, 125);
//            Card card8 = new NormalCard("Card8", 75, 42, 28, 2, 36, 7, 145);
//            Card card9 = new NormalCard("Card9", 85, 52, 33, 5, 41, 8, 165);
//            Card card10 = new NormalCard("Card10", 95, 62, 38, 1, 46, 9, 185);
//            Card card11 = new NormalCard("Card11", 51, 19, 13, 3, 24, 5, 101);
//            Card card12 = new NormalCard("Card12", 61, 29, 18, 4, 29, 6, 121);
//            Card card13 = new NormalCard("Card13", 71, 39, 23, 2, 34, 7, 141);
//            Card card14 = new NormalCard("Card14", 81, 49, 28, 5, 39, 8, 161);
//            Card card15 = new NormalCard("Card15", 91, 59, 33, 1, 44, 9, 181);
//            Card card16 = new NormalCard("Card16", 53, 21, 16, 3, 26, 5, 102);
//            Card card17 = new NormalCard("Card17", 63, 31, 21, 4, 31, 6, 122);
//            Card card18 = new NormalCard("Card18", 73, 41, 26, 2, 36, 7, 142);
//            Card card19 = new NormalCard("Card19", 83, 51, 31, 5, 41, 8, 162);
//            Card card20 = new NormalCard("Card20", 93, 61, 36, 1, 46, 9, 182);
//            Card card21 = new NormalCard("Card21", 52, 20, 14, 3, 25, 5, 103);
//            Card card22 = new NormalCard("Card22", 62, 30, 19, 4, 30, 6, 123);
//            Card card23 = new NormalCard("Card23", 72, 40, 24, 2, 35, 7, 143);
//            Card card24 = new NormalCard("Card24", 82, 50, 29, 5, 40, 8, 163);
//            Card card25 = new NormalCard("Card25", 92, 60, 34, 1, 45, 9, 183);
//            Card card26 = new NormalCard("Card26", 54, 22, 17, 3, 27, 5, 104);
//            Card card27 = new NormalCard("Card27", 64, 32, 22, 4, 32, 6, 124);
//            Card card28 = new NormalCard("Card28", 74, 42, 27, 2, 37, 7, 144);
//            Card card29 = new NormalCard("Card29", 84, 52, 32, 5, 42, 8, 164);
//            Card card30 = new NormalCard("Card30", 94, 62, 37, 1, 47, 9, 184);
//            Card card31 = new NormalCard("Card31", 56, 23, 18, 3, 28, 5, 106);
//            Card card32 = new NormalCard("Card32", 66, 33, 23, 4, 33, 6, 126);
//            Card card33 = new NormalCard("Card33", 76, 43, 28, 2, 38, 7, 146);
//            Card card34 = new NormalCard("Card34", 86, 53, 33, 5, 43, 8, 166);
//            Card card35 = new NormalCard("Card35", 96, 63, 38, 1, 48, 9, 186);
//            Card card36 = new NormalCard("Card36", 57, 24, 19, 3, 29, 5, 107);
//            Card card37 = new NormalCard("Card37", 67, 34, 24, 4, 34, 6, 127);
//            Card card38 = new NormalCard("Card38", 77, 44, 29, 2, 39, 7, 147);
//            Card card39 = new NormalCard("Card39", 87, 54, 34, 5, 44, 8, 167);
//            Card card40 = new NormalCard("Card40", 97, 64, 39, 1, 49, 9, 187);
//
//            Card.cards.add(card1);
//            Card.cards.add(card2);
//            Card.cards.add(card3);
//            Card.cards.add(card4);
//            Card.cards.add(card5);
//            Card.cards.add(card6);
//            Card.cards.add(card7);
//            Card.cards.add(card8);
//            Card.cards.add(card9);
//            Card.cards.add(card10);
//            Card.cards.add(card11);
//            Card.cards.add(card12);
//            Card.cards.add(card13);
//            Card.cards.add(card14);
//            Card.cards.add(card15);
//            Card.cards.add(card16);
//            Card.cards.add(card17);
//            Card.cards.add(card18);
//            Card.cards.add(card19);
//            Card.cards.add(card20);
//            Card.cards.add(card21);
//            Card.cards.add(card22);
//            Card.cards.add(card23);
//            Card.cards.add(card24);
//            Card.cards.add(card25);
//            Card.cards.add(card26);
//            Card.cards.add(card27);
//            Card.cards.add(card28);
//            Card.cards.add(card29);
//            Card.cards.add(card30);
//            Card.cards.add(card31);
//            Card.cards.add(card32);
//            Card.cards.add(card33);
//            Card.cards.add(card34);
//            Card.cards.add(card35);
//            Card.cards.add(card36);
//            Card.cards.add(card37);
//            Card.cards.add(card38);
//            Card.cards.add(card39);
//            Card.cards.add(card40);
//
//            // Add the cards to the database
//            CardDatabase.addCard(card1);
//            CardDatabase.addCard(card2);
//            CardDatabase.addCard(card3);
//            CardDatabase.addCard(card4);
//            CardDatabase.addCard(card5);
//            CardDatabase.addCard(card6);
//            CardDatabase.addCard(card7);
//            CardDatabase.addCard(card8);
//            CardDatabase.addCard(card9);
//            CardDatabase.addCard(card10);
//            CardDatabase.addCard(card11);
//            CardDatabase.addCard(card12);
//            CardDatabase.addCard(card13);
//            CardDatabase.addCard(card14);
//            CardDatabase.addCard(card15);
//            CardDatabase.addCard(card16);
//            CardDatabase.addCard(card17);
//            CardDatabase.addCard(card18);
//            CardDatabase.addCard(card19);
//            CardDatabase.addCard(card20);
//            CardDatabase.addCard(card21);
//            CardDatabase.addCard(card22);
//            CardDatabase.addCard(card23);
//            CardDatabase.addCard(card24);
//            CardDatabase.addCard(card25);
//            CardDatabase.addCard(card26);
//            CardDatabase.addCard(card27);
//            CardDatabase.addCard(card28);
//            CardDatabase.addCard(card29);
//            CardDatabase.addCard(card30);
//            CardDatabase.addCard(card31);
//            CardDatabase.addCard(card32);
//            CardDatabase.addCard(card33);
//            CardDatabase.addCard(card34);
//            CardDatabase.addCard(card35);
//            CardDatabase.addCard(card36);
//            CardDatabase.addCard(card37);
//            CardDatabase.addCard(card38);
//            CardDatabase.addCard(card39);
//            CardDatabase.addCard(card40);

            System.out.println("40 cards added successfully.");

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
