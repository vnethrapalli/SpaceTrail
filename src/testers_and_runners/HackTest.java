import Minigames.HackerMan.HackerMan;
import testers_and_runners.NodLoder;

import javax.swing.*;
import java.awt.*;

public class HackTest {
    public static void main(String[] args) {
        JFrame dumbGame = new JFrame();
        HackerMan m =new HackerMan(9,6, new NodLoder("test.txt",dumbGame));
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
