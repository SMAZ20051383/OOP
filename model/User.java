package model;

import controller.SqlController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    public static ArrayList<User> allUsers = new ArrayList<>() ;
    private  String username;
    private final ArrayList<Card> cards = new ArrayList<>();
    private  ArrayList<Card> battleDeck;
    private String password;
    private String Nickname;
    private String Email;
    private int cardsToPlay = 1;
    private int movesLeft = 3;
    private int gold = 50;
    private int experience;
    private int level=1;
    private int HP = 100;
    private String[] securityQuestion = new String[2] ;
    public User(String username, String password, String nikname, String email, String[] securityQuestion, ArrayList<Card> battleDeck, int level, int HP, int experience, int gold) {
        this.username = username;
        this.password = password;
        this.Nickname = nikname;
        this.Email = email;
        this.securityQuestion[0]=securityQuestion[0];
        this.securityQuestion[1]=securityQuestion[1];
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
        this.Nickname = nikname ;
        this.Email = email ;
        SqlController.insertUser(this);
        SqlController.updateUser(this);
    }

    public static ArrayList<User> getUsers() {
        return allUsers;
    }

    public static void addUser(User user) {
        allUsers.add(user);
    }
    public void setHP(int HP){
        this.HP=HP;
        SqlController.updateUser(this);
    }

    public void decreaseCardsToPlay() {
        cardsToPlay--;
        SqlController.updateUser(this);
    }

    public void decreaseMovesLeft() {
        movesLeft--;
        SqlController.updateUser(this);
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
        SqlController.updateUser(this);
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
        SqlController.updateUser(this);
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
                "\ncoin: " + gold+
                "\nemail: " + Email+
                "\nnickname: "+Nickname+
                "\nHP: "+HP+
                "\nXP: "+experience;
    }

    public void changeGold(int amount) {
        gold += amount;
        SqlController.updateUser(this);
    }

    public void addToCard(String cardName) {
        switch (cardName) {
        }
    }

    public void removeFromCard(Card card) {
        cards.remove(card);
        SqlController.updateUser(this);
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public String getEmail() {
        return Email;
    }

    public String getNickname() {
        return Nickname;
    }

    public String[] getSecurityQuestion() {
        return securityQuestion;
    }

    @Override
    public boolean equals(Object obj) {
        return this.username.equals(((User) obj).username);
    }

    public void addGold(int amount) {
        gold += amount;
        SqlController.updateUser(this);
    }

    public void reassignMovesAndCards() {
        cardsToPlay = 1;
        movesLeft = 3;
    }

    public void setSecurityQuestion(String[] securityQuestion) {
        this.securityQuestion = securityQuestion;
        SqlController.updateUser(this);
    }

    public void setUsername(String username) {
        int Id = SqlController.getUserID(this.username);
        this.username = username;
        SqlController.updateUsername(Id,this.username);
    }

    public void setNickname(String nickname) {
        this.Nickname = nickname;
        SqlController.updateUser(this);
    }

    public void setEmail(String email) {
        Email = email;
        SqlController.updateUser(this);
    }
}
