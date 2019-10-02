package Minigames;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Asteroids extends JPanel {

    private final int PLAYERDIAMETER = 36;
    private final int MAXASTEROIDS = 100;
    private int x, y;
    private int transparency = 0;
    private long startTime = System.currentTimeMillis();

    private Timer pos;
    private Timer aster;
    private Timer fade;

    private boolean dirPressed[] = new boolean[4]; // up, down, left, right
    private boolean startValues = true;
    private boolean isAlive = true;

    private ArrayList<Rock> asteroids = new ArrayList<Rock>();

    private Image backdrop;


    public Asteroids() {

        try {
            backdrop = ImageIO.read(new File("sprites/spaceBackdrop.png"));
        } catch (IOException ex) {

        }

        pos = new Timer(20, new AdjustPositionTimer());
        aster = new Timer(500, new NewAsteroidTimer());
        fade = new Timer(100, new FadeOutTimer());
        pos.start();
        aster.start();

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

        if (startValues) {
            x = (getWidth() - PLAYERDIAMETER) / 2;
            y = (getHeight() - PLAYERDIAMETER) / 2;

            startValues = false;
        }

        if (isAlive) {

            g.drawImage(backdrop, 0, 0, null, null);

            g.setColor(Color.yellow);
            g.fillArc(x, y, PLAYERDIAMETER, PLAYERDIAMETER, 0, 360);

            g.setColor(Color.red);
            for (int i = 0; i < asteroids.size(); i++) {
                Rock rock = asteroids.get(i);
                g.fillRect(rock.rx, rock.ry, rock.size, rock.size);

                if (i % 2 == 0) { //some go to the right
                    rock.rx += rock.speed;
                    rock.ry += rock.speed * rock.slope;
                }
                else {
                    rock.rx -= rock.speed; // some go to the left
                    rock.ry -= rock.speed * rock.slope;
                }
            }
        }

        else  {
            g.setColor(new Color(0, 0, 0, transparency));
            g.fillRect(0, 0, getWidth(), getHeight());
        }


    }

    private class AdjustPositionTimer implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {

            if(dirPressed[0] && y >= 0)
                y -= 3;

            if (dirPressed[1] && y + PLAYERDIAMETER <= getHeight())
                y += 3;

            if (dirPressed[2] && x >= 0)
                x -= 3;

            if (dirPressed[3] && x + PLAYERDIAMETER <= getWidth())
                x += 3;

            for (int i = 0; i < asteroids.size(); i++) { // collision math
                Rock rock = asteroids.get(i);
                if (Math.pow(rock.rx - (x + PLAYERDIAMETER / 2),2) + Math.pow(rock.ry - (y + PLAYERDIAMETER / 2), 2) < PLAYERDIAMETER * PLAYERDIAMETER / 4 || // top left corner
                        Math.pow(rock.rx + rock.size - (x + PLAYERDIAMETER / 2),2) + Math.pow(rock.ry - (y + PLAYERDIAMETER / 2), 2) < PLAYERDIAMETER * PLAYERDIAMETER / 4|| // top right corner
                        Math.pow(rock.rx - (x + PLAYERDIAMETER / 2),2) + Math.pow(rock.ry + rock.size - (y + PLAYERDIAMETER / 2), 2) < PLAYERDIAMETER * PLAYERDIAMETER / 4 || // bottom left corner
                        Math.pow(rock.rx + rock.size - (x + PLAYERDIAMETER / 2),2) + Math.pow(rock.ry + rock.size - (y + PLAYERDIAMETER / 2), 2) < PLAYERDIAMETER * PLAYERDIAMETER / 4 || // bottom right corner
                        Math.pow(rock.rx + rock.size - (x + PLAYERDIAMETER / 2),2) + Math.pow(rock.ry + rock.size / 2 - (y + PLAYERDIAMETER), 2) < PLAYERDIAMETER * PLAYERDIAMETER / 4 || // right center
                        Math.pow(rock.rx + rock.size / 2 - (x + PLAYERDIAMETER / 2),2) + Math.pow(rock.ry - (y + PLAYERDIAMETER / 2), 2) < PLAYERDIAMETER * PLAYERDIAMETER / 4|| // top center
                        Math.pow(rock.rx - (x + PLAYERDIAMETER / 2),2) + Math.pow(rock.ry + rock.size / 2 - (y + PLAYERDIAMETER / 2), 2) < PLAYERDIAMETER * PLAYERDIAMETER / 4 || // left center
                        Math.pow(rock.rx + rock.size / 2 - (x + PLAYERDIAMETER / 2),2) + Math.pow(rock.ry + rock.size - (y + PLAYERDIAMETER / 2), 2) < PLAYERDIAMETER * PLAYERDIAMETER / 4) { //bottom center

                    isAlive = false;
                    pos.stop();
                    aster.stop();
                    fade.start();
                    System.out.println("You lived " + (System.currentTimeMillis() - startTime) / 1000.0 + " seconds!");
                }
            }

            if (isAlive)
                repaint();


        }
    }

    private class NewAsteroidTimer implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            if (asteroids.size() < MAXASTEROIDS)
                asteroids.add(new Rock());

            for (int i = 0; i < asteroids.size(); i++) {
                if (!asteroids.get(i).isWithinBounds())
                    asteroids.set(i, new Rock());
            }
        }
    }

    private class FadeOutTimer implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            if (transparency < 255)
                transparency += 5;

            repaint();
        }
    }

    private class Rock {

        public int rx;
        public int ry;
        public int size;
        public int slope;
        public int speed;

        public Rock() {
            Random r = new Random();
            size = r.nextInt(30) + 15;
            slope = (int)(5 * r.nextDouble() - 5 * r.nextDouble());
            speed = r.nextInt(3) + 1;

            rx = r.nextInt(getWidth());
            ry = r.nextInt(getHeight());
            while(Math.abs(x - rx) < 50 || Math.abs(y - ry) < 50) {
                rx = r.nextInt(getWidth());
                ry = r.nextInt(getHeight());
            }

        }

        public boolean isWithinBounds() {
            return rx >= 0 && rx <= getWidth() && ry >= 0 && ry <= getHeight();
        }
    }
}