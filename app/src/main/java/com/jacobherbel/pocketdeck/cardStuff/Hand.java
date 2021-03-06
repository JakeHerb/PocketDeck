package com.jacobherbel.pocketdeck.cardStuff;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.RelativeLayout;
import com.jacobherbel.pocketdeck.R;
import com.jacobherbel.pocketdeck.customListeners.CardInHandListener;
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
    private static final float SCALE_FACTOR = 1f;
    private int mMinCardShowing;
    private int mMaxOverlap;
    private int mMostCardsVisible;

    public Hand(Context context) {
        mContext = context;
        mHandLayout = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rlp.bottomMargin = mContext.getResources().getDisplayMetrics().heightPixels / 12;
        mHandLayout.setLayoutParams(rlp);
        mCardWidth = Math.round(ContextCompat.getDrawable(mContext, R.drawable.back).getIntrinsicWidth() * SCALE_FACTOR);
        mRoomForHand = mContext.getResources().getDisplayMetrics().widthPixels - Math.round(mCardWidth + mCardWidth / 2);
        mMinCardShowing = mCardWidth / 6;
        mMaxOverlap = mCardWidth - mMinCardShowing;
        mMostCardsVisible = (mRoomForHand) / mMinCardShowing;
    }

    // Add a card to the end of the hand without any specific information or animation
    public void add(CardView card) {
        rescale(SCALE_FACTOR, card);
        mHand.add(setIndex(card), card);
        mHandLayout.addView(card);
        card.setOnTouchListener(new CardInHandListener((RelativeLayout) mHandLayout.getParent(), this));
        mCardsInHand++;
        arrangeCards();
        // For come reason, if this animate isn't here, the card is placed in a weird location
        // TODO figure out why
        card.animate().translationY(card.getReturnPositionY() + setVerticalDisplacement(card))
                .translationX(card.getReturnPositionX())
                .setDuration(0);
        if (!card.isRevealed()) {
            card.flipCard();
        }
        if (isHidden) {
            isHidden = false;
        }
    }

    public void rescale(float scaleFactor, CardView card) {
        card.setScaleX(scaleFactor);
        card.setScaleY(scaleFactor);
    }

    // Returns the index at which a card should be placed, based on its x-position.
    public int setIndex(CardView newCard) {
        if (isHidden) {
            return mCardsInHand;
        }
        int index = 0;
        for (CardView card : mHand) {
            if (newCard.getX() < card.getX()) {
                return index;
            }
            index++;
        }
        return mCardsInHand;
    }

    // Dynamically resizes where the cards in the hand are placed.
    public void arrangeCards() {
        float prevCard = 0f;
        for (CardView card : mHand) {
            if (card == mHand.peekFirst()) {
                card.setX((spaceAvailable() + mCardWidth) / 2);
                card.setReturnPositionY(0f);
            } else {
                card.setX(prevCard + calcSpaceBetweenCards());
                card.setReturnPositionY(mHand.peekFirst().getReturnPositionY());
            }
            card.setReturnPositionX(card.getX());
            prevCard = card.getX();
        }
        angleCards();
        for (CardView card : mHand) {
            card.setY(setVerticalDisplacement(card) + card.getReturnPositionY());
            card.bringToFront();
        }
    }

    // Tilts the cards with increasingly steep angles, the further away from the center they are
    public void angleCards() {
        // TODO set a max angle for the cards so we don't end up with the snake pattern
        int halfHand = mCardsInHand / 2;
        for (int i = 0, degreeTotal = halfHand * 3; i < halfHand; i++, degreeTotal -= 3) {
            mHand.get(i).setRotation(-degreeTotal);
            mHand.get(mCardsInHand - (1 + i)).setRotation(degreeTotal);
        }
        if (mCardsInHand % 2 != 0) {
            mHand.get(halfHand).setRotation(0);
        }
    }

    // Uses the angle of the card to determine how much the card should be moved down.
    public float setVerticalDisplacement(CardView card) {
        double moveDownAmount = Math.abs(((mCardWidth / 2) * Math.sin(Math.toRadians(card.getRotation()))));
        return (float) moveDownAmount * 2;
    }

    // Returns the first occurance of the given card from the hand while also removing it from the hand
    public CardView pullFromHand(CardView card) {
        mHandLayout.removeView(card);
        mHand.removeFirstOccurrence(card);
        mCardsInHand--;
        return card;
    }

    // Pushes the hand layout below the screen, hiding it.
    public void hideBelowScreen() {
        int screenCenter = mContext.getResources().getDisplayMetrics().widthPixels / 2;
        for (CardView card : mHand) {
            card.animate().translationX(screenCenter - (mCardWidth / 2))
                    .translationY(card.getReturnPositionY() + 550)
                    .setDuration(200);
        }
        isHidden = true;
    }

    // Pulls the hand layout back to its original position
    public void bringBackToScreen() {
        for (CardView card : mHand) {
            card.animate().translationX(card.getReturnPositionX())
                    .translationY(card.getReturnPositionY() + setVerticalDisplacement(card))
                    .setDuration(200);
        }
        mHandLayout.bringToFront();
        isHidden = false;
    }

    // A method which makes debugging easier by telling me card locations
    public void logger() {
        Log.i("TAG", "Recent card current X, Y:" + mHand.peekLast().getX() + " "
                + mHand.peekLast().getY());
        Log.i("TAG", "Top card current X, Y:" + mHand.peekFirst().getX() + " "
                + mHand.peekFirst().getY());
        Log.i("TAG", "Recent card return X, Y:" + mHand.peekLast().getReturnPositionX() + " "
                + mHand.peekLast().getReturnPositionY());
        Log.i("TAG", "Top card return X, Y:" + mHand.peekFirst().getReturnPositionX() + " "
                + mHand.peekFirst().getReturnPositionY());
    }

    // Returns the amount of each card that should show, depending on number of cards in the hand
    public int calcSpaceBetweenCards() {
        if ((mRoomForHand / mCardsInHand) < mMinCardShowing) {
            return mMinCardShowing;
        } else if (mCardsInHand == 0) {
            return 0;
        } else if (mCardsInHand * mCardWidth <= mRoomForHand) {
            return mCardWidth;
        } else {
            return mRoomForHand / mCardsInHand;
        }

    }

    // Returns the amount of screen space taken up by the cards in the hand
    public int spaceOccupied() {
        if (mCardsInHand >= mMostCardsVisible) {
            return mRoomForHand + mCardWidth;
        } else if (mCardsInHand == 0) {
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

    public Context getContext() { return mContext;}

    public RelativeLayout getmHandLayout() {
        return mHandLayout;
    }

    public int getmCardsInHand() {
        return mCardsInHand;
    }
}
