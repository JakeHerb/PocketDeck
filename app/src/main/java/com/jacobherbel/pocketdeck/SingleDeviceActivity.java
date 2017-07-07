package com.jacobherbel.pocketdeck;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.jacobherbel.pocketdeck.cardStuff.Card;
import com.jacobherbel.pocketdeck.cardStuff.CardDeck;
import com.jacobherbel.pocketdeck.cardStuff.CardView;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class SingleDeviceActivity extends AppCompatActivity {

    private Context mContext;
    private CardDeck mCardDeck;
    private CardDeck mHand;
    private int mCardsInHand = 0;
    private Random rn = new Random(System.currentTimeMillis());

    public int previousHandView; // TODO delete this after making CardDeck a list of CardViews


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_device);
        mContext = this;
        mCardDeck = new CardDeck(mContext);
        mHand = new CardDeck(mContext);
        mCardDeck.fillDeck();
    }


    public void getTopCard(View mView) {
        Button keepbtn = (Button) findViewById(R.id.newCardBtn);
        CardView cardImage = (CardView) findViewById(R.id.card1);
        if (keepbtn.getText().equals("Grab a new card")) {
            Card newCard = mCardDeck.next();
            cardImage.setCard(newCard);
            cardImage.flipCard();
            keepbtn.setText("Keep card");
        }
        else if (keepbtn.getText().equals("Keep card")) {
            addToHand(mView, cardImage.getCard());
            cardImage.flipCard();
            keepbtn.setText("Grab a new card");
        }

        else {
            keepbtn.setText("An issue occurred");
        }

    }

    // Adds a new card to the hand and responds according to how many cards are already in the hand
    public void addToHand(View mView, Card mCard) {
        RelativeLayout handLayout = (RelativeLayout) findViewById(R.id.handLayout);
        CardView newHandView = new CardView(mContext);
        newHandView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        // If else block generates a new ID depending on what version of Android the user is using
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            newHandView.setId(generateViewId());
        } else {
            newHandView.setId(View.generateViewId());
        }
        // end TODO
        if (mCardsInHand == 0) {
            mHand.add(mCard);
            mCardsInHand++;
            newHandView.setCard(mCard);
            handLayout.addView(newHandView);
            previousHandView = newHandView.getId();

        }
        else if (mCardsInHand <= 5) {
            mHand.add(mCard);
            mCardsInHand++;
            newHandView.setCard(mCard);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) newHandView.getLayoutParams();
            params.addRule(RelativeLayout.RIGHT_OF, previousHandView);
            handLayout.addView(newHandView);
            previousHandView = newHandView.getId();
        }
        else {
            //TODO add "replace a current card" or "toss" buttons when user hand is full
        }
        newHandView.flipCard();
    }

    // Cuts the deck between 1/4 of its size, and 3/4 of its size
    public void cutDeck(View mView) {
        int cutIndex = rn.nextInt(mCardDeck.getCardsInDeck() / 2) + (mCardDeck.getCardsInDeck() / 4);
        mCardDeck.cut(cutIndex);
    }



    // Taken from a user online as a way to generate ID's for API's less than version 17.
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * Generate a value suitable for use in generateViewID
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }




}
