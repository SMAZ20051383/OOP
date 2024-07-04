package controller;

import model.*;
import view.RegisterMenu;

import java.util.ArrayList;

public class Controller {



    public void run() {
        new RegisterMenu().run(this);
    }

    public User getUserByUsername(String username) {
        for (User user : User.getUsers()) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public void register(String username, String pass , String nikname , String email) {
        User.addUser(new User(username, pass , nikname , email));
    }

    public boolean passwordIsWrong(String username, String password) {
        User user = getUserByUsername(username);
        return user.isPasswordWrong(password);
    }

    public boolean passwordFormatIsBad(String pass) {
        return pass.length() < 8;
    }
    public boolean passwordFormatIsBad2(String password) {
        return  !password.matches(".*[a-z].*")
                || !password.matches(".*[A-Z].*")
                ||!password.matches(".*[^a-zA-Z0-9].*");
    }
    public boolean emailFormatIsBad2(String email) {
        return !email.matches("^[^@]+@[^@]+\\.com$");
    }

    public String listOfUsers() {
        StringBuilder output = new StringBuilder();
        int counter = 1;
        for (User user : User.getUsers()) {
            output.append("user ").append(counter).append(": ").append(user.getUsername()).append('\n');
            counter++;
        }
        return output.toString();
    }

    public String scoreboard() {
        ArrayList<User> scoreboard = makeScoreboard();
        StringBuilder output = new StringBuilder();
        int counter = 1;
        for (User user : scoreboard) {
            if (counter > 5) break;
            output.append(counter);
            output.append("- username: ").append(user.getUsername());
            output.append(" level: ").append(user.getLevel());
            output.append(" experience: ").append(user.getExperience());
            output.append('\n');
            counter++;
        }
        return output.toString();
    }

    private ArrayList<User> makeScoreboard() {
        ArrayList<User> scoreboard = new ArrayList<>(User.getUsers());
        for (int i = 0; i < User.getUsers().size(); i++) {
            for (int j = i + 1; j < User.getUsers().size(); j++) {
                User user1 = scoreboard.get(i), user2 = scoreboard.get(j);
                if (!compareUser(user1, user2)) {
                    scoreboard.set(i, user2);
                    scoreboard.set(j, user1);
                }
            }
        }
        return scoreboard;
    }

    private Boolean compareUser(User user1, User user2) {
        if (user1.getLevel() > user2.getLevel()) {
            return true;
        } else if (user1.getLevel() < user2.getLevel()) {
            return false;
        } else {
            if (user1.getExperience() > user2.getExperience()) {
                return true;
            } else if (user1.getExperience() < user2.getExperience()) {
                return false;
            } else {
                return user1.getUsername().compareTo(user2.getUsername()) <= 0;
            }
        }
    }

    public void changePassword(String username, String password) {
        User user = getUserByUsername(username);
        user.setPassword(password);
    }

    public int getRankByUsername(String username) {
        ArrayList<User> scoreboard = makeScoreboard();
        int counter = 1;
        for (User user : scoreboard) {
            if (user.getUsername().equals(username)) return counter;
            counter++;
        }
        return 0;
    }

    public String info(String username) {
        User user = getUserByUsername(username);
        return user + "\nrank: " + getRankByUsername(username);
    }

    public boolean isCardNameWrong(String name) {
        return !name.equals("Fireball")
                && !name.equals("Wizard")
                && !name.equals("Archer")
                && !name.equals("Heal")
                && !name.equals("Dragon");
    }

    public Card getCardFromDeck(String username, String cardName) {
        User user = getUserByUsername(username);
        return user.getCardByNameFromBattleDeck(cardName);
    }


    public void removeFromDeck(String username, String cardName) {
        Card card = getCardFromDeck(username, cardName);
        User user = getUserByUsername(username);
        user.removeFromDeck(card);
    }

    public void addToDeck(String username, String cardName) {
        Card card = getCardFromCards(username, cardName);
        User user = getUserByUsername(username);
        user.addToDeck(card);
    }

    public boolean userHasCard(String username, String cardName) {
        return getUserByUsername(username).getCardByNameFromCards(cardName) != null;
    }

    public String showDeck(String username) {
        User user = getUserByUsername(username);
        StringBuilder output = new StringBuilder();
        for (Card card : user.getBattleDeck()) {
            output.append(card.getName()).append("\n");
        }
        return output.toString();
    }


    public Card getCardFromCards(String username, String cardName) {
        User user = getUserByUsername(username);
        return user.getCardByNameFromCards(cardName);
    }

    public int getCardPrice(String cardName) {
        switch (cardName) {
            case "Fireball":
            case "Archer":
                return 80;
            case "Heal":
                return 150;
            case "Dragon":
                return 160;
            case "Wizard":
                return 140;
        }
        return 0;
    }

    public void buyCard(String username, String cardName) {
        User user = getUserByUsername(username);
        user.changeGold(-getCardPrice(cardName));
        user.addToCard(cardName);
    }

    public void sellCard(String username, String cardName) {
        User user = getUserByUsername(username);
        user.changeGold((int) Math.floor(getCardPrice(cardName) * 0.8));
        Card card = getCardFromCards(username, cardName);
        user.removeFromCard(card);
    }


    public boolean isDirectionWrong(String name) {
        return !name.equals("middle")
                && !name.equals("left")
                && !name.equals("right");
    }



    public int movesLeft(String username) {
        User user = getUserByUsername(username);
        return user.getMovesLeft();
    }


    public void getWinner(String username1, String username2) {
    }

}
