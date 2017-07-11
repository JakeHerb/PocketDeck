package com.jacobherbel.pocketdeck;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jacobherbel.pocketdeck.cardStuff.Card;
import com.jacobherbel.pocketdeck.cardStuff.CardDeck;
import com.jacobherbel.pocketdeck.cardStuff.CardView;
import com.jacobherbel.pocketdeck.cardStuff.Hand;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class SingleDeviceActivity extends AppCompatActivity {

    private Context mContext;
    private CardDeck mCardDeck;
    private Hand mHand;
    private Random rn = new Random(System.currentTimeMillis());
    private ViewUtils mUtils = new ViewUtils();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_device);
        mContext = this;
        mCardDeck = new CardDeck(mContext);
        mHand = new Hand(mContext);
        mCardDeck.fillDeck();
        LinearLayout wholeScreen = (LinearLayout) findViewById(R.id.singleDeviceActivityLayout);
        wholeScreen.addView(mHand.getmHandLayout());
    }


    public void getTopCard(View view) {
        Button keepbtn = (Button) findViewById(R.id.newCardBtn);
        CardView topCard = (CardView) findViewById(R.id.card1);
        if (keepbtn.getText().equals("Grab a new card")) {
            topCard.setCard(mCardDeck.peek().getCard());
            topCard.flipCard();
            keepbtn.setText("Keep card");
        } else if (keepbtn.getText().equals("Keep card")) {
            addToHand(mCardDeck.nextView());
            topCard.flipCard();
            keepbtn.setText("Grab a new card");
        } else {
            keepbtn.setText("An issue occurred");
        }
    }

    public void addToHand(CardView cardView) {
        mHand.add(cardView);
    }

    // Cuts the deck between 1/4 of its size, and 3/4 of its size
    public void cutDeck(View View) {
        int cutIndex = rn.nextInt(mCardDeck.getCardsInDeck() / 2) + (mCardDeck.getCardsInDeck() / 4);
        mCardDeck.cut(cutIndex);
    }

    // Shuffles the deck
    public void shuffleDeck(View view) {
        mCardDeck.shuffle();
    }

}
