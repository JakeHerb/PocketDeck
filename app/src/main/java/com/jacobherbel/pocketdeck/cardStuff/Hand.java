package com.jacobherbel.pocketdeck.cardStuff;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jacobherbel.pocketdeck.R;

import java.util.LinkedList;

/**
 * Created by jacobherbel on 7/11/17.
 */
public class Hand {

    Context mContext;
    private int mCardsInHand = 0;
    private LinkedList<CardView> mHand = new LinkedList<>();
    private RelativeLayout mHandLayout;
    public boolean isHidden = false;
    private final int mRoomForHand;
    private final int mCardWidth;
    private int mMinCardShowing;
    public int mMaxOverlap;
    public int mMostCardsVisible;

    public Hand(Context context) {
        mContext = context;
        mCardWidth = ContextCompat.getDrawable(mContext, R.drawable.back).getIntrinsicWidth();
        mRoomForHand = mContext.getResources().getDisplayMetrics().widthPixels - (mCardWidth / 2);
        mMinCardShowing = mCardWidth / 6;
        mMaxOverlap = mCardWidth - mMinCardShowing;
        mMostCardsVisible = (mRoomForHand - mCardWidth) / mMinCardShowing - 1;
        mHandLayout = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        rlp.setMargins(0, 30, 0, 0); // Left, Top, Right, Bottom
        mHandLayout.setLayoutParams(rlp);
    }

    // Add a card to the end of the linkedlist
    public void add(CardView card) {
        RelativeLayout.LayoutParams cardParams = (RelativeLayout.LayoutParams) card.getLayoutParams();
        mHand.add(card);
        testMoveCards();
        mHandLayout.addView(card);
        card.flipCard();
        mCardsInHand++;
    }

    // Temporary method by which to dynamically resize where the cards in the hand are placed.
    public void testMoveCards() {
        float prevCard = mHandLayout.getX();
        for (CardView card : mHand) {
            RelativeLayout.LayoutParams cardParams = (RelativeLayout.LayoutParams) card.getLayoutParams();
            if (card == mHand.peekFirst()) {
                card.setReturnPositionX(card.getX());
                prevCard = card.getX();
            }
            else {
                card.setX(prevCard + calcSpaceBetweenCards());
                card.setReturnPositionX(card.getX());
                prevCard = card.getX();
            }
        }
    }
    // Returns the first occurance of the given card from the hand while also removing it from the hand
    public CardView pullFromHand(CardView card) {
        mHand.removeFirstOccurrence(card);
        mCardsInHand--;
        return card;
    }

    // Pushes the hand layout below the screen, hiding it.
    public void hideBelowScreen() {
        mHandLayout.animate().translationYBy(500).setDuration(100);
        isHidden = true;
    }

    // Pulls the hand layout back to its original position
    public void bringBackToScreen() {
        mHandLayout.animate().translationYBy(-500).setDuration(100);
        isHidden = false;
    }

    // Returns the amount of space each card should show, depending on number of cards in the hand
    public int calcSpaceBetweenCards() {
        if (mCardsInHand >= mMostCardsVisible) {
            return mMinCardShowing;
        } else if (mCardsInHand == 0) {
            return 0;
        } else if ((mCardsInHand + 1) * mCardWidth <= mRoomForHand) {
            return mCardWidth;
        } else {
            return (mRoomForHand - mCardWidth) / mCardsInHand;
        }

    }

    // Returns the amount of screen space taken up by the cards in the hand
    public int spaceOccupied() {
        if (mCardsInHand >= mMostCardsVisible) {
            return mRoomForHand;
        } else if (mCardsInHand == 1) {
            return mCardWidth;
        } else {
            return calcSpaceBetweenCards() * (mCardsInHand - 1) + mCardWidth;
        }
    }

    // Returns the space still available for the hand
    public int spaceAvailable() {
        return mRoomForHand - spaceOccupied();
    }

    // Returns, but does not remove, the most recently added CardView
    public CardView peekLast() {
        return mHand.peekLast();
    }

    public RelativeLayout getmHandLayout() {
        return mHandLayout;
    }

    public int getmCardsInHand() {
        return mCardsInHand;
    }
}
