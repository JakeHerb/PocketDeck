package com.jacobherbel.pocketdeck.customListeners;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jacobherbel.pocketdeck.R;
import com.jacobherbel.pocketdeck.cardStuff.CardView;
import com.jacobherbel.pocketdeck.cardStuff.Hand;

/**
 * Created by jacobherbel on 7/18/17.
 */
public class DefaultCardListener implements View.OnTouchListener{

    private ViewGroup root;
    private Hand hand;
    public DefaultCardListener(ViewGroup root, Hand hand) {
        this.root = root;
        this.hand = hand;
    }
    float lastTouchX;
    float lastTouchY;
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        CardView v = (CardView) view;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Log.i("TAG", "touched down");
                v.bringToFront();

                // When moving from one layout to another, the Y value gets weird, so for now I have to set
                // the Y value as just the raw input data.
                if (v.getIsInHand()) {
                    hand.getmHandLayout().removeView(v);
                    root.addView(v);
                    hand.pullFromHand(v);
                    hand.arrangeCards();
                    v.setRotation(0);
                    v.setY(event.getRawY()); // TODO figure out a way to make this smoother
                }
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
                break;
            }
        }

        return true;
    }
}

