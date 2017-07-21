package com.jacobherbel.pocketdeck.cardStuff;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jacobherbel on 6/20/17.
 */
public class CardDeck {

    private LinkedList<CardView> mDeck = new LinkedList<>();
    Context mContext;
    private ViewGroup mParent;
    private int mCardsInDeck = 0;
    private float xLocation;
    private float yLocation;
    Random rn = new Random(System.currentTimeMillis());

    public CardDeck(Context context, ViewGroup parent) {
        this.mContext = context;
        mParent = parent;
        fillDeck();
    }

    public void place(float x, float y) {
        xLocation = x;
        yLocation = y;
        for (int i = mCardsInDeck - 4; i < mCardsInDeck; i++) {
            mParent.addView(mDeck.get(i));
        }
        arrange();
    }

    public void arrange() {
        int movementAmount = 10;
        if (mCardsInDeck >= 4) {
            CardView card = (CardView) mDeck.get(mCardsInDeck - 4);
            card.setX(xLocation + (movementAmount * 3));
            card.setY(yLocation - (movementAmount * 3));
            card.setReturnPositionX(card.getX());
            card.setReturnPositionY(card.getY());
        }
        if (mCardsInDeck >= 3) {
            CardView card = (CardView) mDeck.get(mCardsInDeck - 3);
            card.setX(xLocation + (movementAmount * 2));
            card.setY(yLocation - (movementAmount * 2));
            card.setReturnPositionX(card.getX());
            card.setReturnPositionY(card.getY());
        }
        if (mCardsInDeck >= 2) {
            CardView card = (CardView) mDeck.get(mCardsInDeck - 2);
            card.setX(xLocation + (movementAmount));
            card.setY(yLocation - (movementAmount));
            card.setReturnPositionX(card.getX());
            card.setReturnPositionY(card.getY());
        }
        if (mCardsInDeck >= 1) {
            CardView card = (CardView) mDeck.get(mCardsInDeck - 1);
            card.setX(xLocation);
            card.setY(yLocation);
            card.setReturnPositionX(card.getX());
            card.setReturnPositionY(card.getY());
        }
    }

    // Initializes the deck to have all 52 cards in order
    public void fillDeck() {
        if (mCardsInDeck != 0) {
            mDeck.clear();
        }
        for (Suit suit : Suit.values()) {
            for (CardValue value : CardValue.values()) {
                try {
                    CardView tempCV = new CardView(mContext, new Card(mContext, value, suit));
                    tempCV.setLayoutParams(new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT));
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        tempCV.setId(generateViewId());
                    } else {
                        tempCV.setId(View.generateViewId());
                    }
                    mDeck.addFirst(tempCV);
                    ++mCardsInDeck;
                }
                catch (IOException e) {
                    throw new Error("Card Picture missing");
                }
            }
        }
    }

    // Grabs random cards from the unshuffled portion of the deck and places them at the end until all cards are shuffled
    public void shuffle() {
        for (int i = mCardsInDeck; i > 0; i--) {
            int tempIndex = rn.nextInt(i);
            mDeck.add(mDeck.remove(tempIndex));
        }
    }

    // Returns the least recently added CardView, while also removing it
    public CardView grabBottomCard() {
        --mCardsInDeck;
        mParent.removeView(mDeck.peekFirst());
        return mDeck.pollFirst();
    }

    // Returns the most recently added CardView, while also removing it
    public CardView grabTopCard() {
        --mCardsInDeck;
        mParent.removeView(mDeck.peekLast());
        return mDeck.pollLast();
    }

    // Returns, but does not remove, the least recently added CardView
    public CardView peek() {
        return mDeck.peekFirst();
    }

    // Returns, but does not remove, the most recently added CardView
    public CardView peekLast() {
        return mDeck.peekLast();
    }

    // Adds the card to the end of the list
    public void add(CardView newCardView) {
        mCardsInDeck++;
        mDeck.add(newCardView);
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


    // Taken from a user online as a way to generate ID's for API's less than version 17.
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * Generate a value suitable for use in generateViewID
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

}
