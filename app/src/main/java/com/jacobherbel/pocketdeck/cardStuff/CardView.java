package com.jacobherbel.pocketdeck.cardStuff;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import com.jacobherbel.pocketdeck.R;


/**
 * Created by jacobherbel on 7/7/17.
 */
public class CardView extends ImageView {

    private Card mCard;
    private boolean mRevealed;
    private boolean mIsInHand;
    private float mReturnPositionX;
    private float mReturnPositionY;
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


    // Returns the resource ID
    public int getID() {return this.getId();}

    public boolean isRevealed() {return mRevealed;}

    public void setReturnPositionX(float x) {mReturnPositionX = x;}

    public void setReturnPositionY(float y ) {mReturnPositionY = y;}

    public void setIsInHand(boolean bool) {mIsInHand = bool;}

    public float getReturnPositionX() {return mReturnPositionX;}

    public float getReturnPositionY() {return mReturnPositionY;}

    public boolean getIsInHand() {return mIsInHand;}
}
