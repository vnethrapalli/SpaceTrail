import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class NodLoder extends JPanel {
    private Font title;
    private FontMetrics f_title;

    private int mouse_x, mouse_y;
    private Node node;
    private String wide;
    private String[][] answers;

    public NodLoder(String filename) {
        node=new Node(filename);
        answers=node.getAns();
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
        addMouseListener(new MouseAdapter() {
            @Override
            //actions on click
            public void mouseClicked(MouseEvent e) {
                for(int i =5;i<=15;i+=2) { //for loop in same format as paint component


                }

            }
        });

        wide="";

    }
    public void draw(){
        repaint();
    }
    protected void paintComponent(Graphics g) {

        //background
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        g.drawRect(0,0,getWidth()-1,2*getHeight()/5);

        //title
        g.setColor(Color.yellow);
        g.setFont(title);
        int y_pos=1;

        for(int i=0;i<node.getText().length;i++){


            if(i!=node.getText().length-1&&g.getFontMetrics().stringWidth(wide+" "+ node.getText()[i+1])>getWidth()){
                g.drawString(wide,0,g.getFontMetrics().getAscent()+y_pos*getHeight()/20);
                wide="";
                y_pos+=2;

            }
            wide+=" ";
            wide+=node.getText()[i];
        }
        if(g.getFontMetrics().stringWidth(wide)<getWidth()){
            g.drawString(wide,0,g.getFontMetrics().getAscent()+y_pos*getHeight()/20);
        }
        wide="";

        for(int i=0;i<answers.length;i++) {


            //button
            g.setColor(Color.BLACK);
            //g.setColor(Color.MAGENTA);      Test string hitbox-
            Rectangle2D.Double rect =new Rectangle2D.Double();
            rect.setRect(f_title.getStringBounds((i+1)+". "+answers[i][0],g));
            g.fillRect(getWidth()/20,(i*2+9)*getHeight()/20, (int)rect.width,(int)rect.height);

            g.setColor(Color.YELLOW);
            g.drawString((i+1)+". "+answers[i][0],getWidth()/20,(i*2+10)*getHeight()/20-getHeight()/100);
/*            g.setColor(Color.yellow);
            this.setLayout(null);
            JLabel j1=new JLabel("BEEEEEEEEP");
            j1.setLocation(3*getWidth()/5,getHeight()/2);
            this.add(j1);

            j1.setForeground(Color.yellow);
*/

        }

    }

}
