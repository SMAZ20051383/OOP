package model;

public class Castle {
    private int hitPoint;
    private int power;

    public Castle(int zarib, int level) {
        this.hitPoint = zarib * level;
        this.power = 500 * level;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getPower() {
        return power;
    }

    public void decreaseHitPoint(int amount) {
        hitPoint -= amount;
        if (hitPoint <= 0) {
            hitPoint = -1;
            power = 0;
        }
    }

}
