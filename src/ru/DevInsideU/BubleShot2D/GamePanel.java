package ru.DevInsideU.BubleShot2D;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * Created by Администратор on 12.11.2015.
 */
public class GamePanel extends JPanel implements Runnable{

    //Field-POLE
    public static int WIDTH=600;
    public static int HEIGHT=600;

    private Thread thread;

    private BufferedImage image;
    private Graphics2D g;

    private int FPS;
    private double millisToFPS;
    private long timerFPS;
    private int sleepTime;

    public static GameBack background;
    public static Player player;
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Enemy> enemies;
    public static Wave wave;


    //CONSTRUCTOR
    public GamePanel(){
        super();

        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setFocusable(true);
        requestFocus();

        addKeyListener(new Listeners());

    }

    //Functions

    public void start(){
        thread = new Thread(this);
        thread.start();
    }

    public void run() {

        FPS = 32;
        millisToFPS = 1000/FPS;
        sleepTime = 0;

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g =(Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        background = new GameBack();
        player = new Player();
        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        wave = new Wave();

        while (true){//TODO States
            timerFPS = System.nanoTime();

            gameUpdate();
            gameRender();
            gameDraw();

            timerFPS = (System.nanoTime()-timerFPS )/1000000;
            if (millisToFPS > timerFPS){
                sleepTime = (int) (millisToFPS - timerFPS);
            }else sleepTime = 1;
            try {
                thread.sleep(sleepTime); //TODO FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println(sleepTime);
            timerFPS = 0;
            sleepTime = 1;

        }
    }
    public void gameUpdate(){
        //Background update
        background.update();

        //Player update
        player.update();

        //Bullets update
        for(int i = 0;i < bullets.size(); i++){
            bullets.get(i).update();
            boolean remove = bullets.get(i).remove();
            if(remove){
                bullets.remove(i);
                i--;
            }
        }
        //Enemies update
        for(int i = 0;i < enemies.size(); i++){
            enemies.get(i).update();
        }
        //Bullets-enemies collide
        for(int i=0;i<enemies.size();i++){
            Enemy e = enemies.get(i);
            double ex = e.getX();
            double ey = e.getY();
            for(int j=0;j<bullets.size();j++){
                Bullet b = bullets.get(j);
                double bx = b.getX();
                double by = b.getY();
                double dx = ex-bx;
                double dy = ey-by;
                double dist = Math.sqrt(dx*dx + dy*dy);
                if ((int)dist <= e.getR()+ b.getR()){
                    e.hit();
                    bullets.remove(j);
                    j--;
                    boolean remove= e.remove();
                    if(remove){
                        enemies.remove(i);
                        i--;
                    break;
                }

            }
            }
        }

        //Player-enemy collides
        for(int i=0; i < enemies.size();i++){
            Enemy e = enemies.get(i);
            double ex = e.getX();
            double ey = e.getY();

            double px = player.getX();
            double py = player.getY();
            double dx = ex - px;
            double dy = ey - py;
            double dist = Math.sqrt(dx * dx + dy * dy);
            if(dist <= e.getR() + player.getR()){
                e.hit();
                player.hit();
                boolean remove= e.remove();
                if(remove) {
                    enemies.remove(i);
                    i--;
                }
                boolean dead = player.dead();
                if(dead){
                    System.out.print("U DED");
                    thread.stop();
                }

            }
        }

        //Wave update
        wave.update();
    }

    public void gameRender(){
        //Background draw
        background.draw(g);

        //Player draw
        player.draw(g);

        //Bullets draw
        for(int i = 0;i < bullets.size(); i++){
            bullets.get(i).draw(g);
        }
         //Enemies draw
        for(int i = 0;i < enemies.size(); i++){
            enemies.get(i).draw(g);
        }
        //Wave draw
        if (wave.showWave()){
            wave.draw(g);
        }

    }
    private void gameDraw(){
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }
}
