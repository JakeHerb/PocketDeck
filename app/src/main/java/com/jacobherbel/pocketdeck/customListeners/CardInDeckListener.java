package com.jacobherbel.pocketdeck.customListeners;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;

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

    private float lastTouchX;
    private float lastTouchY;
    private long startClickTime;
    private float moveDistance;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        CardView v = (CardView) view;
        float xBoundary = mContext.getResources().getDisplayMetrics().widthPixels - v.getWidth();
        float yBoundary = mContext.getResources().getDisplayMetrics().heightPixels - v.getHeight();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Log.i("TAG", "touched down");
                moveDistance = 0;
                final float x = event.getRawX();
                final float y = event.getRawY();
                startClickTime = System.currentTimeMillis();
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
                // Check if the card is being placed off of the screen
                if (!(v.getX() + dX > xBoundary || v.getX() + dX < 0)) {
                    v.animate().translationXBy(dX)
                            .setDuration(0);
                    Log.i("TAG", "moving x by " + dX);
                    lastTouchX = x;
                    moveDistance += dX;
                } else {
                    Log.i("TAG", "Touching a wall on X axis");
                }
                if (!(v.getY() + dY > yBoundary || v.getY() + dY < 0)) {
                    v.animate().translationYBy(dY)
                            .setDuration(0);
                    Log.i("TAG", "moving y by " + dY);
                    lastTouchY = y;
                    moveDistance += dY;
                } else {
                    Log.i("TAG", "Touching a wall on Y axis");
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                Log.i("TAG", "touched up");
                if (event.getRawY() > 1750) { // TODO get rid of this magic number
                    CardView card = mDeck.grabTopCard();
                    mHand.add(card);
                    mDeck.rearrange();
                    Log.i("TAG", "put in hand");
                } else if (System.currentTimeMillis() - startClickTime < ViewConfiguration.getTapTimeout() && Math.abs(moveDistance) < 100) {
                    Toast.makeText(mContext, "Tapping", Toast.LENGTH_SHORT).show();
                    v.animate().translationX(v.getReturnPositionX())
                            .translationY(v.getReturnPositionY())
                            .setDuration(0);
                    // TODO create the buttons
                } else {
                    mRoot.addView(mDeck.grabTopCard());
                    v.setReturnPositionX(v.getX());
                    v.setReturnPositionY(v.getY());
                    v.setOnTouchListener(new CardOnTableListener(mContext, mHand));
                    mDeck.rearrange();
                    Log.i("TAG", "Removing card from deck and placing at " + v.getReturnPositionX() + ", " + v.getReturnPositionY());
                }
                break;
            }
        }
        return true;
    }

}
