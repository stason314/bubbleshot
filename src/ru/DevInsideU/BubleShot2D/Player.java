package ru.DevInsideU.BubleShot2D;

import java.awt.*;

/**
 * Created by Администратор on 12.11.2015.
 */
public class Player {

    //Fields
    private double x;
    private double y;
    private int r;

    private  double dx; //Move coaf
    private  double dy;

    private  int speed;

    private  double health;

    private Color color1;
    private Color color2;

    public static boolean up;
    public static boolean down;
    public static boolean left;
    public static boolean right;
    
    public static boolean isFiring;

    //Constructor
    public Player(){
        x = GamePanel.WIDTH / 2;
        y = GamePanel.HEIGHT / 2;

        r = 5;

        speed = 5;

        dx = 0;
        dy = 0;

        health = 3;

        color1 = Color.white;

        up = false;
        down = false;
        left = false;
        right = false;
    }

    //Functions
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public int getR() {return r; }

    public void hit(){
        health--;
        System.out.println(health);
    }
    public boolean dead(){
        if(health == 0){
            return true;
        }else return false;
    }

    public void update(){
        if(up && y > r){
            dy = -speed;
        }
        if(down && y < GamePanel.HEIGHT-r){
            dy = speed;
        }
        if(left && x > r){
            dx = -speed;
        }
        if(right && x < GamePanel.WIDTH-r){
            dx = speed;
        }
        if(up && left || up && right || down && left || down && right){
            double angle = Math.toRadians(45);
            dy = dy*Math.sin(angle);
            dx = dx*Math.cos(angle);
        }

        y+=dy;
        x+=dx;

        dy = 0;
        dx = 0;
        if(isFiring){
            GamePanel.bullets.add(new Bullet());
        }

    }

    public void draw(Graphics2D g){
        g.setColor(color1);
        g.fillOval((int) (x-r),(int) (y-r), 2*r, 2*r);
        g.setStroke(new BasicStroke(3));
        g.setColor(color1.darker());
        g.drawOval((int) (x-r),(int) (y-r), 2*r, 2*r);
        g.setStroke(new BasicStroke(1));
    }
}
