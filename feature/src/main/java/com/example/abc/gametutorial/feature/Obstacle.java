package com.example.abc.gametutorial.feature;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Obstacle implements GameObject{
    private Rect rect;
    private Rect rect2;
    private int color;


    public Rect getRectangle(){
        return rect;
    }
    //method to increment y value







    public Obstacle(int  rectHeight, int color,int startX,int startY,int playergap) {

        this.color = color;
        //startx is start point of gap
        //creating 2 rectangles with gap bw them
        rect=new Rect(0,startY,startX,startY+rectHeight);
        rect2=new Rect(startX+playergap,startY,Constant.SCREEN_WIDTH,startY+rectHeight);

    }
    public void incrementY(float y){
        rect.top +=y;
        rect.bottom +=y;
        rect2.top +=y;
        rect2.bottom +=y;


    }
    public boolean playerCollide(RectPlayer player){


    return Rect.intersects(rect,player.getRectangle())
            || Rect.intersects(rect2,player.getRectangle()) ;
    }
    @Override
    public void draw(Canvas canvas) {
        Paint paint=new Paint();
        paint.setColor(color);
        canvas.drawRect(rect,paint);
        canvas.drawRect(rect2,paint);
    }

    @Override
    public void update() {

    }
}
