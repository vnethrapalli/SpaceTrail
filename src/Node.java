import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Node {

    private String text;
    private String[] answers;
    private String file;

    public Node(String file) {
        this.file=file;
        try {
            loadfile(file);
        }catch (FileNotFoundException ex){
            System.out.println("oops");
        }

        JFrame branch = new JFrame();
        branch.setSize(2 * (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3 , 4 * (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5);
        //https://stackoverflow.com/questions/9706097/panel-size-changes-from-computer-to-computer
        branch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        branch.setLocationRelativeTo(null);
        branch.add(new NodePanel());

    }
    private void loadfile(String file) throws FileNotFoundException {
        BufferedReader in = new BufferedReader(new FileReader(file));
    }
    private class NodePanel extends JPanel {

        protected void paintComponent(Graphics g) {
            g.setColor(Color.blue);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}


