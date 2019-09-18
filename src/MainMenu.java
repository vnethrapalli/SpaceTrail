import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends JPanel {

    private Font title;
    private FontMetrics f_title;

    private int mouse_x, mouse_y;
    int w = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
            h = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    public MainMenu() {
        title = new Font("Matura MT Script Capitals", Font.BOLD,h / 10);
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
