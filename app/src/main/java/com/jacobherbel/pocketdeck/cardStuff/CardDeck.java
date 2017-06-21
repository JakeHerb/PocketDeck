package com.jacobherbel.pocketdeck.cardStuff;

import com.jacobherbel.pocketdeck.cardStuff.Card;

/**
 * Created by jacobherbel on 6/20/17.
 */
public class CardDeck {

    private Card[] deck = new Card[52];
    public CardDeck() {
        fillDeck(deck);
    }

    // Returns the card at the specified location
    public Card cardAt(int index) {
        return deck[index];
    }

    // Populates the array with cards in order, unshuffled
    private void fillDeck(Card[] emptyDeck) {
        Suit spades = new Suit("spades", true);  // Filled with spades first in 0 - 12
        for (int i = 1; i < 14; ++i) {
            if (i < 10) {
                emptyDeck[i - 1] = new Card(i, spades, "s0" + i);
            }
            else {
                emptyDeck[i - 1] = new Card(i, spades, "s" + i );
            }
        }
        Suit diamonds = new Suit("diamonds", false); // Filled with diamonds second in 13 - 25
        for (int i = 1; i < 14; ++i) {
            if (i < 10) {
                emptyDeck[i + 12] = new Card(i, diamonds, "d0" + i);
            }
            else {
                emptyDeck[i + 12] = new Card(i, diamonds, "d" + i );
            }
        }
        Suit clubs = new Suit("clubs", true); // Filled with clubs third in 26 - 38
        for (int i = 1; i < 14; ++i) {
            if (i < 10) {
                emptyDeck[i + 25] = new Card(i, clubs, "d0" + i);
            }
            else {
                emptyDeck[i + 25] = new Card(i, clubs, "d" + i );
            }
        }
        Suit hearts = new Suit("hearts", false); // Filled with hearts last in 39 - 51
        for (int i = 1; i < 14; ++i) {
            if (i < 10) {
                emptyDeck[i + 38] = new Card(i, clubs, "d0" + i);
            }
            else {
                emptyDeck[i + 38] = new Card(i, clubs, "d" + i );
            }
        }
    }
}
