package Minigames;

import javax.swing.*;
import java.awt.*;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static java.lang.Math.*;
public class MicroWars {
    public HashMap<Point,ArrayList<soldier>> troops=new HashMap<>();
    public ArrayList<soldier> troopslist=new ArrayList<>();
    public Planet[] planets;
    private int speed;
    private ArrayList<soldier> selected = new ArrayList<>();
    public static final int PLAYERTEAM=1;
    private MicroWarsPanel paint=new MicroWarsPanel(this);
    public final static int soldiersize=4;
    public final static int planetsizemod=20;
    public int victory=-1;
    public final static int WIN=1;
    public final static int LOSE=0;
    public MicroWars(int numplanets,int maxwidth,int maxheight,int maxsize,int teams,int speed) {
        while(!makemap(numplanets,maxwidth,maxheight,maxsize,teams,speed)){
            numplanets--;
        }
        play();
    }
    private boolean makemap(int numplanets,int maxwidth,int maxheight,int maxsize,int teams,int speed){
        int checking=0;
        this.speed=speed;
        Random rand = new Random();
        planets=new Planet[numplanets];
        for (int i = 0; i < numplanets-teams; i++) {
            boolean thing=true;
            int x=0;
            int y=0;
            int size=rand.nextInt(maxsize)+1;
            do {
                thing=true;
                x = rand.nextInt(maxwidth);
                y = rand.nextInt(maxheight);
                for(int j=0;j<planets.length;j++){
                    if(planets[j]!=null&&(abs(planets[j].x-x)<((size+planets[j].maxsize)*planetsizemod/2)||abs(planets[j].y-y)<((size+planets[j].maxsize)*planetsizemod/2))){
                        thing=false;
                    }
                }
                checking++;
                if(checking>1000){
                    return false;
                }
            }while (!thing);
            planets[i]=new Planet(x,y,-1,size);
            planets[i].strength=0;
        }
        for (int i = numplanets-teams; i < numplanets; i++) {
            boolean thing=true;
            int x=0;
            int y=0;
            int size=rand.nextInt(maxsize)+1;
            do {
                thing=true;
                x = rand.nextInt(maxwidth);
                y = rand.nextInt(maxheight);
                for(int j=0;j<planets.length;j++){
                    if(planets[j]!=null&&(abs(planets[j].x-x)<((size+planets[j].maxsize)*planetsizemod/2)||abs(planets[j].y-y)<((size+planets[j].maxsize)*planetsizemod/2))){
                        thing=false;
                    }
                }
                checking++;
                if(checking>1000){
                    return false;
                }
            }while (!thing);
            planets[i]=new Planet(x,y,numplanets-i,size);
            planets[i].size=1;
            planets[i].strength=100;
        }
        JFrame game = new JFrame();
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setTitle("game");
        game.add(paint);
        game.setSize(2 * (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3 , 4 * (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 5);
        game.setLocationRelativeTo(null);
        game.setVisible(true);
        paint.addMouseListener(new MouseXY(this));
        return true;
    }
    public void play(){
        int time=0;
        while(done()==-1){
            try {
                Thread.sleep(50);
            }catch (Exception e){
                System.out.println("oops");
            }
            for (soldier s: troopslist){
                s.update(troops,planets);
            }
            for (int i = 0; i <planets.length; i++) {
                if(time%10==0) {
                    planets[i].update(troops, troopslist, speed);
                }
            }
            anhilate();
            paint.repaint();
            time++;
        }
        victory=done();
        paint.repaint();
    }
    public int done(){
        boolean won=true;
        boolean lost=true;
        for (int i = 0; i <planets.length ; i++) {
            if(planets[i].team==PLAYERTEAM){
                lost=false;
            }
            if(planets[i].team>0&&planets[i].team!=PLAYERTEAM){
                won=false;
            }
        }
        if(!won&&!lost){
            return -1;
        }
        if(lost){
            return LOSE;
        }
        if (won){
            return WIN;
        }
        return -1;
    }
    public void select(int x,int y, int x1,int y1){
        // re write to make faster
        int biggerx=max(x,x1);
        int smallerx=min(x,x1);
        int biggery=max(y,y1);
        int smallery=min(y,y1);
        for (int i = 0; i <troopslist.size() ; i++) {
            if(troopslist.get(i).x<biggerx && troopslist.get(i).x>smallerx
                && troopslist.get(i).y<biggery && troopslist.get(i).y>smallery && troopslist.get(i).TEAM==PLAYERTEAM){
                selected.add(troopslist.get(i));
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
        for (int i=0;i<troopslist.size();i++) {
            soldier s=troopslist.get(i);
            for (int j = -5; j <= 5; j++) {
                for (int k = -5; k <= 5; k++) {
                    if (sqrt((double)(j*j+k*k))>5||(j==0&&k==0)) {
                        continue;
                    }
                    Point loc = new Point(s.x+j,s.y+k);
                    //System.out.println(troops.get(loc));
                    if (troops.get(loc)!= null&&troops.get(loc).size()>0 && troops.get(loc).get(0).TEAM != s.TEAM) {
                        soldier n=troops.get(loc).remove(0);
                        troopslist.remove(n);
                        loc=new Point(s.x,s.y);
                        troopslist.remove(s);
                        if(troops.get(loc)!=null&&troops.get(loc).size()>0) {
                            troops.get(loc).remove(0);
                        }
                    }
                }
            }
        }
        for (Planet p :planets) {
            int size= p.size;
            if(size==0){
                size++;
            }
            int bound=size*planetsizemod/2;
            for (int j = -bound; j <=bound; j++) {
                for (int k = -bound; k <= bound; k++) {
                    if (sqrt((double)(j*j+k*k))>bound) {
                        continue;
                    }
                    Point loc = new Point(p.x+j,p.y+k);
                    if (troops.get(loc)!=null&&troops.get(loc).size()>0) {
                        int iterations=troops.get(loc).size();
                        for (int i = 0;i <iterations ; i++) {
                            if(troops.get(loc)!=null&&troops.get(loc).get(i).left) {
                                if(p.changestrength(troops.get(loc).get(i).TEAM)) {
                                    soldier n = troops.get(loc).remove(i);
                                    troopslist.remove(n);
                                    iterations--;
                                }
                            }
                        }

                    }
                }
            }
        }

    }

}

class MicroWarsPanel extends JPanel{
    public MicroWars m;
    public HashMap<Integer,Color> color =new HashMap<>();
    private Color[] c = {Color.BLUE,Color.RED,Color.MAGENTA,Color.ORANGE};
    public MicroWarsPanel(MicroWars m){
        this.m = m;
        color.put(-1,Color.BLACK);
        for(int i=0;i<c.length;i++){
            color.put(i,c[i]);
        }
    }
    protected void paintComponent(Graphics g) {
        if ((m.victory == MicroWars.WIN || m.victory == MicroWars.LOSE)){
            if(m.victory==MicroWars.WIN){
                g.drawString("You Win",getWidth()/2,getHeight()/2);
            }
            if(m.victory==MicroWars.LOSE){
                g.drawString("You Lose",1000,300);
            }

        } else {
            super.paintComponent(g);
            for (int i = 0; i < m.planets.length; i++) {
                if (m.planets[i] == null) {
                    return;
                }
                int size = m.planets[i].size;
                if (size == 0) {
                    size++;
                }
                int x = m.planets[i].x - size * MicroWars.planetsizemod / 2;
                int y = m.planets[i].y - size * MicroWars.planetsizemod / 2;
                int team = m.planets[i].team;
                g.setColor(color.get(team));
                g.drawOval(x, y, size * 20, size * 20);
                g.drawRect(m.planets[i].x, m.planets[i].y, 1, 1);
                x = m.planets[i].x - size * MicroWars.planetsizemod/4;
                y = m.planets[i].y - size * MicroWars.planetsizemod/4;
                g.fillArc(x,y,size*10,size*10,0,(int)(m.planets[i].strength/(10.0/36.0)));
                x = m.planets[i].x - size * MicroWars.planetsizemod/2;
                y = m.planets[i].y + size * MicroWars.planetsizemod/2;
                g.fillRect(x,y,(int)(size * MicroWars.planetsizemod*((double)m.planets[i].untilgrowth/m.planets[i].maxStrength)),10);
            }
            for (int i = 0; i < m.troopslist.size(); i++) {
                int x = m.troopslist.get(i).x - MicroWars.soldiersize / 2;
                int y = m.troopslist.get(i).y - MicroWars.soldiersize / 2;
                int team = m.troopslist.get(i).TEAM;
                g.setColor(color.get(team));
                g.drawOval(x, y, 4, 4);
            }

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
        size=0;
    }
    public boolean changestrength(int soldierteam){
        if(team==soldierteam && size>=maxsize && strength>=maxStrength){
            return false;
        }
        int strengthchange=0;
        if(team==soldierteam){
            strengthchange=1;
        }
        else if(team==-1){
            team=soldierteam;
            strengthchange=1;
        }
        else {
            strengthchange=-1;
        }

        if(strength+strengthchange>=maxStrength){
            if(size==0){
                size=1;
                strength=100;
                return true;
            }
            untilgrowth+=strengthchange;
            if(untilgrowth>maxStrength&&size<maxsize){
                size++;
                untilgrowth=0;
            }
            else if(size>=maxsize){
                untilgrowth=0;
            }
            strength=maxStrength;
        }
        if(strength+strengthchange<0){
            team=-1;
            size=0;
            strength=0;
            untilgrowth=0;
        }
        else if(strength+strengthchange<=maxStrength){
            strength+=strengthchange;
        }
        return true;
    }
    public void update(HashMap<Point,ArrayList<soldier>> troops,ArrayList<soldier>troopslist,int speed){
        if(team==-1){
            return;
        }
        int soldiers=0;
        if(team==MicroWars.PLAYERTEAM){
            soldiers=size*2;
        }
        else {
            soldiers=(int)(size*2);
        }
        for(int i = 0;i<soldiers;i++) {
            int n = (int)((random()-0.5)*size*MicroWars.planetsizemod);
            int k = (int)((random()-0.5)*size*MicroWars.planetsizemod);
            Point p = new Point(x+n,y+k);
            soldier sold= new soldier(x+n, y+k, team, speed, maxsize*MicroWars.planetsizemod/2);
            troops.putIfAbsent(p,new ArrayList<>());
            troops.get(p).add(sold);
            troopslist.add(sold);
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
    protected boolean left=false;
    private int homex;
    private int homey;
    private int distance;
    private boolean returntobase;
    public soldier(int x, int y,int team,int speed,int planetsize){
        this.x=x;
        this.y=y;
        homex=x;
        homey=y;
        distance=planetsize;
        this.TEAM=team;
        this.speed=speed;
    }
    public void order(int x, int y){
        gotox=x;
        gotoy=y;
    }
    public void update(HashMap<Point,ArrayList<soldier>> troops,Planet[] planets){
        if(TEAM!=MicroWars.PLAYERTEAM){
            if(gotox==-1&&gotoy==-1){
                int rand = (int)(planets.length*random());
                for (int i=0;i<planets.length;i++) {
                    Planet p=planets[i];
                    if(p.team==TEAM&&p.strength<p.maxStrength){
                        this.returntobase=true;
                        order(planets[(i+1)%planets.length].x,planets[(i+1)%planets.length].y);
                        break;
                    }
                }
                Planet p = planets[rand];
                order(p.x,p.y);
            }
        }
        if(gotox<0||gotoy<0){
            return;
        }
        if(gotox==x&&gotoy==y){
            gotox=-1;
            gotoy=-1;
            return;
        }
        int tempx=x;
        int tempy=y;
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
        Point newloc =new Point(x,y);
        Point oldloc= new Point(tempx,tempy);
        try {
            troops.get(oldloc).remove(this);
        }catch(Exception e){
            System.out.println("Oof");
        }
        troops.putIfAbsent(newloc,new ArrayList<soldier>());
        troops.get(newloc).add(this);
        if(troops.get(newloc)==null){
            System.out.println("here");
        }
        if(!left) {
            int diff1=homex-x-MicroWars.soldiersize/2;
            int diff2=homey-y-MicroWars.soldiersize/2;
            if (distance+1<sqrt(pow(diff1,2)+pow(diff2,2))){
                left=true;
                if(returntobase=true){
                    order(homex,homey);
                }
            }
        }
    }
}
