package model;

import java.util.ArrayList;
import java.util.Map;

public class User {
    public static ArrayList<User> allUsers ;
    private final String username;
    private final ArrayList<Card> cards = new ArrayList<>();
    private  ArrayList<Card> battleDeck;
    private String password;
    private String Nikname;
    private String Email;
    private int cardsToPlay = 1;
    private int movesLeft = 3;
    private int gold;
    private int experience;
    private int level;
    private int HP;
    private Map<String, String> securityQuestion;

    public User(String username, String password, String nikname, String email, Map<String, String> securityQuestion, ArrayList<Card> battleDeck, int level, int HP, int experience, int gold) {
        this.username = username;
        this.password = password;
        this.Nikname = nikname;
        this.Email = email;
        this.securityQuestion = securityQuestion;
        this.battleDeck = battleDeck;
        this.level = level;
        this.HP = HP;
        this.experience = experience;
        this.gold = gold;

        allUsers.add(this);
    }
    public User(String username , String password , String nikname , String email){
        this.username = username ;
        this.password = password ;
        this.Nikname = nikname ;
        this.Email = email ;
    }

    public static ArrayList<User> getUsers() {
        return allUsers;
    }

    public static void addUser(User user) {
        allUsers.add(user);
    }
    public void setHP(int HP){
        this.HP=HP;
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
    public int getHP() {return  HP;}

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
