package testers_and_runners;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Node {
    public final int ROW_LENGTH=4;
    private String[] text;
    private String[][] answers;
    public static String FILE;


    public Node(String file) {
        FILE=file;
        try {
            loadfile();
        }catch (IOException ex){
            System.out.println("oops");
        }

    }

    private void loadfile() throws IOException {
        //https://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/
        BufferedReader in = new BufferedReader(new FileReader(FILE));
        this.text= in.readLine().split(" ");
        String num=in.readLine();
        int number=Integer.parseInt(num);
        answers=new String[number][ROW_LENGTH];
        for (int i = 0; i < number; i++) {
            String line = in.readLine();
            String[] parts=line.split("_");
            for (int j = 0; j <ROW_LENGTH ; j++) {
                answers[i][j]=parts[j];
            }
        }


    }
    public String[] getText(){
        return text;
    }
    public String[][] getAns(){
        return answers;
    }


    private class NodePanel extends JPanel {

        protected void paintComponent(Graphics g) {
            g.setColor(Color.blue);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}


