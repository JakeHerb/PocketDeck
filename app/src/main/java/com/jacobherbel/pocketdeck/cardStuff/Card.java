package com.jacobherbel.pocketdeck.cardStuff;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.IOException;

/**
 * Created by jacobherbel on 6/20/17.
 */
public class Card {

    private final Suit mSuit;
    private final CardValue mCardValue;
    private final int mFrontImageID;
    private final int mBackImageID;

    public Card(Context context, CardValue cardValue, Suit suit) throws IOException {
        mCardValue = cardValue;
        mSuit = suit;
        String name = "" + getSuit().getSuitName() + "_" + getCardValue().getValueName();
        mFrontImageID = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        mBackImageID = context.getResources().getIdentifier("back", "drawable", context.getPackageName());
    }

    public Suit getSuit() {
        return mSuit;
    }

    public CardValue getCardValue() {
        return mCardValue;
    }

    public int getFrontImage() {
        return mFrontImageID;
    }

    public int getBackImage() { return mBackImageID; }


}

