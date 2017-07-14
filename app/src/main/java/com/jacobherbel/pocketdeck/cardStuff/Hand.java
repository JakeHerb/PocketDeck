package com.jacobherbel.pocketdeck.cardStuff;

import android.content.Context;
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

    public Hand(Context context) {
        mContext = context;
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
        if (mCardsInHand > 0) {
            cardParams.addRule(RelativeLayout.RIGHT_OF, mHand.peekLast().getID());
        }
        mHand.add(card);
        mHandLayout.addView(card);
        card.flipCard();
        mCardsInHand++;
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

    // Returns, but does not remove, the most recently added CardView
    public CardView peekLast() {
        return mHand.peekLast();
    }

    public RelativeLayout getmHandLayout() {
        return mHandLayout;
    }
}
