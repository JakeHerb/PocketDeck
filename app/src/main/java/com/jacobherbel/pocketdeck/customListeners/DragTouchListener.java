package com.jacobherbel.pocketdeck.customListeners;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jacobherbel on 7/18/17.
 */
public class DragTouchListener implements View.OnTouchListener{

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("View", "more text");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, shadowBuilder, v, 0);
            return true;
        } else {
            return false;
        }
    }
}
