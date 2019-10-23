import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class NodLoder extends JPanel {
    private Font title;
    private FontMetrics f_title;

    private int mouse_x, mouse_y;
    private Node node;
    private String wide;
    private int y_pos;

    public NodLoder(String filename) {
        Font customFont=null;
        try {
            //create the font to use. Specify the size!
            //https://www.dafontfree.net/freefonts-matura-mt-script-capitals-f65093.htm
            //https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("MATURASC.ttf")).deriveFont(Font.BOLD,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/30);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();

        }
        //create the font

        //title = new Font("Matura MT Script Capitals", Font.BOLD,h / 10);

        title = customFont;
        f_title =  getFontMetrics(title);

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {

                mouse_x = e.getX();
                mouse_y = e.getY();



            }

        });
        node=new Node(filename);
        wide="";
    }
    public void draw(){
        repaint();
    }
    protected void paintComponent(Graphics g) {

        //background
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        //title
        g.setColor(Color.yellow);
        g.setFont(title);
        y_pos=1;

        for(int i=0;i<node.getText().length;i++){

            System.out.println(g.getFontMetrics().stringWidth(wide));
            System.out.println(getWidth());

            if(i!=node.getText().length-1&&g.getFontMetrics().stringWidth(wide+" "+ node.getText()[i+1])>getWidth()){
                g.drawString(wide,0,g.getFontMetrics().getAscent()+y_pos*getHeight()/20);
                wide="";
                y_pos+=2;

            }
            System.out.println(g.getFontMetrics().stringWidth(wide));
            System.out.println(getWidth());
            wide+=" ";
            wide+=node.getText()[i];
        }
        if(g.getFontMetrics().stringWidth(wide)<getWidth()){
            g.drawString(wide,0,y_pos);
        }
        wide="";

        //g.drawString(node.getText(),0,f_title.getAscent());

        //button
        g.fillRect(2 * getWidth() / 5, getHeight() / 2, getWidth() / 5, getHeight() / 20);


    }

}
