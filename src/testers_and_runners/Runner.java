package testers_and_runners;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Runner {
    public static JFrame screen;

    public static void main(String[] args) {
        screen = new MainMenu();

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
