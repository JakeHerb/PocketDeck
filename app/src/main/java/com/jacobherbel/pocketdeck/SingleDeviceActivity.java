package com.jacobherbel.pocketdeck;

import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    private int index = 0;
    private Random rn = new Random(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_device);
        context = this;
        cardDeck = new CardDeck(context);
    }


    public void getRandomCard(View view) {
        ImageView card = (ImageView) findViewById(R.id.card1);
        card.setImageResource(cardDeck.next().getImage());

        ImageView topCard = (ImageView) findViewById(R.id.topOfDeck);
        TranslateAnimation moveLefttoRight = new TranslateAnimation(0, 300, 0, 0); // This stuff just slides the card to the right
        moveLefttoRight.setDuration(1000);
        moveLefttoRight.setFillAfter(true);
        card.startAnimation(moveLefttoRight);

    }

    public void addToHand(View view, Card card) {
        if (cardsInHand <= 5) {
            hand[cardsInHand - 1] = card;
        }
        else {
            //TODO add "replace a current card" or "toss" buttons when user hand is full
        }
    }

    // Cuts the deck between 1/4 of its size, and 3/4 of its size
    public void cutDeck(View view) {
        int cutIndex = rn.nextInt(cardDeck.getCardsInDeck() / 2) + (cardDeck.getCardsInDeck() / 4);
        cardDeck.cut(cutIndex);
        Button btn = (Button) findViewById(R.id.newCardBtn); // Changing text of the button for debugging purposes
        btn.setText("" + cutIndex); // This line is for debugging purposes also
    }


}
