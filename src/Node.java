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
    }
    private void loadfile(String file) throws FileNotFoundException {
        //https://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/
        BufferedReader in = new BufferedReader(new FileReader(file));
    }
    private class NodePanel extends JPanel {

        protected void paintComponent(Graphics g) {

        }
    }
}


