import javax.imageio.ImageIO;
import javax.swing.*;
        import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class Shop extends JPanel {
    public int[] supplies = {1,1,1,1,1,1};
    private int mouse_x, mouse_y;
    private Font title;
    private FontMetrics f_title;
    private Image triangle;

    public Shop() {

        addMouseMotionListener(new MouseAdapter() {
                        @Override
            public void mouseMoved(MouseEvent e) {

                mouse_x = e.getX();
                mouse_y = e.getY();

                repaint();

            }

        });
        Font customFont=null;
        try {
            //create the font to use. Specify the size!
            //https://www.dafontfree.net/freefonts-matura-mt-script-capitals-f65093.htm
            //https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("MATURASC.ttf"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();

        }
        title = customFont;
        try {
            //triangle image
            triangle = ImageIO.read(new File("Green_triangle.png"));
        }catch (IOException e){
            System.out.println("no");
        }

    }
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(242,198,102));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(230,94,9));
        //buttons x difference is 1/10
        //https://commons.wikimedia.org/wiki/File:Green_triangle.svg
        g.fillRect(  getWidth() / 6, getHeight() / 4, getWidth() / 12, getHeight() / 20);
        g.fillRect(  2*getWidth() / 3, getHeight() / 4, getWidth() / 12, getHeight() / 20);
        g.fillRect(  5*getWidth() / 6, getHeight() / 4, getWidth() / 12, getHeight() / 20);


        g.fillRect(  getWidth() / 6, 7*getHeight() / 20, getWidth() / 12, getHeight() / 20);
        g.fillRect(  2*getWidth() / 3, 7*getHeight() / 20, getWidth() / 12, getHeight() / 20);
        g.fillRect(  5*getWidth() / 6, 7*getHeight() / 20, getWidth() / 12, getHeight() / 20);

        g.fillRect(  getWidth() / 6, 9*getHeight() / 20, getWidth() / 12, getHeight() / 20);
        g.fillRect(  2*getWidth() / 3, 9*getHeight() / 20, getWidth() / 12, getHeight() / 20);
        g.fillRect(  5*getWidth() / 6, 9*getHeight() / 20, getWidth() / 12, getHeight() / 20);

        g.fillRect(  getWidth() / 6, 11*getHeight() / 20, getWidth() / 12, getHeight() / 20);
        g.fillRect(  2*getWidth() / 3, 11*getHeight() / 20, getWidth() / 12, getHeight() / 20);
        g.fillRect(  5*getWidth() / 6, 11*getHeight() / 20, getWidth() / 12, getHeight() / 20);

        g.fillRect(  getWidth() / 6, 13*getHeight() / 20, getWidth() / 12, getHeight() / 20);
        g.fillRect(  2*getWidth() / 3, 13*getHeight() / 20, getWidth() / 12, getHeight() / 20);
        g.fillRect(  5*getWidth() / 6, 13*getHeight() / 20, getWidth() / 12, getHeight() / 20);

        g.fillRect(  getWidth() / 6, 3*getHeight() / 4, getWidth() / 12, getHeight() / 20);
        g.fillRect(  2*getWidth() / 3, 3*getHeight() / 4, getWidth() / 12, getHeight() / 20);
        g.fillRect(  5*getWidth() / 6, 3*getHeight() / 4, getWidth() / 12, getHeight() / 20);

        g.setColor(Color.BLACK);
        title=title.deriveFont(Font.BOLD,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/15);
        f_title = getFontMetrics(title);
        g.setFont(title);
        g.drawString("Shop",0,f_title.getAscent());

        title=title.deriveFont(Font.PLAIN,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/20);
        f_title = getFontMetrics(title);
        g.setFont(title);
        g.drawString("Choose what ye want",(getWidth())/3,getHeight()/5);



    }

}
