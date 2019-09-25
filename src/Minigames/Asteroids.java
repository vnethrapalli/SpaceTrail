package Minigames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Asteroids extends JPanel {

    private int x, y;
    private Timer pos;
    private boolean dirPressed[] = new boolean[4]; // up, down, left, right

    public Asteroids() {

        pos = new Timer(20, new AdjustPositionTimer());
        pos.start();

        setFocusable(true);
        addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_UP)
                    dirPressed[0] = true;
                else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    dirPressed[1] = true;
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    dirPressed[2] = true;
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    dirPressed[3] = true;
                }


            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP)
                    dirPressed[0] = false;
                else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    dirPressed[1] = false;
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    dirPressed[2] = false;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    dirPressed[3] = false;
                }
            }

            public void keyTyped(KeyEvent e) { /* ... */ }
        });

    }

    protected void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.yellow);
        g.fillArc(x, y, 20, 20, 0, 360);

    }

    private class AdjustPositionTimer implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {

            if(dirPressed[0])
                y -= 2;

            if (dirPressed[1])
                y += 2;

            if (dirPressed[2])
                x -= 2;

            if (dirPressed[3])
                x += 2;

            repaint();


        }
    }
}