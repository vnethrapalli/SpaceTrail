import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends JPanel {

    private Font title = new Font("Matura MT Script Capitals", Font.BOLD,
            (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 10);

    private FontMetrics f_title = getFontMetrics(title);

    private int mouse_x, mouse_y;

    public MainMenu() {
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {

                mouse_x = e.getX();
                mouse_y = e.getY();

            }

        });

    }

    protected void paintComponent(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.yellow);
        g.setFont(title);
        g.drawString("Space Trail", (getWidth()  - f_title.stringWidth("Space Trail")) / 2, getHeight() / 3);
    }
}
