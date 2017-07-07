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

    private LinkedList<Card> mDeck = new LinkedList<>(); // TODO change to a LinkedList of CardViews instead of Cards
    Context mContext;
    private int mCardsInDeck = 0;
    Random rn = new Random(System.currentTimeMillis());

    public CardDeck(Context context) {
        this.mContext = context;
    }

    // Initializes the deck to have all 52 cards in order
    public void fillDeck() {
        for (Suit suit : Suit.values()) {
            for (CardValue value : CardValue.values()) {
                try {
                    mDeck.add(new Card(mContext, value, suit));
                    ++mCardsInDeck;
                }
                catch (IOException e) {
                    throw new Error("Card Picture missing");
                }
            }
        }
    }

    // Returns the card at the top of the deck, while also removing it
    public Card next() {
        --mCardsInDeck;
        return mDeck.pollFirst();
    }

    public Card peek() {
        return mDeck.peek();
    }

    // Adds the card to the end of the list
    public void add(Card card) {
        mCardsInDeck++;
        mDeck.add(card);
    }

    // Finds the given index, and places all cards that come after it on the top of the deck
    public void cut(int cutIndex) {
        for (int i = 0; i < cutIndex; ++i) {
            mDeck.add(mDeck.poll());
        }
    }

    public int getCardsInDeck() {
        return mCardsInDeck;
    }

}
