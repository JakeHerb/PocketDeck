package com.jacobherbel.pocketdeck.cardStuff;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.jacobherbel.pocketdeck.R;

import java.util.jar.Attributes;

/**
 * Created by jacobherbel on 7/7/17.
 */
public class CardView extends ImageView {

    private Card mCard;
    private boolean mRevealed;
    private float mReturnPositionX;
    private float mReturnPositionY;
    private float lastTouchX;
    private float lastTouchY;
    public CardView(Context context) {
        super(context);
        init(context);
    }

    public CardView(Context context, Card card) {
        super(context);
        this.mCard = card;
        init(context);
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public CardView(Context context, AttributeSet attrs, int defStyle, Card card) {
        super(context, attrs, defStyle);
        this.mCard = card;
        init(context);
    }

    private void init(Context context) {
        Resources res = this.getResources();
        mRevealed = false;
        this.setImageResource(R.drawable.back);
        this.setOnTouchListener(cardInHandListener);
    }


    public Card getCard() {
        if (this.mCard != null) {
            return this.mCard;
        }
        else {
            return null;
        }
    }

    public void setCard(Card newCard) {
        this.mCard = newCard;
    }

    public void horizontalReveal() {
        final CardView cView = this;
        cView.animate()
                .rotationYBy(90)
                .setDuration(300)
                .setInterpolator(new LinearInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        cView.setImageResource(mCard.getFrontImage());
                        cView.setRotationY(-90); // Must be set to -90 or else the image will show up mirrored
                        cView.animate()
                                .rotationYBy(90)
                                .setDuration(300)
                                .setInterpolator(new LinearInterpolator());
                    }
                });
        mRevealed = true;
    }

    public void horizontalReset() {
        final CardView cView = this;
        cView.animate()
                .rotationYBy(90)
                .setDuration(300)
                .setInterpolator(new LinearInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        cView.setImageResource(mCard.getBackImage());
                        cView.setRotationY(-90); // Must be set to -90 or else the image will show up mirrored
                        cView.animate()
                                .rotationYBy(90)
                                .setDuration(300)
                                .setInterpolator(new LinearInterpolator());
                    }
                });
        mRevealed = false;
    }

    public void flipCard() {
        if (mRevealed) {
            horizontalReset();
        }
        else {
            horizontalReveal();
        }
    }

    public View.OnTouchListener cardInHandListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    Log.i("TAG", "touched down");
                    v.bringToFront();
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

    // Returns the resource ID
    public int getID() {return this.getId();}

    public boolean isRevealed() {return mRevealed;}

    public void setReturnPositionX(float x) {mReturnPositionX = x;}

    public void setReturnPositionY(float y ) {mReturnPositionY = y;}

    public float getReturnPositionX() {return mReturnPositionX;}

    public float getReturnPositionY() {return mReturnPositionY;}
}
