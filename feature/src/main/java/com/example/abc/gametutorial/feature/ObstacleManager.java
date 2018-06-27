package com.example.abc.gametutorial.feature;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class ObstacleManager {

    //higerindex=lower on screen-higher y value
    private ArrayList<Obstacle> obstacles;
    private int playergap;
    private int obstaclegap;
    private int obstacleheight;
    private int color;
    private int score=0;


    private long startTime;
    private long initTime;//to control obstacle time


    public ObstacleManager(int playergap,int obstaclegap,int obstacleheight,int color){
        this.playergap=playergap;
        this.obstaclegap=obstaclegap;
        this.obstacleheight=obstacleheight;
        this.color=color;

        //starttime for obstacles
       startTime=  initTime = System.currentTimeMillis();
       // startTime= System.currentTimeMillis();
        obstacles=new ArrayList<>();
        
        populateObastacles();
    }

    public boolean playerCollide(RectPlayer rectPlayer){
        for(Obstacle ob:obstacles){
        if(ob.playerCollide(rectPlayer))
            return true;
        }
        return false;
    }

    private void populateObastacles() {

        int currY= -5*Constant.SCREEN_HEIGHT/4;

        while (currY<0 ){

            int xStart= (int) (Math.random()*(Constant.SCREEN_WIDTH-playergap));
            //playergap is subtracted so the gap don't get generated outside screen

            obstacles.add(new Obstacle(obstacleheight,color,xStart,currY,playergap));

            //currenty/starty will get changed to new value for every obstacle that
            //would be equal to previous obstacle height +  obsgap
            currY += obstacleheight + obstaclegap;



        }

    }
        public void update(){

        int elapsedtime=(int)(System.currentTimeMillis()-startTime);
        startTime=System.currentTimeMillis();
        //increase speed as time goes on
        float speed=(float) (Math.sqrt( 1 +(startTime - initTime)/2000.0))*
                Constant.SCREEN_HEIGHT/(10000.0f);
       // float speed=      Constant.SCREEN_HEIGHT/(10000.0f);
        for(Obstacle ob:obstacles){
        ob.incrementY(speed * elapsedtime);//distance=S*t



        }
        if(obstacles.get(obstacles.size()-1).getRectangle().top >= Constant.SCREEN_HEIGHT){
            //means below the screen  then generate new rectangle at top
            int xStart= (int) (Math.random()*(Constant.SCREEN_WIDTH-playergap));
            obstacles.add(0,new Obstacle(obstacleheight,color,xStart,
                    (obstacles.get(0).getRectangle().top - obstacleheight - obstaclegap) ,playergap));

           obstacles.remove(obstacles.size()-1);
            score++;


        }
        }

        public void draw(Canvas canvas){
        for(Obstacle ob : obstacles){
            //draw every obstacle on canvas
            ob.draw(canvas);
        }
            Paint paint=new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            canvas.drawText("Score: "+score,50,50 +paint.descent() - paint.ascent() ,paint);


        }
}
