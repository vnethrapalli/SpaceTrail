import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JPanel {


    private FontMetrics f_title;

    private int mouse_x, mouse_y;

    public MainMenu() {
        Font customFont=null;
        try {
            //create the font to use. Specify the size!
            //https://www.dafontfree.net/freefonts-matura-mt-script-capitals-f65093.htm
            //https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("MATURASC.ttf")).deriveFont(Font.BOLD,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/10);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();

        }
        //create the font

        //title = new Font("Matura MT Script Capitals", Font.BOLD,h / 10);

        f_title =  getFontMetrics(customFont);

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {

                mouse_x = e.getX();
                mouse_y = e.getY();

                repaint();

            }

        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if ((mouse_x >= 2 * getWidth() / 5 && mouse_x <= 3 * getWidth() / 5 ) && (mouse_y >= getHeight() / 2 && mouse_y <= 11 * getHeight() / 20)) {
                    JFrame shop = new JFrame();
                    shop.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    shop.setTitle("Shop!");
                    shop.add(new Shop());
                    shop.setSize(2 * (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3 , 4 * (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5);
                    shop.setLocationRelativeTo(null);
                    shop.setResizable(false);
                    shop.setVisible(true);

                }
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
        g.fillRect(2 * getWidth() / 5, getHeight() / 2, getWidth() / 5, getHeight() / 20);

    }
}