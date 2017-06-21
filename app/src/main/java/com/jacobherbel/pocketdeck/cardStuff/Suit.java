package com.jacobherbel.pocketdeck.cardStuff;

/**
 * Created by jacobherbel on 6/20/17.
 */
public class Suit {

    String suitName;
    boolean isBlack;
    boolean isRed;
    public Suit(String suitName, boolean isBlack) {
        this.suitName = suitName;
        this.isBlack = isBlack;
        this.isRed = !isBlack;
    }
}
