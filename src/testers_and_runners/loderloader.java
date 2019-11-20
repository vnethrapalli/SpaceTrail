package testers_and_runners;

import javax.swing.*;
import java.awt.*;

public class loderloader extends JFrame {
    public NodLoder nl;
    public void changenode(String nod){
        if(nod==null){
            System.out.println("game over");
        }
        nl= new NodLoder(nod,this);
        this.removeAll();
        this.add(nl);
    }

    public static void main(String[] args) {


    }
}
