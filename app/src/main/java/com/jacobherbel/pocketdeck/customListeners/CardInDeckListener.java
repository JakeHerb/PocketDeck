package com.jacobherbel.pocketdeck.customListeners;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.jacobherbel.pocketdeck.cardStuff.CardDeck;
import com.jacobherbel.pocketdeck.cardStuff.CardView;
import com.jacobherbel.pocketdeck.cardStuff.Hand;

/**
 * Created by jacobherbel on 7/21/17.
 */
public class CardInDeckListener implements View.OnTouchListener {

    private ViewGroup mRoot;
    private Hand mHand;
    private Context mContext;
    private CardDeck mDeck;
    public CardInDeckListener(Context context, ViewGroup root, Hand hand, CardDeck deck) {
        mContext = context;
        mRoot = root;
        mHand = hand;
        mDeck = deck;
    }

    float lastTouchX;
    float lastTouchY;
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        CardView v = (CardView) view;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Log.i("TAG", "touched down");
                final float x = event.getRawX();
                final float y = event.getRawY();

                // Remember where we started
                lastTouchX = x;
                lastTouchY = y;
                break;
            }

            // Keeps track of the difference between where the card was last time you pressed
            // and where it is now, then moves the view accordingly
            case MotionEvent.ACTION_MOVE: {
                // TODO check if the are low enough to bring the hand back to the screen
                final float x = event.getRawX();
                final float y = event.getRawY();
                final float dX = x - lastTouchX;
                final float dY = y - lastTouchY;
                Log.i("TAG", "moving: (" + dX + ", " + dY + ")");
                v.animate().translationXBy(dX)
                        .translationYBy(dY)
                        .setDuration(0);
                lastTouchX = x;
                lastTouchY = y;
                break;
            }
            case MotionEvent.ACTION_UP: {
                Log.i("TAG", "touched up");
                if (event.getRawY() > 1750) { // TODO get rid of this magic number
                    ViewGroup parent = (ViewGroup) v.getParent();
                    parent.removeView(v);
                    mHand.add(mDeck.grabTopCard());
                    mDeck.rearrange();
                    Log.i("TAG", "put in hand");
                } else {
                    v.animate().translationY(v.getReturnPositionY())
                            .translationX(v.getReturnPositionX())
                            .setDuration(100);
                    Log.i("TAG", "teleport back");
                }


                break;
            }
        }

        return true;
    }
}
