package model;

public class Troop extends Card {
    private final int maxHitPoint;
    private int hitPoint;

    public Troop(String name, User owner) {
        super(name, owner);
        switch (name) {
            case "Archer":
                power = 800;
                price = 80;
                hitPoint = 1900;
                break;
            case "Dragon":
                power = 1100;
                price = 160;
                hitPoint = 3200;
                break;
            case "Wizard":
                power = 1400;
                price = 140;
                hitPoint = 3200;
                break;
        }
        maxHitPoint = hitPoint;
    }

    public void decreaseHitPoint(int amount) {
        hitPoint -= amount;
    }

    public void heal() {
        hitPoint += 1000;
        if (hitPoint > maxHitPoint) hitPoint = maxHitPoint;
    }

    public int getHitPoint() {
        return hitPoint;
    }
}
