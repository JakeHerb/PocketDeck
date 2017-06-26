package com.jacobherbel.pocketdeck.cardStuff;

/**
 * Created by jacobherbel on 6/26/17.
 */
public enum Suit {
    SPADES("spades"),
    DIAMONDS("diamonds"),
    CLUBS("clubs"),
    HEARTS("hearts");

    private String suitName;

    Suit(String suitName) {
        this.suitName = suitName;
    }

    public String getSuitName() {
        return suitName;
    }
}
