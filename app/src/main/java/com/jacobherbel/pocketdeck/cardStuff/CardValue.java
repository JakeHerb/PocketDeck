package com.jacobherbel.pocketdeck.cardStuff;

/**
 * Created by jacobherbel on 6/26/17.
 */
public enum CardValue {
    ACE(1, "ace"),
    TWO(2, "two"),
    THREE(3, "three"),
    FOUR(4, "four"),
    FIVE(5, "five"),
    SIX(6, "six"),
    SEVEN(7, "seven"),
    EIGHT(8, "eight"),
    NINE(9, "nine"),
    TEN(10, "ten"),
    JACK(11, "jack"),
    QUEEN(12, "queen"),
    KING(13, "king");

    private int cardValue;
    private String valueName;

    CardValue(int cardValue, String valueName) {
        this.cardValue = cardValue;
        this.valueName = valueName;
    }

    public int getCardValue() {
        return cardValue;
    }

    public String getValueName() {
        return valueName;
    }
}
