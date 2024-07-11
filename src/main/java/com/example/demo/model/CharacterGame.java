package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.demo.view.MainMenu.selectRandomNumbers;

public class CharacterGame {
    private String name;
    public ArrayList<Card> cards = new ArrayList<>();

    public CharacterGame(String name) {
        this.name = name;
        if (this.cards.size() == 0) {
            int number = CardDatabase.getNumberOfCards() - 1;
            List<Integer> random_numbers = selectRandomNumbers(number);
            for (int i = 0; i < 10; i++) {
                this.cards.add(CardDatabase.getCardByIndex(random_numbers.get(i)));
            }
        }
    }
    public String getName() {
        return name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    // Method to randomly select a CharacterGame for a player
    public static CharacterGame selectRandomCharacterGame(ArrayList<CharacterGame>charactors) {
        Random random = new Random();
        int index = random.nextInt(charactors.size());
        return charactors.get(index);
    }
    public boolean haveThisCard(Card card){
        for(Card card1 : cards){
            if(card1.equals(card)){
                return true ;
            }
        }
        return false ;
    }

}
