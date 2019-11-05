package Minigames.HackerMan;
import javax.imageio.ImageIO;
import javax.swing.*;
import Maze.*;
import Minigames.MiniGame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

//https://docs.oracle.com/javase/tutorial/2d/images/loadimage.html
public class HackerMan implements MiniGame {
    public Tile[][] tiles;
    public int[][] maze;
    public final int SCALE=20;
    public int width;
    public int height;
    private HackerManpanel paint = new HackerManpanel(this);
    private MazeGenerator mazegen;
    private boolean victory =false;
    public HackerMan(int width, int height){
        tiles=new Tile[width][height];
        MazeGenerator m = new MazeGenerator(width*2-1,height*2-1);
        maze=m.getMAZE();
        transfer(m);
        mazegen=m;
        this.width=width;
        this.height=height;

    }
    private void transfer(MazeGenerator m){
        for (int i = 0; i <tiles.length ; i++) {
            for (int j = 0; j <tiles[i].length ; j++) {
                int numsideopen=0;
                boolean bent=false;
                System.out.println(i+","+j+" new coord "+i*2+","+j*2);
                if((i*2+1)<maze.length&&maze[i*2+1][j*2]==0) {
                    numsideopen++;
                }
                if((i*2-1)>0&&maze[i*2-1][j*2]==0) {
                    numsideopen++;
                }
                if((j*2+1)<maze.length&&maze[i*2][j*2+1]==0) {
                    numsideopen++;
                }
                if((j*2-1)>0&&maze[i*2][j*2-1]==0) {
                    numsideopen++;
                }
                if(numsideopen==2){
                    if(((i*2-1)>0&&(i*2+1)<maze.length)&&maze[i*2+1][j*2]==0&&maze[i*2-1][j*2]==0) {
                        bent=true;
                    }
                    if(((j*2-1)>0&&(j*2+1)<maze.length)&&maze[i*2][j*2+1]==0&&maze[i*2][j*2-1]==0) {
                        bent=true;
                    }
                }
                if(bent){
                    numsideopen=5;
                }
                boolean start=false;
                boolean end=false;
                if(m.getStart()[0]/2==i&&m.getStart()[1]/2==j){
                    start=true;
                    numsideopen=4;
                }
                if(m.getEnd()[0]/2==i&&m.getEnd()[1]/2==j){
                    end=true;
                    numsideopen=4;
                }
                Random rand= new Random();
                tiles[i][j]=new Tile(SCALE,SCALE,numsideopen,rand.nextInt(4),start,end,i,j);
            }
        }
    }
    public void rotate(int x,int y){
        int x1 = (x-10)/100;
        int y1=(y-30)/100;
        System.out.println(x+","+y);
        System.out.println(x1+","+y1);
        tiles[x1][y1].rotate(1);
        for (Tile[] t1:tiles) {
            for (Tile t:t1) {
                t.explored=false;
            }
        }
        if(isSolved()){
            victory=true;
        }
    }
    private boolean isSolved(){
        int startx= mazegen.getStart()[0]/2;
        int starty=mazegen.getStart()[1]/2;
        return isConnected(tiles[startx][starty]);
    }
    private boolean isConnected(Tile t){
        if(t.explored){
            return false;
        }
        t.explored=true;
        if(t.end){
            return true;
        }
        if(t.type==0||t.type==1){
            return false;
        }
        for(Tile tile: getConnectedRooms(t)){
            if(isConnected(tile)){ // you stupid
                return true;
            }
        }
        return false;
    }
    private Tile[] getConnectedRooms(Tile t){ //fix this
        if(t.type==1||t.type==0){
            return null;
        }

        ArrayList<Tile>paths=new ArrayList<>();
        Tile[] possiblePaths = isfacing(t);
        for(Tile tile:possiblePaths){
            Tile[] otherTileFace = isfacing(tile);
            for (Tile returnTile:otherTileFace) {
                if(returnTile.equals(t)){
                    paths.add(tile);
                }
            }
        }
        Tile[]finalpaths=new Tile[paths.size()];
        paths.toArray(finalpaths);
        return finalpaths;
    }
    private Tile[] isfacing(Tile t){
        ArrayList<Tile> facing = new ArrayList<>();
        if(t.type==2){
            int badx=0;
            int bady=0;
            int badx2=0;
            int bady2=0;
            if(t.rotation==Tile.UP){
                badx=t.x-1;
                bady=t.y;
                badx2=t.x;
                bady2=t.y-1;
            }
            if(t.rotation==Tile.RIGHT){
                badx=t.x;
                bady=t.y-1;
                badx2=t.x+1;
                bady2=t.y;
            }
            if(t.rotation==Tile.DOWN){
                badx=t.x;
                bady=t.y+1;
                badx2=t.x+1;
                bady2=t.y;
            }
            if(t.rotation==Tile.LEFT){
                badx=t.x;
                bady=t.y+1;
                badx2=t.x-1;
                bady2=t.y;
            }
            for(int i=-1;i<=1;i++){
                for (int j = -1; j <=1 ; j++) {
                    if((j!=0&&i!=0)||(j==i)){
                        continue;
                    }
                    if((i+t.x==badx&&j+t.y==bady)||(i+t.x==badx2&&j+t.y==bady2)){
                        continue;
                    }
                    if(t.x+i>=0&&t.x+i<width&&t.y+j>=0&&t.y+j<height){
                        Tile nexttile=tiles[t.x+i][t.y+j];
                        facing.add(nexttile);
                    }
                }
            }
        }
        if(t.type==3){
            int badx=0;
            int bady=0;
            if(t.rotation==Tile.UP){
                badx=t.x+1;
                bady=t.y;
            }
            if(t.rotation==Tile.RIGHT){
                badx=t.x;
                bady=t.y+1;
            }
            if(t.rotation==Tile.DOWN){
                badx=t.x-1;
                bady=t.y;
            }
            if(t.rotation==Tile.LEFT){
                badx=t.x;
                bady=t.y-1;
            }
            for(int i=-1;i<=1;i++){
                for (int j = -1; j <=1 ; j++) {
                    if((j!=0&&i!=0)||(j==i)){
                        continue;
                    }
                    if(i+t.x==badx&&j+t.y==bady){
                        continue;
                    }
                    if(t.x+i>=0&&t.x+i<width&&t.y+j>=0&&t.y+j<height){
                        Tile nexttile=tiles[t.x+i][t.y+j];
                        facing.add(nexttile);
                    }
                }
            }
        }
        if(t.type==4){
            for(int i=-1;i<=1;i++){
                for (int j = -1; j <=1 ; j++) {
                    if((j!=0&&i!=0)||(j==i)){
                        continue;
                    }
                    if(t.x+i>=0&&t.x+i<width&&t.y+j>=0&&t.y+j<height){
                        Tile nexttile=tiles[t.x+i][t.y+j];
                        facing.add(nexttile);
                    }
                }
            }
        }
        if(t.type==5){
            int badx=0;
            int bady=0;
            int badx2=0;
            int bady2=0;
            if(t.rotation==Tile.UP||t.rotation==Tile.DOWN){
                badx=t.x-1;
                bady=t.y;
                badx2=t.x+1;
                bady2=t.y;
            }
            if(t.rotation==Tile.RIGHT||t.rotation==Tile.LEFT){
                badx=t.x;
                bady=t.y-1;
                badx2=t.x;
                bady2=t.y+1;
            }
            for(int i=-1;i<=1;i++){
                for (int j = -1; j <=1 ; j++) {
                    if((j!=0&&i!=0)||(j==i)){
                        continue;
                    }
                    if((i+t.x==badx&&j+t.y==bady)||(i+t.x==badx2&&j+t.y==bady2)){
                        continue;
                    }
                    if(t.x+i>=0&&t.x+i<width&&t.y+j>=0&&t.y+j<height){
                        Tile nexttile=tiles[t.x+i][t.y+j];
                        facing.add(nexttile);
                    }
                }
            }
        }
        Tile[] tilesfacing = new Tile[facing.size()];
        return facing.toArray(tilesfacing);
    }
    public void play() {
        Mouselistener m = new Mouselistener(this);
        JFrame game = new JFrame();
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setTitle("game");
        game.add(paint);
        game.setSize(width*100, height*100);
        game.setLocationRelativeTo(null);
        game.setVisible(true);
        game.addMouseListener(m);
        int time =0;
        while(time <1000&& (!victory)){
            try {
                Thread.sleep(100);
            }catch (Exception e){
                System.out.println("hi");
            }
            paint.repaint();
            time++;
        }
    }
    public boolean winner(){
        return victory;
    }
}
class HackerManpanel extends JPanel {
    private HackerMan HM;
    public HackerManpanel(HackerMan HM){
        this.HM=HM;
    }
    protected void paintComponent(Graphics g) {
        for(int i = 0; i<HM.tiles.length;i++){
            for(int j = 0; j<HM.tiles[i].length;j++){
                BufferedImage image=null;
                String v = "";
                if(HM.tiles[i][j].explored){
                    v="v";
                }
                try {
                    image = ImageIO.read(new File("Images/Maze"+HM.tiles[i][j].type+v+".png"));
                } catch (IOException e) {
                    System.out.println("no such file "+"Images/Maze"+HM.tiles[i][j].type);
                }
                //https://stackoverflow.com/questions/8639567/java-rotating-images
                double rotationRequired = Math.toRadians (HM.tiles[i][j].rotation*90);
                double locationX = image.getWidth() / 2;
                double locationY = image.getHeight() / 2;
                AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
                // Drawing the rotated image at the required drawing locations
                g.drawImage(op.filter(image, null), i*100, j*100, null);

            }
        }
    }
}
class Tile{
    public int rotation;
    public int width;
    public int height;
    public int type;
    public int x;
    public int y;
    public boolean start;
    public boolean end;
    public static final int UP=0;
    public static final int RIGHT=1;
    public static final int DOWN=2;
    public static final int LEFT=3;
    public boolean explored=false;
    public Tile(int width, int height,int type,int startrotation,boolean start,boolean end,int x,int y){
        rotation=startrotation;
        this.width=width;
        this.height=height;
        this.type=type;
        this.start=start;
        this.end=end;
        this.x=x;
        this.y=y;
    }
    public void rotate(int times){
        if(!start&&!end){
            rotation=(rotation+times)%4;
        }
    }
    public boolean equals(Tile t){
        return (t.x==x&&t.y==y);
    }
}
