package Minigames.HackerMan;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouselistener implements MouseListener {
    private HackerMan HM;
    public Mouselistener(HackerMan H){
        HM=H;
    }
    public void mouseClicked(MouseEvent e){
        HM.rotate(e.getX(),e.getY());
    }
    public void mousePressed(MouseEvent e){

    }
    public void mouseReleased(MouseEvent e){

    }
    public void mouseEntered(MouseEvent e){

    }
    public void mouseExited(MouseEvent e){

    }
}
