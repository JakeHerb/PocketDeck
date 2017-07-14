package com.jacobherbel.pocketdeck;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
    private GestureDetectorCompat mDetector;


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
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
    }


    // Shows the user the top card from the deck, and allows them to add it to their hand
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

    // Code to detect the user making a quick swipe in any of the 4 main directions
    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG, "onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,
                               float velocityX, float velocityY) {
            switch (getSlope(e1.getX(), e1.getY(), e2.getX(), e2.getY())) {
                case 1:
                    Log.d(DEBUG_TAG, "top");
                    if (mHand.isHidden) {
                        mHand.bringBackToScreen();
                    }
                    return true;
                case 2:
                    Log.d(DEBUG_TAG, "left");
                    return true;
                case 3:
                    Log.d(DEBUG_TAG, "down");
                    if (!mHand.isHidden) {
                        mHand.hideBelowScreen();
                    }
                    return true;
                case 4:
                    Log.d(DEBUG_TAG, "right");
                    return true;
            }
            return false;
        }

        private int getSlope(float x1, float y1, float x2, float y2) {
            Double angle = Math.toDegrees(Math.atan2(y1 - y2, x2 - x1));
            if (angle > 45 && angle <= 135)
                // top
                return 1;
            if (angle >= 135 && angle < 180 || angle < -135 && angle > -180)
                // left
                return 2;
            if (angle < -45 && angle >= -135)
                // down
                return 3;
            if (angle > -45 && angle <= 45)
                // right
                return 4;
            return 0;
        }
    }

}
