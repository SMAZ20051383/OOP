package model;

public abstract class Card {
    private final User owner;
    private final String name;
    protected int power;
    protected int price;

    public Card(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }



    @Override
    public String toString() {
        return name + ": " + owner.getUsername();
    }
}
