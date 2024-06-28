package controller;

import model.*;
import view.RegisterMenu;

import java.util.ArrayList;

public class Controller {
    private static int convertDirectionToInt(String direction) {
        int dir = -1;
        switch (direction) {
            case "left":
                dir = 0;
                break;
            case "middle":
                dir = 1;
                break;
            case "right":
                dir = 2;
        }
        return dir;
    }

    private static void addGold(User user1, User user2) {
        int numOfDefeatedCastles = 0;
        if (user2.getLeftCastle().getHitPoint() == -1) numOfDefeatedCastles++;
        if (user2.getMiddleCastle().getHitPoint() == -1) numOfDefeatedCastles++;
        if (user2.getRightCastle().getHitPoint() == -1) numOfDefeatedCastles++;
        user1.addGold(numOfDefeatedCastles * 20);
    }

    private static int makeWinner(User user1, User user2) {
        int winner = 0;
        if (user1.getCastleHitPoint("left") == -1
                && user1.getCastleHitPoint("middle") == -1
                && user1.getCastleHitPoint("right") == -1) {
            winner += 2;
        }
        if (user2.getCastleHitPoint("left") == -1
                && user2.getCastleHitPoint("middle") == -1
                && user2.getCastleHitPoint("right") == -1) {
            winner += 1;
        }
        return winner;
    }

    public void run() {
        new RegisterMenu().run(this);
    }

    public User getUserByUsername(String username) {
        for (User user : User.getUsers()) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public void register(String username, String pass) {
        User.addUser(new User(username, pass));
    }

    public boolean passwordIsWrong(String username, String password) {
        User user = getUserByUsername(username);
        return user.isPasswordWrong(password);
    }

    public boolean passwordFormatIsBad(String pass) {
        return pass.length() < 8
                || pass.length() > 20
                || !pass.matches(".*[a-z]+.*")
                || !pass.matches(".*[A-Z]+.*")
                || !pass.matches(".*\\d+.*")
                || !pass.matches("\\S+")
                || pass.matches("\\d.+")
                || !pass.matches(".*[!@#$%^&*]+.*");
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

    public int getSizeOfDeck(String username) {
        User user = getUserByUsername(username);
        return user.getBattleDeck().size();
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

    public boolean isGoldEnough(String username, String cardName) {
        User user = getUserByUsername(username);
        return user.getGold() >= getCardPrice(cardName);
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

    public String showCastleHitPoint(String username) {
        User user = getUserByUsername(username);
        return "middle castle: " + user.getCastleHitPoint("middle") + "\n" +
                "left castle: " + user.getCastleHitPoint("left") + "\n" +
                "right castle: " + user.getCastleHitPoint("right");
    }

    public void buildCastles(String username) {
        User user = getUserByUsername(username);
        user.BuildCastles();
    }

    public boolean isDirectionWrong(String name) {
        return !name.equals("middle")
                && !name.equals("left")
                && !name.equals("right");
    }

    public void buildPlayGround() {
        Cell.renewCells();
    }

    public String showLineInfo(String direction) {
        int dir = convertDirectionToInt(direction);
        StringBuilder output = new StringBuilder(direction + " line:\n");
        for (int i = 0; i < 15; i++) {
            for (Card card : Cell.cells[dir][i].getCardsInCell()) {
                output.append("row ").append(i + 1).append(": ").append(card).append("\n");
            }
        }
        return output.toString();
    }

    public int cardsToPlay(String username) {
        User user = getUserByUsername(username);
        return user.getCardsToPlay();
    }

    public int movesLeft(String username) {
        User user = getUserByUsername(username);
        return user.getMovesLeft();
    }

    public Troop getTroopFromCell(String username, String lineDirection, int row) {
        int dir = convertDirectionToInt(lineDirection);
        for (Card card : Cell.cells[dir][row - 1].getCardsInCell()) {
            if (card instanceof Troop && card.getOwner().getUsername().equals(username)) {
                return (Troop) card;
            }
        }
        return null;
    }

    public void moveTroop(String username, String lineDirection, int row, String direction) {
        int dir = convertDirectionToInt(lineDirection);
        int finalRow = direction.equals("upward") ? row + 1 : row - 1;
        Troop troop = getTroopFromCell(username, lineDirection, row);
        Cell.cells[dir][row - 1].removeTroop(troop);
        Cell.cells[dir][finalRow - 1].addCard(troop);
        getUserByUsername(username).decreaseMovesLeft();
    }

    public void deployCard(String username, String cardName, String lineDirection, int row) {
        int dir = convertDirectionToInt(lineDirection);
        User user = getUserByUsername(username);
        Card card;
        if (cardName.equals("Heal")) {
            card = new Spell("Heal", user);
        } else {
            card = new Troop(cardName, user);
        }
        Cell.cells[dir][row - 1].addCard(card);
        user.decreaseCardsToPlay();
    }

    public int getCastleHitPoint(String username, String lineDirection) {
        User user = getUserByUsername(username);
        return user.getCastleHitPoint(lineDirection);
    }

    public void doFireball(String myUsername, String opponentUsername, String lineDirection) {
        User oppUser = getUserByUsername(opponentUsername);
        oppUser.decreaseCastleHitPoint(lineDirection, 1400);
        User user = getUserByUsername(myUsername);
        user.decreaseCardsToPlay();
    }

    public String getWinner(String username1, String username2) {
        User user1 = getUserByUsername(username1);
        User user2 = getUserByUsername(username2);
        int winner = makeWinner(user1, user2);
        switch (winner) {
            case 2:
                return username2;
            case 1:
                return username1;
        }
        return null;
    }

    public void attack(String username1, String username2) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 15; j++) {
                Cell.cells[i][j].attackInCell();
            }
        }
        for (int i = 0; i < 3; i++) {
            Cell.cells[i][0].attackToCastle(getUserByUsername(username1), i);
            Cell.cells[i][14].attackToCastle(getUserByUsername(username2), i);
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 15; j++) {
                Cell.cells[i][j].heal();
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 15; j++) {
                Cell.cells[i][j].removeDeaths();
            }
        }
    }

    public String getWinner2(String username1, String username2) {
        User user1 = getUserByUsername(username1);
        User user2 = getUserByUsername(username2);
        int points1 = user1.getCastleHitPoint("left")
                + user1.getCastleHitPoint("middle")
                + user1.getCastleHitPoint("right");
        int points2 = user2.getCastleHitPoint("left")
                + user2.getCastleHitPoint("middle")
                + user2.getCastleHitPoint("right");
        if (points2 > points1) return username2;
        if (points1 > points2) return username1;
        return null;
    }

    public void makeLevelAndExperienceAndGold(String username1, String username2) {
        User user1 = getUserByUsername(username1);
        User user2 = getUserByUsername(username2);
        user1.makeExperince();
        user2.makeExperince();
        addGold(user1, user2);
        addGold(user2, user1);
    }

    public void reassignMovesAndCards(String username) {
        User user = getUserByUsername(username);
        user.reassignMovesAndCards();
    }
}
