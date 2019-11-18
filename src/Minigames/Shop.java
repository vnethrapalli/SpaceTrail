package Minigames;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import Util.Sound;
import Util.Character;


public class Shop extends JPanel implements MiniGame{
    public int[] supplies = {0,0,0,0,0,0}; //amount of supplies that will be bought
    private int mouse_x, mouse_y; //mouse positions x and y
    private Font font; //actual font
    private FontMetrics fontMetric; //dimensions of font
    private Image Ltriangle; //left facing triangle image
    private Image Rtriangle; //right facing triangle image
    public String[] supplyName = {"Thing 1","Thing 2","Thing 2","Thing 3","Thing 4","Big thing"}; //name of all supplies
    public int[] suppliesPrice = {2,3,100,5,4,2}; //price of all supplies
    public Sound music =new Sound(); //music player class
    public Timer animate=new Timer(0,new TimerListener()); //animater
    private Boolean end;


    public Shop(Character character) {
        //allows for tracking mouse location
        addMouseMotionListener(new MouseAdapter() {
                        @Override
            public void mouseMoved(MouseEvent e) {

                mouse_x = e.getX();
                mouse_y = e.getY();

            }

        });
        addMouseListener(new MouseAdapter() {
            @Override
            //actions on click
            public void mouseClicked(MouseEvent e) {
                for(int i =5;i<=15;i+=2) { //for loop in same format as paint component
                    if((mouse_x>=getWidth()/10&&mouse_x<=getWidth()/10+getWidth()/12)&&(mouse_y>=i*getHeight()/20&&mouse_y<=(i+1)*getHeight()/20)){ //decrement button position and dimensions checked
                        if(supplies[(i-5)/2]==0){ //overflow to 99 instead of negative
                        supplies[(i-5)/2]=99;
                        }
                        else{
                            supplies[(i-5)/2]--; //decrement 1
                        }

                    }
                    if((mouse_x>=4*getWidth()/9&&mouse_x<=4*getWidth()/9+getWidth()/12)&&(mouse_y>=i*getHeight()/20&&mouse_y<=(i+1)*getHeight()/20)){ //increase button position and dimensions checked
                        if(supplies[(i-5)/2]==99){ //overflow to 0 instead of 3 digit
                            supplies[(i-5)/2]=0;
                        }
                        else{
                            supplies[(i-5)/2]++; //increment 1
                        }

                    }

                }

            }
        });

        //custom font creation
        try {

            //https://www.dafontfree.net/freefonts-matura-mt-script-capitals-f65093.htm
            //https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
            font = Font.createFont(Font.TRUETYPE_FONT, new File("MATURASC.ttf"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();

        }


        try {
            //two triangle images used
            Rtriangle = rotateR(ImageIO.read(new File("Green_triangle.png")));
            Ltriangle = rotateL(ImageIO.read(new File("Green_triangle.png")));

        }catch (IOException e){
            System.out.println("no");
        }

        //music and timer start. Timer for repainting
        music.playShopTheme();

        }
    public void play(){
        animate.start();
    }
    public Boolean winner(){
        return end;
    }
    protected void paintComponent(Graphics g) {
        //background color and fill background
        g.setColor(new Color(242,198,102));
        g.fillRect(0, 0, getWidth(), getHeight());

        //set major texts in shop menu
        //Title
        g.setColor(Color.BLACK);
        font=font.deriveFont(Font.BOLD,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/15);
        fontMetric = getFontMetrics(font);
        g.setFont(font);
        g.drawString("Minigames.Shop",0,fontMetric.getAscent());

        //Banner
        font=font.deriveFont(Font.PLAIN,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/20);
        fontMetric = getFontMetrics(font);
        g.setFont(font);
        g.drawString("Choose what ye want",(getWidth())/3,getHeight()/7);

        //Subtitles
        font=font.deriveFont(Font.PLAIN,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/40);
        fontMetric = getFontMetrics(font);
        g.setFont(font);
        g.drawString("Amount",3*getWidth()/5-getWidth()/25,getHeight()/5);
        g.drawString("Total",7*getWidth()/10-getWidth()/50,getHeight()/5);

        //set font for for loop
        font=font.deriveFont(Font.PLAIN,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/25);
        fontMetric = getFontMetrics(font);
        g.setFont(font);

        for(int i =5;i<=15;i+=2) { // for is weird for spacing
            g.setColor(new Color(230,94,9)); // button color

            //buttons are added
            // buttons x difference is 1/10
            //https://commons.wikimedia.org/wiki/File:Green_triangle.svg
            g.fillRect(getWidth() / 10, i*getHeight() / 20, getWidth() / 12, getHeight() / 20);
            g.fillRect(4 * getWidth() / 9, i*getHeight() / 20, getWidth() / 12, getHeight() / 20);
            g.fillRect(5 * getWidth() / 6, i*getHeight() / 20, getWidth() / 12, getHeight() / 20);

            // add triangle image
            // other triangle option
            // g.drawImage(Ltriangle, getWidth() / 6, (i*getHeight() / 20)+(getHeight()/100), getWidth() / 15, getHeight() / 30, null);
            g.drawImage(Ltriangle, getWidth() / 10, i*getHeight() / 20, getWidth() / 12, getHeight() / 20, null);
            g.drawImage(Rtriangle, 4 * getWidth() / 9, i*getHeight() / 20, getWidth() / 12, getHeight() / 20, null);

            // add shop items amounts and cost
            g.setColor(Color.BLACK);
            g.drawString(supplyName[(i-5)/2]+"($"+suppliesPrice[(i-5)/2]+")",(getWidth())/5,i*getHeight()/20+fontMetric.getAscent()-getHeight()/100);
            g.drawString(Integer.toString(supplies[(i-5)/2]),3*getWidth()/5-getWidth()/25,i*getHeight()/20+fontMetric.getAscent()-getHeight()/100);
            g.drawString("$"+Integer.toString(supplies[(i-5)/2]*suppliesPrice[(i-5)/2]),7*getWidth()/10-getWidth()/50,i*getHeight()/20+fontMetric.getAscent()-getHeight()/100);

        }

    }

    //https://stackoverflow.com/questions/8639567/java-rotating-images
    //rotates triangle 90 degrees clockwise
    public static BufferedImage rotateL(BufferedImage img )
    {
        int width  = img.getWidth();
        int height = img.getHeight();
        BufferedImage   newImage = new BufferedImage( height, width, img.getType() );

        for( int i=0 ; i < width ; i++ )
            for( int j=0 ; j < height ; j++ )
                newImage.setRGB( height-1-j, i, img.getRGB(i,j) );

        return newImage;
    }

    //rotates triangle 90 degrees counter-clockwise
    public static BufferedImage rotateR(BufferedImage img )
    {
        int width  = img.getWidth();
        int height = img.getHeight();
        BufferedImage   newImage = new BufferedImage( height, width, img.getType() );

        for( int i=0 ; i < width ; i++ )
            for( int j=0 ; j < height ; j++ )
                newImage.setRGB( j, width-1-i, img.getRGB(i,j) );

        return newImage;
    }

    //Timer for repainting
    class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }
}
