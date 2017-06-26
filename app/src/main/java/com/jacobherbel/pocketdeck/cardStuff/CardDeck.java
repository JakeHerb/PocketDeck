package com.jacobherbel.pocketdeck.cardStuff;

import android.content.Context;

import com.jacobherbel.pocketdeck.cardStuff.Card;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by jacobherbel on 6/20/17.
 */
public class CardDeck {

    private LinkedList<Card> deck = new LinkedList<>();
    Context context;

    public CardDeck(Context context) {
        this.context = context;
        fillDeck();
    }

    // Initializes the deck to have all 52 cards in order
    public void fillDeck() {
        for (Suit suit : Suit.values()) {
            for (CardValue value : CardValue.values()) {
                try {
                    deck.add(new Card(context, value, suit));
                }
                catch (IOException e) {
                    throw new Error("Card Picture missing"); // Thrown if the picture isn't found
                }
            }
        }
    }

    // Returns the card at the top of the deck, while also removing it
    public Card next() {
        return deck.pop();
    }
}
