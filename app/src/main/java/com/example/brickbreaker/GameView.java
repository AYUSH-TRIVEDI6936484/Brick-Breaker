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

    private void createBricks() {
        int brickWidth = dWidth / 8;
        int brickHeight = dHeight / 16;
        for (int column = 0; column<8; column++){
            for (int row=0; row<3; row++){
                bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                numBricks++;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        ballX += velocity.getX();
        ballY += velocity.getY();
        if ((ballX >= dWidth - ball.getWidth()) || ballX <=0){
            velocity.setX(velocity.getX() * -1);
        }
        if (ballY <=0){
            velocity.setY(velocity.getY() * -1);
        }
        if (ballY > paddleY + paddle.getHeight()){
            ballX = 1 + random.nextInt(bound:dWidth - ball.getWidth() -1);
            ballY = dHeight/3;
            if (mpMiss != null){
                mpMiss.start();
            }
            velocity.setX(xVelocity());
            velocity.setY(32);
            life--;
            if (life == 0){
                gameOver = true;
                launchGameOver();
            }
            if (((ballX + ball.getWidth()) >= paddleX)
                    && (ballX <= paddleX + paddle.getWidth())
                    && (ballY + ball.getHeight() >= paddleY)
                    && (ballY + ball.getHeight() <= paddleY + paddle.getHeight())){
                if (mpHit != null){
                    mpHit.start();
                }
                velocity.setX(velocity.getX() +1);
                velocity.SetY((velocity.getY() +1) * -1);
            }
            canvas.drawBitmal(ball, ballX, ballY, paint:null);
            canvas.drawBitmap(paddle, paddleX, paddleY, paint:null);
            for (int i=0; i<numBricks; i++){
                if (bricks[i].getVisibility()){
                    canvas.drawRect(r.bricks[i].column * bricks[i].width + 1,   paint:bricks[i].row * bricks[i].height + 1, bricks[i].column * bricks[i].width + bricks[i].width -1, bottom:bricks[i].row * bricks[i].height + bricks[i].height -1, brickPaint);
                }
            }
            canvas.drawText(text:"" + points, x:20, TEXT_SIZE, textPaint);
            if (life ==  2){
                healthPaint.setColor(Color.YELLOW);
            } else if (life == 1){
                healthPaint.setColor(Color.RED);
            }
            canvas.drawRect(left:dWidth-200, top:30, right:dWidth -200 + 60 * life, bottom:80, healthPaint);
        }
    }




    private void launchGameOver(){
        handler.removeCallbacksAndMessages(token:null);
        Intent intent = new Intent(context, GameOver.class);
        intent.putExtra(name:"points", points);
        context.startActicity(intent);
        ((Activity) content).finish();
    }



    private int xVelocity(){
        int[] values = {-35, -30, -25, 25, 30, 35};
        int index = random.nextInt(bound:6);
        return values[index];
    }
}
