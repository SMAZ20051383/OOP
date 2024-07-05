package model;

import java.util.ArrayList;

public abstract class Card {
    public static ArrayList<Card>cards = new ArrayList<>();
    private  User owner;
    private String name;
    protected int power;
    protected int price;

    public Card(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }
    public Card getCard(String name){
        for(Card card:cards){
            if(card.getName().equals(name)){
                return card ;
            }
        }
        return null;
    }



    @Override
    public String toString() {
        return name + ": " + owner.getUsername();
    }
}
