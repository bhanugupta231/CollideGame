package com.example.abc.gametutorial.feature;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    public static final int MAX_FPS=30;
    private double averagefps;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;


    public void setRunning(boolean running){
        this.running=running;

    }




public MainThread(SurfaceHolder surfaceHolder,GamePanel gamePanel){
            super();
            this.surfaceHolder=surfaceHolder;
            this.gamePanel=gamePanel;

}

@Override
    public void run(){
    long starttime;
    long timemillis =1000/MAX_FPS;
    long waittime;
    int framecount=0;
    long totaltime=0;
    long targettime =1000/MAX_FPS;
    while (running){
        starttime=System.nanoTime();
        canvas=null;

        try{
           canvas=this.surfaceHolder.lockCanvas();
           synchronized (surfaceHolder){
               this.gamePanel.update();
               this.gamePanel.draw(canvas);


           }
        }catch (Exception e){e.printStackTrace();}
        finally {
            if(canvas!=null){
                try {
                surfaceHolder.unlockCanvasAndPost(canvas);


                }catch (Exception e){e.printStackTrace();}
            }
        }
        timemillis=(System.nanoTime()-starttime)/1000000;
        waittime=targettime-timemillis;
        try {
          if(waittime>0){
              this.sleep(waittime);
          }


        }catch (Exception e){e.printStackTrace();}

        totaltime+=System.nanoTime() - starttime;
        framecount++;
        if(framecount==MAX_FPS){
            averagefps=1000/((totaltime/framecount)/1000000);
            framecount=0;
            totaltime=0;
            System.out.println(averagefps);
        }




    }
}

}
