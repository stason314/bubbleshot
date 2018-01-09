package ru.DevInsideU.BubleShot2D;

import javax.swing.*;

/**
 * Created by Администратор on 12.11.2015.
 */
public class GameStart {

    public static void main(String[] args){
        GamePanel panel = new GamePanel();

        JFrame startFrame = new JFrame("BUBLESHOT");
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startFrame.setContentPane(panel);
        startFrame.pack();
        startFrame.setLocationRelativeTo(null);
        startFrame.setVisible(true);

        panel.start();
    }
}
