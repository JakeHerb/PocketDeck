package com.jacobherbel.pocketdeck.cardStuff;

/**
 * Created by jacobherbel on 6/20/17.
 */
public class Card {

    private final int value;
    private final Suit suit;
    private final String fileName;

    public Card(int value, Suit suit, String fileName) {
        this.value = value;
        this.suit = suit;
        this.fileName = fileName;
    }

    // Returns the fileName of the image as a String
    public String fileName() {
        return fileName;
    }
}

