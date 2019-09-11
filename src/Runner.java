import javax.swing.*;
import java.awt.*;

public class Runner {


    public static void main(String[] args) {

        JFrame screen = new JFrame();
        screen.add(new MainMenu());

        screen.setSize(2 * (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3 , 4 * (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5);
        //https://stackoverflow.com/questions/9706097/panel-size-changes-from-computer-to-computer

        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setLocationRelativeTo(null);
        screen.setVisible(true);
    }
}
