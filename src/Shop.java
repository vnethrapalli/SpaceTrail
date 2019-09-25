import javax.swing.*;
        import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class Shop extends JPanel {
    public int[] supplies = {1,1,1,1,1,1};
    private int mouse_x, mouse_y;

    private FontMetrics f_title;
    protected void paintComponent(Graphics g) {
        Font customFont=null;
        try {
            //create the font to use. Specify the size!
            //https://www.dafontfree.net/freefonts-matura-mt-script-capitals-f65093.htm
            //https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("MATURASC.ttf")).deriveFont(Font.BOLD,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/20);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();

        }
        f_title=getFontMetrics(customFont);

        g.setColor(new Color(242,198,102));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(230,94,9));
        g.fillRect(  getWidth() / 6, getHeight() / 3, getWidth() / 12, getHeight() / 20);

    }
    public Shop() {
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {

                mouse_x = e.getX();
                mouse_y = e.getY();

                repaint();

            }

        });
    }
}
