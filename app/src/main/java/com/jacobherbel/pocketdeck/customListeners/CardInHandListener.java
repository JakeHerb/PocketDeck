package com.jacobherbel.pocketdeck.customListeners;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jacobherbel.pocketdeck.R;
import com.jacobherbel.pocketdeck.cardStuff.CardView;
import com.jacobherbel.pocketdeck.cardStuff.Hand;

/**
 * Created by jacobherbel on 7/18/17.
 */
public class CardInHandListener implements View.OnTouchListener{

    private ViewGroup mRoot;
    private Hand mHand;
    public CardInHandListener(ViewGroup root, Hand hand) {
        mRoot = root;
        mHand = hand;
    }
    float lastTouchX;
    float lastTouchY;
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        CardView v = (CardView) view;
        float xBoundary = mRoot.getContext().getResources().getDisplayMetrics().widthPixels - v.getWidth();
        float yBoundary = mRoot.getContext().getResources().getDisplayMetrics().heightPixels - v.getHeight();
        switch (event.getAction()) {

            // Remove the card from the hand layout, straighten it, and place it in the root layout
            // in the same location
            case MotionEvent.ACTION_DOWN: {
                Log.i("TAG", "touched down");
                v.bringToFront();
                int[] viewLocation = new int[2];
                v.getLocationInWindow(viewLocation);
                mHand.getmHandLayout().removeView(v);
                mRoot.addView(v);
                mHand.pullFromHand(v);
                mHand.arrangeCards();
                v.setRotation(0);
                v.setY(viewLocation[1]); // TODO figure out a way to make this smoother
                v.setRotation(0);
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
                } else {
                    Log.i("TAG", "Touching a wall on X axis");
                }
                if (!(v.getY() + dY > yBoundary || v.getY() + dY < 0)) {
                    v.animate().translationYBy(dY)
                            .setDuration(0);
                    Log.i("TAG", "moving y by " + dY);
                    lastTouchY = y;
                } else {
                    Log.i("TAG", "Touching a wall on Y axis");
                }
                break;
            }

            // If the user lets go, leave the card at its location and set it as an On Table Card
            case MotionEvent.ACTION_UP: {
                Log.i("TAG", "touched up");
                if (event.getRawY() > 1750) { // TODO get rid of this magic number
                    ViewGroup parent = (ViewGroup) v.getParent();
                    parent.removeView(v);
                    mHand.add(v);
                } else {
                    v.setReturnPositionY(v.getY());
                    v.setReturnPositionX(v.getX());
                    v.setOnTouchListener(new CardOnTableListener(mRoot.getContext(), mHand));
                }
                break;
            }
        }

        return true;
    }
}

