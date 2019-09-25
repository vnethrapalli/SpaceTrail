package Minigames;

import javax.swing.*;
import java.awt.*;

public class AsteroidsTester {

    public static void main(String[] args) {
        JFrame asteroid = new JFrame();
        asteroid.setTitle("Asteroids");
        asteroid.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        asteroid.setResizable(false);
        asteroid.setSize(2 * (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3 , 4 * (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5);
        asteroid.add(new Asteroids());
        asteroid.setLocationRelativeTo(null);
        asteroid.setVisible(true);
    }
}
