package com.example.brickbreaker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;

import java.util.Random;

public class GameView extends View{
    Context context;
    float ballX,ballY;
    velocity v=new velocity(25,32);
    Handler handler;
    final long UPDATE_MILLIS=30;
    Runnable runnable;
    Paint textpaint=new Paint();
    Paint healthPaint=new Paint();
    Paint brickPaint=new Paint();
    float TEXT_SIZE=120;
    float paddleX,paddleY;
    float oldX,oldPaddleX;
    int points=0;
    int life=3;
    Bitmap ball,paddle;
    int dWidth,dHeight;
    int ballWidth,ballHeight;
    MediaPlayer mpHit,mpMiss,mpBreak;
    Random random;
    Brick[] bricks=new Brick[30];
    int numBricks=0;
    int brokenBricks=0;
    boolean gameOver=false;

    public GameView(Context context) {
        super(context);
        this.context = context;
        ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        paddle = BitmapFactory.decodeResource(getResources(), R.drawable.paddle);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        mpHit=MediaPlayer.create(context,R.raw.hit);
    }
}
