package Minigames;

import testers_and_runners.NodLoder;

import javax.swing.*;
import java.awt.*;

public class FishingTester {

    public static void main(String[] args) {
        JFrame fishing = new JFrame();
        fishing.setTitle("Fishing");
        fishing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fishing.setResizable(false);
        fishing.setSize(2 * (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3 , 4 * (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5);
        fishing.add(new FishingPanel(new NodLoder("txt.txt",new JFrame())));
        fishing.setLocationRelativeTo(null);
        fishing.setVisible(true);
    }
}
