package Minigames;

import Minigames.MicroWars;

import java.awt.*;
import java.awt.event.*;
public class MouseXY implements MouseListener, MouseMotionListener
//https://way2java.com/awt-events/finding-x-y-coordinates-mouse-position-java/
{
    private int startx, starty;
    private int x,y;
    String str="";
    private MicroWars microwar;
    public MouseXY(MicroWars m)
    {
        microwar=m;
    }
    // override MouseListener five abstract methods
    public void mousePressed(MouseEvent e)
    {
        if(e.getButton()==MouseEvent.BUTTON1) {
            Point p =e.getPoint();
            startx = (int)p.getX();
            starty = (int)p.getY();
        }

    }
    public void mouseClicked(MouseEvent e)
    {
        if(e.getButton()!=MouseEvent.BUTTON1) {
            Point p = e.getPoint();
            x = (int)p.getX();
            y = (int)p.getY();
            microwar.setgoto(x, y);
        }

    }
    public void mouseReleased(MouseEvent e)
    {
        if(e.getButton()==MouseEvent.BUTTON1) {
            Point p = e.getPoint();
            int endx = (int)p.getX();
            int endy = (int)p.getY();
            microwar.select(startx, starty, endx, endy);
        }
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