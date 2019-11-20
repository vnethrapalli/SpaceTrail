package testers_and_runners;

import Minigames.Wars.MicroWars;

import javax.swing.*;
import java.awt.*;


public class MicroWarsTester{
    public static void main(String[] args) {
        MicroWars m =new MicroWars(10,500,400,3,2,3,4,3,null);
        JFrame dumbGame = new JFrame();
        dumbGame.setTitle("DumbGame");
        dumbGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dumbGame.setResizable(false);
        dumbGame.setSize(2 * (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3 , 4 * (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5);
        dumbGame.add(m);
        dumbGame.setLocationRelativeTo(null);
        dumbGame.setVisible(true);
        m.play();
    }
}
