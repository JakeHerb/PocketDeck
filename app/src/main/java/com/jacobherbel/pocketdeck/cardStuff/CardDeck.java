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
    private int cardsInDeck = 0;

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
                    ++cardsInDeck;
                }
                catch (IOException e) {
                    throw new Error("Card Picture missing");
                }
            }
        }
    }

    // Returns the card at the top of the deck, while also removing it
    public Card next() {
        --cardsInDeck;
        return deck.pop();
    }

    // Finds the given index, and places all cards that come after it on the top of the deck
    public void cut(int cutIndex) {
        for (int i = 0; i < cutIndex; ++i) {
            deck.add(deck.poll());
        }
    }

    public int getCardsInDeck() {
        return cardsInDeck;
    }
}
