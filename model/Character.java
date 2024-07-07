package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Character {
    private String name;
    public List<Card> cards;

    public Character(String name, List<Card> cards) {
        this.name = name;
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }

    // Method to randomly select a character for a player
    public static Character selectRandomCharacter() {
        // Assume characters and their default cards are predefined
        List<Character> characters = new ArrayList<>();
        characters.add(new Character("Character 1", generateDefaultCards()));
        characters.add(new Character("Character 2", generateDefaultCards()));
        characters.add(new Character("Character 3", generateDefaultCards()));
        characters.add(new Character("Character 4", generateDefaultCards()));

        Random random = new Random();
        int index = random.nextInt(characters.size());
        return characters.get(index);
    }

    // Method to generate default cards for a character
    private static List<Card> generateDefaultCards() {
        // Assume default cards are predefined or generated somehow
        List<Card> defaultCards = new ArrayList<>();
        defaultCards.add(new Card("Card 1", CardType.HEALING, 0, 0, 0));
        defaultCards.add(new Card("Card 2", CardType.DAMAGE, 10, 5, 2));
        defaultCards.add(new Card("Card 3", CardType.SPELL, 0, 0, 3));
        defaultCards.add(new Card("Card 4", CardType.ATTACK, 15, 0, 1));
        defaultCards.add(new Card("Card 5", CardType.DEFENSE, 0, 10, 0));
        return defaultCards;
    }
}
