import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {

    protected void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
