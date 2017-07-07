package com.jacobherbel.pocketdeck.cardStuff;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.jacobherbel.pocketdeck.R;

import java.util.jar.Attributes;

/**
 * Created by jacobherbel on 7/7/17.
 */
public class CardView extends ImageView {

    Card mcard;
    boolean isRevealed = false;
    public CardView(Context context) {
        super(context);
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
        this.mcard = card;
        init(context);
    }

    private void init(Context context) {
        Resources res = this.getResources();
        this.setImageResource(R.drawable.back);
    }


    public Card getCard() {
        if (this.mcard != null) {
            return this.mcard;
        }
        else {
            return null;
        }
    }

    public void setCard(Card newCard) {
        this.mcard = newCard;
    }

    public void horizontalReveal() {
        final CardView cview = this;
        cview.animate()
                .rotationYBy(90)
                .setDuration(300)
                .setInterpolator(new LinearInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        cview.setImageResource(mcard.getFrontImage());
                        cview.setRotationY(-90); // Must be set to -90 or else the image will show up mirrored
                        cview.animate()
                                .rotationYBy(90)
                                .setDuration(300)
                                .setInterpolator(new LinearInterpolator());
                    }
                });
        isRevealed = true;
    }

    public void horizontalReset() {
        final CardView cview = this;
        cview.animate()
                .rotationYBy(90)
                .setDuration(300)
                .setInterpolator(new LinearInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        cview.setImageResource(mcard.getBackImage());
                        cview.setRotationY(-90); // Must be set to -90 or else the image will show up mirrored
                        cview.animate()
                                .rotationYBy(90)
                                .setDuration(300)
                                .setInterpolator(new LinearInterpolator());
                    }
                });
        isRevealed = false;
    }

    public void flipCard() {
        if (isRevealed) {
            horizontalReset();
        }
        else {
            horizontalReveal();
        }
    }
}
