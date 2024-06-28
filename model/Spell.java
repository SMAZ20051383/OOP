package model;

public class Spell extends Card {
    private int turnsLeft;

    public Spell(String name, User owner) {
        super(name, owner);
        switch (name) {
            case "Fireball":
                power = 1400;
                price = 80;
                break;
            case "Heal":
                power = 1000;
                price = 150;
                turnsLeft = 2;
                break;
        }
    }

    public int getTurnsLeft() {
        return turnsLeft;
    }

    public void decreaseTurnsLeft() {
        turnsLeft--;
    }
}
