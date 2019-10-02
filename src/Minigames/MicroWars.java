package Minigames;

import javax.swing.*;
import java.awt.*;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.*;
public class MicroWars {
    public ArrayList<soldier> troops=new ArrayList<>();
    public Planet[] planets;
    private int speed;
    private ArrayList<soldier> selected = new ArrayList<>();
    public static final int PLAYERTEAM=1;
    private MicroWarsPanel paint=new MicroWarsPanel(this);
    public final static int soldiersize=4;
    public final static int planetsizemod=20;
    public MicroWars(int numplanets,int maxwidth,int maxheight,int maxsize,int teams,int speed){


        this.speed=speed;
        Random rand = new Random();
        planets=new Planet[numplanets];
        for (int i = 0; i < numplanets-teams; i++) {
            boolean thing=true;
            int x=0;
            int y=0;
            do {
                thing=true;
                x = rand.nextInt(maxwidth);
                y = rand.nextInt(maxheight);
                for(int j=0;j<planets.length;j++){
                    if(planets[j]!=null&&(planets[j].x==x&&planets[j].y==y)){
                        thing=false;
                    }
                }
            }while (!thing);
            planets[i]=new Planet(x,y,-1,rand.nextInt(maxsize));
        }
        for (int i = numplanets-teams; i < numplanets; i++) {
            boolean thing=true;
            int x=0;
            int y=0;
            do {
                thing=true;
                x = rand.nextInt(maxwidth);
                y = rand.nextInt(maxheight);
                for(int j=0;j<planets.length;j++){
                    if(planets[j]!=null&&(planets[j].x==x&&planets[j].y==y)){
                        thing=false;
                    }
                }
            }while (!thing);
            planets[i]=new Planet(x,y,numplanets-i,rand.nextInt(maxsize));
            planets[i].size=1;
        }
        JFrame game = new JFrame();
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setTitle("game");
        game.add(paint);
        game.setSize(2 * (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3 , 4 * (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5);
        game.setLocationRelativeTo(null);
        game.setVisible(true);
        paint.addMouseListener(new MouseXY(this));
        play();
    }
    public void play(){
        while(done()==-1){
            try {
                Thread.sleep(100);
            }catch (Exception e){
                System.out.println("oops");
            }
            for (int i = 0; i <troops.size() ; i++) {
                troops.get(i).update();
            }
            for (int i = 0; i <planets.length; i++) {
                planets[i].update(troops,speed);
            }
            anhilate();
            paint.repaint();
        }
    }
    public int done(){
        boolean won=true;
        boolean lost=true;
        for (int i = 0; i <planets.length ; i++) {
            if(planets[i].team==PLAYERTEAM){
                lost=false;
            }
            if(planets[i].team!=PLAYERTEAM){
                won=false;
            }
        }
        if(!won&&!lost){
            return -1;
        }
        if(lost){
            return 0;
        }
        if (won){
            return 1;
        }
        return -1;
    }
    public void select(int x,int y, int x1,int y1){
        int biggerx=max(x,x1);
        int smallerx=min(x,x1);
        int biggery=max(y,y1);
        int smallery=min(y,y1);
        for (int i = 0; i <troops.size() ; i++) {
            if(troops.get(i).x<biggerx && troops.get(i).x>smallerx
                && troops.get(i).y<biggery && troops.get(i).y>smallery && troops.get(i).TEAM==PLAYERTEAM){
                selected.add(troops.get(i));
            }
        }
        System.out.println(selected.size());
    }
    public void setgoto(int x, int y ){
        if(selected.size()>0){
            for (int i = 0; i <selected.size() ; i++) {
                selected.get(i).gotox=x+(int)(random()*selected.size()/8-selected.size()/16);
                selected.get(i).gotoy=y+(int)(random()*selected.size()/8-selected.size()/16);
            }
        }
        selected.clear();
    }
    public void anhilate(){
        for (int i = 0; i <troops.size() ; i++) {
            boolean gone=false;
            for (int j = 0; j <troops.size() ; j++) {
                if(troops.get(i).TEAM!=troops.get(j).TEAM){
                    int diff=troops.get(i).x-troops.get(j).x;
                    int diff2=troops.get(i).y-troops.get(j).y;
                    if(sqrt(pow(diff,2)+pow(diff2,2))<5){
                        troops.remove(i);
                        if(j<i) {
                            troops.remove(j);
                            gone=true;
                        }
                        else {
                            troops.remove(j - 1);
                            gone = true;
                        }
                    }
                }
            }
            if(!gone) {
                for (int j = 0; j < planets.length; j++) {
                    int diff=troops.get(i).x-planets[j].x;
                    int diff2=troops.get(i).y-planets[j].y;
                    if(sqrt(pow(diff,2)+pow(diff2,2))<5) {
                        if (planets[j].strength < planets[j].maxStrength) {
                            troops.remove(j);
                            planets[j].changestrength(1);
                        }
                    }
                }
            }
        }
    }

}
class MicroWarsPanel extends JPanel{
    public MicroWars m;
    public MicroWarsPanel(MicroWars m){
        this.m = m;
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i <m.planets.length ; i++) {
            if(m.planets[i]==null){
                return;
            }
            int x=m.planets[i].x;
            int y=m.planets[i].y;
            int size=m.planets[i].maxsize;
            int team=m.planets[i].team;
            g.setColor(Color.red);
            g.drawOval(x,y,size*20,size*20);
        }
        for (int i = 0; i <m.troops.size() ; i++) {
            int x=m.troops.get(i).x;
            int y=m.troops.get(i).y;
            int team=m.troops.get(i).TEAM;
            g.setColor(Color.blue);
            g.drawOval(x,y,4,4);
        }

    }

}

class Planet{
    public int size;
    protected int team;
    protected int x;
    protected int y;
    protected int strength;
    public final int maxStrength=100;
    protected int maxsize;
    protected int untilgrowth;
    public Planet(int x, int y, int team,int maxsize){
        this.x=x;
        this.y=y;
        this.team=team;
        this.maxsize=maxsize;
    }
    public void changestrength(int strengthchange){
        if(strength+strengthchange>maxStrength){
            untilgrowth+=strengthchange;
            if(untilgrowth<maxStrength&&size<maxsize){
                size++;
            }
            strength=maxStrength;
        }
        if(strength+strengthchange<0){
            team=-1;
            size=0;
            strength=0;
            untilgrowth=0;
        }
        else {
            strength+=strengthchange;
        }
    }
    public void update(ArrayList<soldier> troops,int speed){
        if(team==-1){
            return;
        }
        for(int i = 0;i<size*2;i++) {
            troops.add(new soldier(x+maxsize*10-2, y+maxsize*10-2, team, speed));
        }
    }

}
class soldier{
    protected int x;
    protected int y;
    public final int TEAM;
    protected int gotox=-1;
    protected int gotoy=-1;
    protected int speed;
    public soldier(int x, int y,int team,int speed){
        this.x=x;
        this.y=y;
        this.TEAM=team;
        this.speed=speed;
    }
    public void order(int x, int y){
        gotox=x;
        gotoy=y;
    }
    public void update(){
        if(gotox<0||gotoy<0){
            return;
        }

        if(x!=gotox){
            int diff= gotox-x;

            if(abs(diff)>=speed){
                if(diff<0){
                    x-=speed;
                }
                else {
                    x+=speed;
                }
            }
            else if(abs(diff)<=speed) {
                x = gotox;
            }
        }
        if(y!=gotoy){
            int diff= gotoy-y;
            System.out.println(abs(diff));
            if(abs(diff)>=speed){
                if(diff<0){
                    y-=speed;
                }
                else {
                    y+=speed;
                }
            }
            else if(abs(diff)<=speed) {
                y = gotoy;
            }
        }
    }
}
