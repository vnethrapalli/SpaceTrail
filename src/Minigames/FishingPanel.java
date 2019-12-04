package Minigames;

import testers_and_runners.NodLoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class FishingPanel extends JPanel implements MiniGame {

    private Image backdrop, fishingrod, reverseFishingrod;
    private final int BOBBERRADIUS = 20;
    private int PLBASESTRENGTH = 8;
    private int bobberXShift, bobberYShift;
    private boolean dirPressed[] = new boolean[4]; // up, down, left, right
    private Timer playerMove = new Timer(80, new PlayerMovementIntervalTimer());
    private Random r = new Random();

    private boolean isPlaying, drawLeftFishingRod, drawRightFishingRod;
    Fish fish;

    //each round stats
    private double secondsOutOfBounds = 0;
    private final double MARGINPROPORTION = 0.25;
    private long gameStartTime, exitstartTime;
    private boolean exited;

    private boolean win;
    private NodLoder nl;


    public FishingPanel(NodLoder n) {
        nl = n;

        try {
            backdrop = ImageIO.read(new File("sprites/spaceBackdrop.png"));
            fishingrod = ImageIO.read(new File("sprites/fishingrod.png"));
            reverseFishingrod = ImageIO.read(new File("sprites/fishingrodreverse.png"));
        } catch (IOException ex) {

        }

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
                else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                    dirPressed[1] = false;
                else if (e.getKeyCode() == KeyEvent.VK_LEFT)
                    dirPressed[2] = false;
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    dirPressed[3] = false;
            }

            public void keyTyped(KeyEvent e) { /* ... */ }
        });

        playerMove.start();
    }

    public void play() {
        if (!isPlaying) {
            isPlaying = true;
            fish = new Fish();
            gameStartTime = System.currentTimeMillis();
        }
    }

    public Boolean winner() {
        if (isPlaying)
            return null;

        return win;
    }

    public void end() {
        playerMove.stop();
        fish.stopTimers();
        nl.winMini(win);
    }

    protected void paintComponent(Graphics g) {
        g.drawImage(backdrop, 0, 0, null, null);


        g.setColor(Color.red);
        g.fillArc((getWidth() - BOBBERRADIUS) / 2 + bobberXShift,  (getHeight() - BOBBERRADIUS) / 2 + bobberYShift, BOBBERRADIUS, BOBBERRADIUS, 0, 360);

        if (drawRightFishingRod)
            g.drawImage(fishingrod, bobberXShift + 195, bobberYShift + 280, null, null);

        if (drawLeftFishingRod)
            g.drawImage(reverseFishingrod, bobberXShift + 110, bobberYShift + 280, null, null);

        //update time
        g.setColor(Color.yellow);
        g.drawRect((int)(getWidth() * MARGINPROPORTION), (int)(getHeight() * MARGINPROPORTION), (int)((1 - 2 * MARGINPROPORTION) * getWidth()), (int)((1 - 2 * MARGINPROPORTION) * getHeight()));

        //keep track of the time outofbounds
        if (!exited && (bobberXShift + getWidth() / 2 < getWidth() * MARGINPROPORTION || bobberXShift  + getWidth() / 2 > (1 - MARGINPROPORTION) * getWidth()
                || bobberYShift + getHeight() / 2 < getHeight() * MARGINPROPORTION || bobberYShift + getHeight() / 2 > (1 - MARGINPROPORTION) * getHeight())) {
            exitstartTime = System.currentTimeMillis();
            exited = true;
        }

        if (exited && exitstartTime != 0) {
            if ((System.currentTimeMillis() - exitstartTime) / 1000 + secondsOutOfBounds >= 5.000) {
                win = false;
                end();
            }
            if (bobberXShift + getWidth() / 2 > getWidth() * MARGINPROPORTION && bobberXShift  + getWidth() / 2 < (1 - MARGINPROPORTION) * getWidth()
                    && bobberYShift + getHeight() / 2 > getHeight() * MARGINPROPORTION && bobberYShift + getHeight() / 2 < (1 - MARGINPROPORTION) * getHeight()) {
                secondsOutOfBounds += (System.currentTimeMillis() - exitstartTime) / 1000.0;
                exitstartTime = 0;
                System.out.println(secondsOutOfBounds);
                exited = false;
            }
        }

        //win condition: gametime 30 seconds, and if its out of bounds for less than 6 seconds
        if ((System.currentTimeMillis() - gameStartTime) / 1000.0 >= 30) {
            //some code
            //draw the random fish sprite
            //you win!!

            win = true;
            end();

        }


    }


    private class PlayerMovementIntervalTimer implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {

            int str = PLBASESTRENGTH + r.nextInt(5);

            if (dirPressed[0] && !dirPressed[1] && !dirPressed[2] && !dirPressed[3])
                bobberYShift -= str;

            else if (!dirPressed[0] && dirPressed[1] && !dirPressed[2] && !dirPressed[3])
                bobberYShift += str;

            else if (!dirPressed[0] &&!dirPressed[1] && dirPressed[2] && !dirPressed[3])
                bobberXShift -= str;

            else if (!dirPressed[0] &&!dirPressed[1] && !dirPressed[2] && dirPressed[3])
                bobberXShift += str;

            else if (dirPressed[0] && dirPressed[2]) {
                bobberXShift -= (int)Math.sqrt(str);
                bobberYShift -= (int)Math.sqrt(str);
            }

            else if (dirPressed[0] && dirPressed[3]) {
                bobberXShift += (int)Math.sqrt(str);
                bobberYShift -= (int)Math.sqrt(str);
            }

            else if (dirPressed[1] && dirPressed[2]) {
                bobberXShift -= (int)Math.sqrt(str);
                bobberYShift += (int)Math.sqrt(str);
            }

            else if (dirPressed[1] && dirPressed[3]) {
                bobberXShift += (int)Math.sqrt(str);
                bobberYShift += (int)Math.sqrt(str);
            }

            if (bobberXShift > 0) {
                drawRightFishingRod = true;
                drawLeftFishingRod = false;
            }
            else {
                drawLeftFishingRod = true;
                drawRightFishingRod = false;
            }
            repaint();
        }

    }

    private class Fish {

        private int sizeOption; // 3 sizes: S, M, L
        private String type;
        private final String[] SPECIES = {}; // 3 sprites maybe, for different species
        private int pullStrength, dx, dy;

        private Timer switchDirection = new Timer(1200, new MovementIntervalTimer());
        private Timer moveFish = new Timer(40, new FishMovement());

        private Random r;

        public Fish() {
            r = new Random();
            sizeOption = r.nextInt(3); // correspond to size option
            //type = SPECIES[r.nextInt(SPECIES.length)];
            pullStrength = r.nextInt(6) + 5;

            switchDirection.start();
            moveFish.start();

        }

        private class MovementIntervalTimer implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
                double angle = r.nextDouble() * Math.PI * 2;
                dx = (int) (Math.cos(angle) * pullStrength);
                dy = (int) (Math.sin(angle) * pullStrength);

            }

        }

        public void stopTimers() {
            switchDirection.stop();
            moveFish.stop();
        }

        private class FishMovement implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
                 bobberXShift += dx;
                 bobberYShift += dy;

            }

        }

    }

}
