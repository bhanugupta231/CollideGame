package com.example.abc.gametutorial.feature;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread mainThread;
    private Rect r=new Rect();

    private RectPlayer rectPlayer;
    private ObstacleManager obstacleManager;
    private boolean movingplayer=false;
    private boolean gameover=false;
    private long gameovertime;

    private Point playerpoint;
    public GamePanel(Context context)
    {
        super(context);
        getHolder().addCallback(this);
        mainThread=new MainThread(getHolder(),this);
        rectPlayer=new RectPlayer(new Rect(100,100,200,200),
                Color.rgb(255,0,0));
        playerpoint=new Point(Constant.SCREEN_WIDTH/2,3*Constant.SCREEN_HEIGHT/4);
        rectPlayer.update(playerpoint);

        obstacleManager=new ObstacleManager(200,350,75,Color.BLACK);


        setFocusable(true);

    }
    public void reset() {

        playerpoint=new Point(Constant.SCREEN_WIDTH/2,3*Constant.SCREEN_HEIGHT/4);
        rectPlayer.update(playerpoint);

        obstacleManager=new ObstacleManager(200,350,75,Color.BLACK);
        movingplayer=false;


    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mainThread=new MainThread(getHolder(),this);

        mainThread.setRunning(true);
        mainThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry=true;
        while (true){
            try{
            mainThread.setRunning(false);
            mainThread.join();
            }catch (Exception e){e.printStackTrace();}
        }
       // retry=false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:


                if(!gameover &&
                        rectPlayer.getRectangle().contains((int)event.getX(),(int)event.getY())){
                    movingplayer=true;
                     }
                    if(gameover && System.currentTimeMillis()-gameovertime >=2000){
                        reset();
                        gameover=false;

                }

                break;

            case MotionEvent.ACTION_MOVE:
                if(!gameover && movingplayer)
                      playerpoint.set((int)event.getX(),(int)event.getY());
              break;
            //stop tapping anywhere on screen
            case MotionEvent.ACTION_UP:
                movingplayer=false;
                break;
        }

        return true;
        //return super.onTouchEvent(event);
        //update position whereever we touch
    }



    public void update(){
        if(!gameover){
        //  update playerpoint in rectplayer
        rectPlayer.update(playerpoint);

        obstacleManager.update();
        if(obstacleManager.playerCollide(rectPlayer)){
            //if collided
            gameover=true;
            gameovertime=System.currentTimeMillis();
        }
        }

    }
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        //make screen white
        canvas.drawColor(Color.WHITE);
        //drawing rectplayer on screen with red color

        rectPlayer.draw(canvas);
        obstacleManager.draw(canvas);
        if(gameover){
            Paint paint=new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            drawCenterText(canvas,paint,"Game Over");

        }
    }
    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);

        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);

    }

}
