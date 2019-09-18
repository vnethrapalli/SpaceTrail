package Minigames;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.addExact;

public class MicroWars {
    private ArrayList<soldier> troops;
    private Planet[] planets;
    public MicroWars(String FILE){

    }
    public void readmap(){

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
    protected int maxStrength;
    public Planet(int x, int y, int team,int size){
        this.x=x;
        this.y=y;
        this.team=team;
    }
    public void strengthen(int strengthchange){
        if(strength+strengthchange>maxStrength){
            strength=maxStrength;
        }
        if(strength+strengthchange<0){
            team=-1;
            size=0;
        }
        else {
            strength+=strengthchange;
        }
    }
    public void update(ArrayList<soldier> troops,int speed){
        if(team==-1){
            return;
        }

        for(int i = 0;i<size;i++) {
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
