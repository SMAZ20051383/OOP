package model;

import java.util.ArrayList;

public class User {
    private static final ArrayList<User> allUsers = new ArrayList<>();
    private final String username;
    private final ArrayList<Card> cards = new ArrayList<>();
    private final ArrayList<Card> battleDeck = new ArrayList<>();
    private String password;
    private int cardsToPlay = 1;
    private int movesLeft = 3;
    private int gold = 80;
    private int experience = 0;
    private int level = 1;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static ArrayList<User> getUsers() {
        return allUsers;
    }

    public static void addUser(User user) {
        allUsers.add(user);
    }

    public void decreaseCardsToPlay() {
        cardsToPlay--;
    }

    public void decreaseMovesLeft() {
        movesLeft--;
    }

    public String getUsername() {
        return username;
    }

    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Card> getBattleDeck() {
        return battleDeck;
    }

    public int getGold() {
        return gold;
    }

    public int getCardsToPlay() {
        return cardsToPlay;
    }

    public int getMovesLeft() {
        return movesLeft;
    }


    public boolean isPasswordWrong(String password) {
        return !this.password.equals(password);
    }

    public Card getCardByNameFromBattleDeck(String name) {
        for (Card card : battleDeck) {
            if (card.getName().equals(name)) return card;
        }
        return null;
    }

    public void removeFromDeck(Card card) {
        battleDeck.remove(card);
    }


    public void addToDeck(Card card) {
        int counter = 0;
        for (Card card1 : battleDeck) {
            if (card1.getName().compareTo(card.getName()) > 0) break;
            counter++;
        }
        battleDeck.add(counter, card);
    }

    public Card getCardByNameFromCards(String cardName) {
        for (Card card : cards) {
            if (card.getName().equals(cardName)) return card;
        }
        return null;
    }

    public String toString() {
        return "username: " + username +
                "\npassword: " + password +
                "\nlevel: " + level +
                "\nexperience: " + experience +
                "\ngold: " + gold;
    }

    public void changeGold(int amount) {
        gold += amount;
    }

    public void addToCard(String cardName) {
        switch (cardName) {
        }
    }

    public void removeFromCard(Card card) {
        cards.remove(card);
    }




    @Override
    public boolean equals(Object obj) {
        return this.username.equals(((User) obj).username);
    }

    public void addGold(int amount) {
        gold += amount;
    }

    public void reassignMovesAndCards() {
        cardsToPlay = 1;
        movesLeft = 3;
    }
}
