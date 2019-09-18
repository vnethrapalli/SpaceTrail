import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;

public class Node {

    private String text;
    private String[] answers;
    private String file;
    public Node(String file) {
        this.file=file;
        loadfile(file);
    }
    private void loadfile(String file) {
        BufferedReader in = new BufferedReader();
    }
    private class NodePanel extends JPanel {

        protected void paintComponent(Graphics g) {

        }
    }
}


