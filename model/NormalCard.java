package model;

public class NormalCard extends Card {
    private int attack;
    private int defense;
    private int duration;
    private int damagePlayer;
    private int levelUpgrade;
    private int costUpgrade;

    public NormalCard(String name, int cost, int attack, int defense, int duration, int damagePlayer, int levelUpgrade, int costUpgrade) {
        super(name, cost);
        this.attack = attack;
        this.defense = defense;
        this.duration = duration;
        this.damagePlayer = damagePlayer;
        this.levelUpgrade = levelUpgrade;
        this.costUpgrade = costUpgrade;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDamagePlayer() {
        return damagePlayer;
    }

    public void setDamagePlayer(int damagePlayer) {
        this.damagePlayer = damagePlayer;
    }

    public int getLevelUpgrade() {
        return levelUpgrade;
    }

    public void setLevelUpgrade(int levelUpgrade) {
        this.levelUpgrade = levelUpgrade;
    }

    public int getCostUpgrade() {
        return costUpgrade;
    }

    public void setCostUpgrade(int costUpgrade) {
        this.costUpgrade = costUpgrade;
    }

    @Override
    public void play(User player, User opponent) {
        // Implement logic for playing a normal card
    }
}
