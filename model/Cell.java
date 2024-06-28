package model;

import java.util.ArrayList;

public class Cell {
    public static Cell[][] cells = new Cell[3][15];

    private final ArrayList<Card> cardsInCell = new ArrayList<>();

    public static void renewCells() {
        cells = new Cell[3][15];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 15; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public ArrayList<Card> getCardsInCell() {
        return cardsInCell;
    }

    public void removeTroop(Troop troop) {
        cardsInCell.remove(troop);
    }

    public void addCard(Card card) {
        cardsInCell.add(card);
    }

    public void attackInCell() {
        if (cardsInCell.size() < 2) return;
        for (Card card1 : cardsInCell) {
            for (Card card2 : cardsInCell) {
                if (card2 instanceof Spell || card1 instanceof Spell) continue;
                if (card2.getOwner().equals(card1.getOwner())) continue;
                if (card1.getPower() > card2.getPower()) continue;
                Troop troop1 = (Troop) card1;
                troop1.decreaseHitPoint(card2.getPower() - troop1.getPower());
            }
        }
    }

    public void heal() {
        for (Card card1 : cardsInCell) {
            if (card1 instanceof Troop) continue;
            ((Spell) card1).decreaseTurnsLeft();
            for (Card card2 : cardsInCell) {
                if (card2 instanceof Spell) continue;
                if (!card2.getOwner().equals(card1.getOwner())) continue;
                Troop troop2 = (Troop) card2;
                troop2.heal();
            }
        }
    }

    public void attackToCastle(User user, int dir) {
        for (Card card : cardsInCell) {
            if (card instanceof Spell) continue;
            if (user.equals(card.getOwner())) continue;
            Castle castle = null;
            switch (dir) {
                case 0:
                    castle = user.getLeftCastle();
                    break;
                case 1:
                    castle = user.getMiddleCastle();
                    break;
                case 2:
                    castle = user.getRightCastle();
                    break;
            }
            castle.decreaseHitPoint(card.getPower());
            ((Troop) card).decreaseHitPoint(castle.getPower());
        }

    }

    public void removeDeaths() {
        for (int i = 0; i < cardsInCell.size(); i++) {
            Card card = cardsInCell.get(i);
            if (card instanceof Spell && ((Spell) card).getTurnsLeft() == 0
                    || card instanceof Troop && ((Troop) card).getHitPoint() <= 0) {
                cardsInCell.remove(card);
                i--;
            }
        }
    }
}
