package model;

import java.util.ArrayList;

public abstract class Card {
    public static ArrayList<Card> cards = new ArrayList<>();
    private User owner;
    private String name;
    protected int power;
    private int cost;

    public Card(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }
    public static ArrayList<Card> getAvailableCards(User user) {
        ArrayList<Card> availableCards = new ArrayList<>();
        for (Card card : cards) {
            if (!user.getCards().contains(card)) {
                availableCards.add(card);
            }
        }
        return availableCards;
    }
    public static ArrayList<Card> getUpgradableCards(User user) {
        ArrayList<Card> upgradableCards = new ArrayList<>();
        for (Card card : user.getCards()) {
            if (card instanceof Upgradable) {
                upgradableCards.add(card);
            }
        }
        return upgradableCards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public abstract void play(User player, User opponent);
}
interface Upgradable {
    int getUpgradeCost();

    void upgrade(User user);
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

    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
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
