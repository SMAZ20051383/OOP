package util;

import model.CardDatabase;
import model.Card;
import model.NormalCard;

import java.util.ArrayList;

public class CardDatabasePrinter {
    public static void main(String[] args) {
        CardDatabase cardDatabase = new CardDatabase();
        ArrayList<Card> cards = cardDatabase.getAllCards();

        if (cards.isEmpty()) {
            System.out.println("No cards found in the database.");
        } else {
            for (Card card : cards) {
                printCardDetails(card);
            }
        }
    }

    private static void printCardDetails(Card card) {
        System.out.println("Name: " + card.getName());
        System.out.println("Cost: " + card.getCost());
        if (card instanceof NormalCard) {
            NormalCard normalCard = (NormalCard) card;
            System.out.println("Attack: " + normalCard.getAttack());
            System.out.println("Defense: " + normalCard.getDefense());
            System.out.println("Duration: " + normalCard.getDuration());
            System.out.println("Damage to Player: " + normalCard.getDamagePlayer());
            System.out.println("Level Upgrade: " + normalCard.getLevelUpgrade());
            System.out.println("Cost Upgrade: " + normalCard.getCostUpgrade());
        }
        // Add other card types if needed
        System.out.println("-----------------------------");
    }
}
