package model;

import java.util.ArrayList;

public abstract class Card {
    public static ArrayList<Card>cards = new ArrayList<>();
    private  User owner;
    private String name;
    protected int power;
    private int cost;

    public Card(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }
    public void setCost(int cost){
        this.cost = cost;
    }

    public abstract void play(User player, User opponent);
}

class NormalCard extends Card {
    private int attack;
    private int defense;
    private int duration;
    private int damagePlayer;
    private int levelUpgrade;
    private int costUpgrade;
    public void setAttack(int attack ){
        this.attack = attack;
    }
    public void setDefense(int defense ){
        this.defense = defense;
    }
    public void setDuration(int duration ){
        this.duration = duration;
    }
    public void setDamagePlayer(int damagePlayer ){
        this.damagePlayer = damagePlayer;
    }
    public void setLevelUpgrade(int levelUpgrade ){
        this.levelUpgrade = levelUpgrade;
    }
    public void setCostUpgrade(int costUpgrade ){
        this.costUpgrade = costUpgrade;
    }

    public NormalCard(String name, int cost, int attack, int defense, int duration, int damagePlayer, int levelUpgrade, int costUpgrade) {
        super(name, cost);
        this.attack = attack;
        this.defense = defense;
        this.duration = duration;
        this.damagePlayer = damagePlayer;
        this.levelUpgrade = levelUpgrade;
        this.costUpgrade = costUpgrade;
    }

    @Override
    public void play(User player, User opponent) {
        // Implement logic for playing a normal card
    }
}

class ShieldCard extends Card {
    public ShieldCard(String name, int cost) {
        super(name, cost);
    }

    @Override
    public void play(User player, User opponent) {
        // Implement logic for playing the shield card
    }
}

class HealCard extends Card {
    private int healAmount;

    public HealCard(String name, int cost, int healAmount) {
        super(name, cost);
        this.healAmount = healAmount;
    }

    @Override
    public void play(User player, User opponent) {
        player.setHP(player.getHP() + healAmount);
    }
}

class PowerBoostCard extends Card {
    public PowerBoostCard(String name, int cost) {
        super(name, cost);
    }

    @Override
    public void play(User player, User opponent) {
        // Implement logic for power boost card
    }
}

class ChangeHoleLocationCard extends Card {
    public ChangeHoleLocationCard(String name, int cost) {
        super(name, cost);
    }

    @Override
    public void play(User player, User opponent) {
        // Implement logic for changing hole location
    }
}

class RepairCard extends Card {
    public RepairCard(String name, int cost) {
        super(name, cost);
    }

    @Override
    public void play(User player, User opponent) {
        // Implement logic for repairing holes
    }
}

class ReduceRoundCard extends Card {
    public ReduceRoundCard(String name, int cost) {
        super(name, cost);
    }

    @Override
    public void play(User player, User opponent) {
        // Implement logic for reducing round
    }
}

class StealCard extends Card {
    public StealCard(String name, int cost) {
        super(name, cost);
    }

    @Override
    public void play(User player, User opponent) {
        // Implement logic for stealing card
    }
}

class WeakenOpponentCard extends Card {
    public WeakenOpponentCard(String name, int cost) {
        super(name, cost);
    }

    @Override
    public void play(User player, User opponent) {
        // Implement logic for weakening opponent
    }
}

class DuplicateCard extends Card {
    public DuplicateCard(String name, int cost) {
        super(name, cost);
    }

    @Override
    public void play(User player, User opponent) {
        // Implement logic for duplicating card
    }
}

class HideOpponentCardsCard extends Card {
    public HideOpponentCardsCard(String name, int cost) {
        super(name, cost);
    }

    @Override
    public void play(User player, User opponent) {
        // Implement logic for hiding opponent's cards
    }
}





