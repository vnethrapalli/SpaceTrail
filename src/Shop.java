import javax.imageio.ImageIO;
import javax.swing.*;
        import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Shop extends JPanel {
    public int[] supplies = {1,1,1,1,1,1};
    private int mouse_x, mouse_y;
    private Font title;
    private FontMetrics f_title;
    private Image Ltriangle;
    private Image Rtriangle;
    public String[] supplyName = {"Thing 1","Thing 2","Thing 2","Thing 3","Thing 4","Big thing"};
    public int[] suppliesPrice = {2,3,100,5,4,2};


    public Shop() {

        addMouseMotionListener(new MouseAdapter() {
                        @Override
            public void mouseMoved(MouseEvent e) {

                mouse_x = e.getX();
                mouse_y = e.getY();

                repaint();

            }

        });
        Font customFont=null;
        try {
            //create the font to use. Specify the size!
            //https://www.dafontfree.net/freefonts-matura-mt-script-capitals-f65093.htm
            //https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("MATURASC.ttf"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();

        }
        title = customFont;
        try {
            //triangle image
            Rtriangle = rotateR(ImageIO.read(new File("Green_triangle.png")));
            Ltriangle = rotateL(ImageIO.read(new File("Green_triangle.png")));

        }catch (IOException e){
            System.out.println("no");
        }
        }

    protected void paintComponent(Graphics g) {
        g.setColor(new Color(242,198,102));
        g.fillRect(0, 0, getWidth(), getHeight());


        g.setColor(Color.BLACK);
        title=title.deriveFont(Font.BOLD,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/15);
        f_title = getFontMetrics(title);
        g.setFont(title);
        g.drawString("Shop",0,f_title.getAscent());

        title=title.deriveFont(Font.PLAIN,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/20);
        f_title = getFontMetrics(title);
        g.setFont(title);
        g.drawString("Choose what ye want",(getWidth())/3,getHeight()/5);

        //buttons x difference is 1/10
        //https://commons.wikimedia.org/wiki/File:Green_triangle.svg
        title=title.deriveFont(Font.PLAIN,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/25);
        f_title = getFontMetrics(title);
        g.setFont(title);

        for(int i =5;i<=15;i+=2) {
            g.setColor(new Color(230,94,9));
            g.fillRect(getWidth() / 6, i*getHeight() / 20, getWidth() / 12, getHeight() / 20);
            g.fillRect(2 * getWidth() / 3, i*getHeight() / 20, getWidth() / 12, getHeight() / 20);
            g.fillRect(5 * getWidth() / 6, i*getHeight() / 20, getWidth() / 12, getHeight() / 20);

            //other image option
            // g.drawImage(Ltriangle, getWidth() / 6, (i*getHeight() / 20)+(getHeight()/100), getWidth() / 15, getHeight() / 30, null);
            g.drawImage(Ltriangle, getWidth() / 6, i*getHeight() / 20, getWidth() / 12, getHeight() / 20, null);
            g.drawImage(Rtriangle, 2 * getWidth() / 3, i*getHeight() / 20, getWidth() / 12, getHeight() / 20, null);

            g.setColor(Color.BLACK);
            g.drawString(supplyName[(i-5)/2]+"($"+suppliesPrice[(i-5)/2]+")",(getWidth())/3,i*getHeight()/20+f_title.getAscent()-getHeight()/100);


        }



    }
    //https://stackoverflow.com/questions/8639567/java-rotating-images
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

}
