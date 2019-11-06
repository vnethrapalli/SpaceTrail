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
import java.util.Random;

public class FishingPanel extends JPanel {

    public static void main(String[] args) {
        JFrame fishing = new JFrame();
        fishing.setTitle("Fishing");
        fishing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fishing.setResizable(false);
        fishing.setSize(2 * (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3 , 4 * (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5);
        fishing.add(new FishingPanel());
        fishing.setLocationRelativeTo(null);
        fishing.setVisible(true);
    }

    private Image backdrop;
    private final int BOBBERRADIUS = 20;
    private int PLBASESTRENGTH = 6;
    private int bobberXShift, bobberYShift;
    private boolean dirPressed[] = new boolean[4]; // up, down, left, right
    private Timer playerMove = new Timer(70, new PlayerMovementIntervalTimer());
    private Random r = new Random();

    private boolean isPlaying;


    public FishingPanel() {

        try {
            backdrop = ImageIO.read(new File("sprites/spaceBackdrop.png"));
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

    protected void paintComponent(Graphics g) {
        g.drawImage(backdrop, 0, 0, null, null);
        if (!isPlaying) {
            isPlaying = true;
            Fish fish = new Fish();
        }

        g.setColor(Color.red);
        g.fillArc((getWidth() - BOBBERRADIUS) / 2 + bobberXShift,  (getHeight() - BOBBERRADIUS) / 2 + bobberYShift, BOBBERRADIUS, BOBBERRADIUS, 0, 360);

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

            repaint();
        }

    }

    private class Fish {

        private int sizeOption; // 3 sizes: S, M, L
        private String type;
        private final String[] SPECIES = {}; // 3 sprites maybe, for different species
        private int pullStrength;
        private Timer switchDirection = new Timer(60, new MovementIntervalTimer());

        private Random r;

        public Fish() {
            r = new Random();
            sizeOption = r.nextInt(3); // correspond to size option
            //type = SPECIES[r.nextInt(SPECIES.length)];
            pullStrength = r.nextInt(2) + 3;
            System.out.println(pullStrength);
            switchDirection.start();

        }

        private class MovementIntervalTimer implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
                int dx = 0, dy = 0;

                if (bobberXShift <= 0)
                    dx = (int) (Math.cos((Math.PI * r.nextDouble() + Math.PI / 2)) * pullStrength);

                else
                    dx = (int)(Math.cos((Math.PI * r.nextDouble() - Math.PI / 2 )) * pullStrength);

                if (bobberYShift <= 0)
                    dy = -(int) Math.sqrt(pullStrength * pullStrength - dx * dx);

                else
                    dy = (int) Math.sqrt(pullStrength * pullStrength - dx * dx);


                bobberXShift += dx;
                bobberYShift += dy;

            }

        }

    }
}
