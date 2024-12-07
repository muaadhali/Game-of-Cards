package com.game;

import java.util.ArrayList;

public class Player {
    public ArrayList<Card> hand = new ArrayList<>();
    private int id;
    public int shields;
    public int draw;

    public Player(int id) {
        this.id = id;
        this.draw = 0;
    }

    public int getHandSize() {
        return hand.size();
    }

    public String printableHand() {
        String cards = "";
        for (int i = 0; i < getHandSize(); i++) {
            cards += (i+1) + ". " + hand.get(i) + " ";
        }
        return cards;
    }

    public int getId () {
        return id;
    }

    @Override
    public String toString() {
        return "PLAYER: " + id + "\tSHIELDS: " + shields + "\tHAND SIZE: " + getHandSize() + "\nHAND: " + printableHand();
    }
}
