import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {

    private Font title = new Font("Matura MT Script Capitals", Font.BOLD,
            (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 10);

    private FontMetrics f_title = getFontMetrics(title);

    public MainMenu() {
        JButton start = new JButton("Start Adventure!");
    }

    protected void paintComponent(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.yellow);
        g.setFont(title);
        g.drawString("Space Trail", (getWidth()  - f_title.stringWidth("Space Trail")) / 2, getHeight() / 3);
    }
}
