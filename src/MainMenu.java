import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JPanel {

    private Font title;
    private FontMetrics f_title;

    private int mouse_x, mouse_y;
    int w = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
            h = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    public MainMenu() {
        try {
            //create the font to use. Specify the size!
            //Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("MATURASC.ttf")).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("MATURASC.ttf")));
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }
        //create the font

        //title = new Font("Matura MT Script Capitals", Font.BOLD,h / 10);
        title = new Font("MATURASC", Font.BOLD,h / 10);
        f_title =  getFontMetrics(title);

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {

                mouse_x = e.getX();
                mouse_y = e.getY();

            }

        });

    }

    protected void paintComponent(Graphics g) {

        //background
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        //title
        g.setColor(Color.yellow);
        g.setFont(title);
        g.drawString("Space Trail", (getWidth()  - f_title.stringWidth("Space Trail")) / 2, getHeight() / 3);

        //button
        //g.fillRect();


    }
}
