import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Node {
    public final int ROW_LENGTH=4;
    private String text;
    private String[][] answers;
    private String file;

    public Node(String file) {
        this.file=file;
        try {
            loadfile(file);
        }catch (IOException ex){
            System.out.println("oops");
        }

        JFrame branch = new JFrame();
        branch.setSize(2 * (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3 , 4 * (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5);
        //https://stackoverflow.com/questions/9706097/panel-size-changes-from-computer-to-computer
        branch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        branch.setLocationRelativeTo(null);
        branch.add(new NodePanel());

    }

    private void loadfile(String file) throws IOException {
        //https://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/
        BufferedReader in = new BufferedReader(new FileReader(file));
        this.text=in.readLine();
        String num=in.readLine();
        int number=Integer.parseInt(num);
        answers=new String[number][ROW_LENGTH];
        for (int i = 0; i < number; i++) {
            String line = in.readLine();
            String[] parts=line.split(" ");
            for (int j = 0; j <ROW_LENGTH ; j++) {
                answers[i][j]=parts[j];
            }
        }

    }

    private class NodePanel extends JPanel {

        protected void paintComponent(Graphics g) {
            g.setColor(Color.blue);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}


