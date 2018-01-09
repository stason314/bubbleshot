package ru.DevInsideU.BubleShot2D;

import java.awt.*;

/**
 * Created by Администратор on 12.11.2015.
 */
public class GameBack {
    //Fields
    private Color color;

    private long timerM;
    private long timerS;
    private long timerMS;
    private long timerDiff;

    //Constructor
    public GameBack(){
        color = Color.BLUE;

        timerM = System.nanoTime()/1000000;
        timerS = System.nanoTime()/1000000;
        timerMS = System.nanoTime();
        timerDiff = timerM - System.nanoTime();
    }
    //Functions
    public  void update(){

    }

    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);



        g.setFont(new Font("consolas",Font.PLAIN, 20));
        g.setColor(new Color(255,255,255));
        String s = timerDiff + ":" + timerS + ":" +timerMS;
        long length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH - (int) length , GamePanel.HEIGHT);

    }
}
