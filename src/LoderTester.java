import javax.swing.*;
import java.awt.*;

public class LoderTester {
    public static void main(String[] args) {
        NodLoder nod=new NodLoder("test.txt");
        JFrame Loder = new JFrame();
        Loder.setTitle("Nod");
        Loder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Loder.setResizable(false);
        Loder.pack();
        Loder.setSize(2 * (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3 , 4 * (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5);
        Loder.add(new NodLoder("test.txt"));
        Loder.setLocationRelativeTo(null);
        Loder.setVisible(true);

    }
}
