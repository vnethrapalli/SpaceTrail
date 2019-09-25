package Minigames;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.*;
public class MicroWars {
    private ArrayList<soldier> troops;
    private Planet[] planets;
    private int speed;
    public MicroWars(int numplanets,int maxwidth,int maxheight,int maxsize,int teams,int speed){
        this.speed=speed;
        Random rand = new Random();
        planets=new Planet[numplanets];
        for (int i = 0; i < numplanets-teams ; i++) {
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
        for (int i = 0; i < teams; i++) {
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
            planets[i]=new Planet(x,y,i,rand.nextInt(maxsize));
        }
        play();
    }
    public void play(){
        while(done()==-1){
            try {
                Thread.sleep(10);
            }catch (Exception e){
                System.out.println("oops");
            }
            for (int i = 0; i <troops.size() ; i++) {
                troops.get(i).update();
                if(i<planets.length){
                    planets[i].update(troops,speed);
                }
            }
        }
    }
    public int done(){
        boolean won=true;
        boolean lost=true;
        for (int i = 0; i <planets.length ; i++) {
            if(planets[i].team==1){
                lost=false;
            }
            if(planets[i].team!=1){
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

    }
    public void setgoto(int x, int y ){

    }
    public void anhilate(){

    }

}

class Planet{
    private int size;
    protected int team;
    protected int x;
    protected int y;
    protected int strength;
    public final int maxStrength=100;
    protected int maxsize;
    public Planet(int x, int y, int team,int maxsize){
        this.x=x;
        this.y=y;
        this.team=team;
        this.maxsize=maxsize;
    }
    public void changestrength(int strengthchange){
        if(strength+strengthchange>maxStrength){
            if(size<maxsize){
                size++;
            }
            strength=maxStrength;
        }
        if(strength+strengthchange<0){
            team=-1;
            size=0;
            strength=0;
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
            troops.add(new soldier(x, y, team, speed));
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
            if(abs(diff)>speed){
                if(diff<0){
                    x-=speed;
                }
                else {
                    x+=speed;
                }
            }
            x=gotox;
        }
        if(y!=gotoy){
            int diff= gotoy-y;
            if(abs(diff)>speed){
                if(diff<0){
                    y-=speed;
                }
                else {
                    y+=speed;
                }
            }
            y=gotox;
        }
    }
}
