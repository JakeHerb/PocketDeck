package com.jacobherbel.pocketdeck;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.jacobherbel.pocketdeck.cardStuff.Card;
import com.jacobherbel.pocketdeck.cardStuff.CardDeck;

import java.util.Random;

public class SingleDeviceActivity extends AppCompatActivity {

    private Context context;
    private CardDeck cardDeck;
    private Card[] hand = new Card[5];
    private int cardsInHand = 0;
    private Random rn = new Random(System.currentTimeMillis());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_device);
        context = this;
        cardDeck = new CardDeck(context);
    }


    public void getTopCard(View view) {
        ImageView card = (ImageView) findViewById(R.id.card1);
        Card newCard = cardDeck.next();
        horizontalReveal(card, newCard);
    }

    public void addToHand(View view, Card card) {
        if (cardsInHand <= 5) {
            hand[cardsInHand] = card;
            cardsInHand++;
        }
        else {
            //TODO add "replace a current card" or "toss" buttons when user hand is full
        }
    }

    // Cuts the deck between 1/4 of its size, and 3/4 of its size
    public void cutDeck(View view) {
        int cutIndex = rn.nextInt(cardDeck.getCardsInDeck() / 2) + (cardDeck.getCardsInDeck() / 4);
        cardDeck.cut(cutIndex);
    }

    // Flips the card by 90 degrees, swaps out the image, and flips the card another 90 degrees
    public void horizontalReveal(ImageView view, Card card) {
        final ImageView mview = view; // Must be declared final for use in run method
        final Card mcard = card; // Must be declared final for use in run method
        view.animate()
                .rotationYBy(90)
                .setDuration(300)
                .setInterpolator(new LinearInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        mview.setImageResource(mcard.getFrontImage());
                        mview.setRotationY(-90); // Must be set to -90 or else the image will show up mirrored
                        mview.animate()
                                .rotationYBy(90)
                                .setDuration(300)
                                .setInterpolator(new LinearInterpolator());
                    }
                });
    }

}
