package com.jacobherbel.pocketdeck.customListeners;

import android.view.DragEvent;
import android.view.View;

import com.jacobherbel.pocketdeck.cardStuff.CardView;

/**
 * Created by jacobherbel on 7/18/17.
 */
public class DropListener implements View.OnDragListener {
    View draggedView;
    CardView dropped;
    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                draggedView = (View) event.getLocalState();
                dropped = (CardView) draggedView;
                draggedView.setVisibility(View.INVISIBLE);
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                CardView dropTarget = (CardView) v;
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                break;
            default:
                break;
        }
        return true;
    }

}
