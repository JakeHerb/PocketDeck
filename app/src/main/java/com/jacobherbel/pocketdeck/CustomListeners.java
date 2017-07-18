package com.jacobherbel.pocketdeck;

import android.text.method.Touch;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jacobherbel on 7/18/17.
 */
public class CustomListeners {
    private float lastTouchX;
    private float lastTouchY;
    public View.OnTouchListener cardInHandListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
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
    };
}
