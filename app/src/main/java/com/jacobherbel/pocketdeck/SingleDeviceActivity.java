package com.jacobherbel.pocketdeck;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.jacobherbel.pocketdeck.cardStuff.Card;
import com.jacobherbel.pocketdeck.cardStuff.CardDeck;
import com.jacobherbel.pocketdeck.cardStuff.CardView;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class SingleDeviceActivity extends AppCompatActivity {

    private Context mContext;
    private CardDeck mCardDeck;
    private CardDeck mHand;
    private int mCardsInHand = 0;
    private Random rn = new Random(System.currentTimeMillis());
    private ViewUtils mUtils = new ViewUtils();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_device);
        mContext = this;
        mCardDeck = new CardDeck(mContext);
        mHand = new CardDeck(mContext);
        mCardDeck.fillDeck();
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

    // Adds a new card to the hand and responds according to how many cards are already in the hand
    public void addToHand(CardView cardView) {
        RelativeLayout handLayout = (RelativeLayout) findViewById(R.id.handLayout);
        if (mCardsInHand == 0) {
            mHand.add(cardView);
            mCardsInHand++;
            handLayout.addView(cardView);
        } else if (mCardsInHand <= 5) {
            mCardsInHand++;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cardView.getLayoutParams();
            params.addRule(RelativeLayout.RIGHT_OF, mHand.peekLast().getID());
            mHand.add(cardView);
            handLayout.addView(cardView);
        } else {
            //TODO add "replace a current card" or "toss" buttons when user hand is full
        }
        cardView.flipCard();
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
