import Minigames.*;
import Minigames.MicroWars;
import Util.Character;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class NodLoder extends JPanel {
    private Font title;
    private FontMetrics f_title;
    private ArrayList<Rectangle2D.Double> rectList = new ArrayList<>();
    private int mouse_x, mouse_y;
    private Node node;
    private String wide;
    private String[][] answers;
    private int index=0;
    private boolean win;
    private String nextnode;
    private JFrame myFrame;

    public NodLoder(String filename, JFrame frame) {
        node=new Node(filename);
        myFrame=frame;
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
                for(int i =0;i<rectList.size();i++) { //for loop in same format as paint component
                    if(rectList.get(i).contains(new Point(mouse_x,mouse_y))) {
                        index = i;
                        win = runMini(answers[index][1]);
                        if (win) {
                            nextnode = answers[index][2];
                            System.out.println("win");
                        }
                        if (!win) {
                            nextnode = answers[index][3];
                            System.out.println("ose");
                        }
                    }
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
            rect.setRect(getWidth()/20,(i*2+9)*getHeight()/20, f_title.getStringBounds((i+1)+". "+answers[i][0],g).getWidth(),f_title.getStringBounds((i+1)+". "+answers[i][0],g).getHeight());
            rectList.add(rect);

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
    private boolean runMini(String miniName){

            if (miniName.equals("Asteroids")){
                JFrame asteroid = new JFrame();
                asteroid.setTitle("Asteroids");
                asteroid.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                asteroid.setResizable(false);
                asteroid.setSize(2 * (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3 , 4 * (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5);
                asteroid.add(new Asteroids());
                asteroid.setLocationRelativeTo(null);
                asteroid.setVisible(true);
                return false;

            }
            else if (miniName.equals("Wars")){
                MicroWars m =new MicroWars(10,500,400,3,2,3,4,3);
                JFrame dumbGame = new JFrame();
                dumbGame.setTitle("DumbGame");
                dumbGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dumbGame.setResizable(false);
                dumbGame.setSize(2 * (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3 , 4 * (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5);
                dumbGame.add(m);
                dumbGame.setLocationRelativeTo(null);
                dumbGame.setVisible(true);
                m.play();
//                Wars m=new Wars(10,500,400,3,2,3,4,3);
//                myFrame.removeAll();
//                myFrame.validate();
//                myFrame.repaint();
//                myFrame.add(m.paint);
//                myFrame.validate();
//                myFrame.setVisible(true);
//
//                m.play();
//                m.winner();
//                Wars m=new Wars(10,500,400,3,2,3,4,3);
//                m.play();
//                return m.winner();
            }
            else if (miniName.equals("Shop")){
                Shop s=new Shop(new Character(false,1));
                return s.winner();

            }
            return false;

    }

}
