import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Runner {

    public static void main(String[] args) {

        JFrame screen = new JFrame();
        screen.add(new MainMenu());


        screen.setSize(2 * (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3 , 4 * (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5);
        //https://stackoverflow.com/questions/9706097/panel-size-changes-from-computer-to-computer

        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setResizable(false);
        screen.setTitle("Space Trail");
        screen.setLocationRelativeTo(null);
        screen.setVisible(true);
        save(new Node("test.txt"));
        try {
            System.out.println(load().FILE);
        }catch (Exception e){
            System.out.println("cant load");
        }

    }
    public static void save(Node curnode){
        //https://www.javatpoint.com/java-filewriter-class
        try{
            FileWriter fw=new FileWriter("savefile");
            fw.write(curnode.FILE);
            fw.close();
        }catch(Exception e){System.out.println(e);}
        System.out.println("Success...");
    }
    public static Node load() throws Exception{
            BufferedReader in = new BufferedReader(new FileReader("savefile"));
            return new Node(in.readLine());

    }
}
