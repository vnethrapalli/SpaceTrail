import Minigames.MicroWars;

import java.awt.*;
import java.awt.event.*;
public class MouseXY extends Frame implements MouseListener, MouseMotionListener
//https://way2java.com/awt-events/finding-x-y-coordinates-mouse-position-java/
{
    private int startx, starty;
    private int x,y;
    String str="";
    private MicroWars microwar;
    public MouseXY(MicroWars m)
    {
        microwar=m;
        addMouseListener(this);
        addMouseMotionListener(this);

        setSize(300, 300);
        setVisible(true);
    }
    // override MouseListener five abstract methods
    public void mousePressed(MouseEvent e)
    {
        startx = e.getX();
        starty = e.getY();
    }
    public void mouseReleased(MouseEvent e)
    {
        int endx=e.getX();
        int endy=e.getY();
        microwar.select(startx,starty,endx,endy);
    }
    public void mouseClicked(MouseEvent e)
    {
        x = e.getX();
        y = e.getY();
        microwar.setgoto(x,y);

    }
    public void mouseEntered(MouseEvent e)
    {
        x = e.getX();
        y = e.getY();

    }
    public void mouseExited(MouseEvent e)
    {
        x = e.getX();
        y = e.getY();

    }
    // override MouseMotionListener two abstract methods
    public void mouseMoved(MouseEvent e)
    {
        x = e.getX();
        y = e.getY();

    }
    public void mouseDragged(MouseEvent e)
    {
        x = e.getX();
        y = e.getY();

    }
}