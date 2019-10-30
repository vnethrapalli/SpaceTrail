package Util;

public class Character {

    public final boolean gender;
    public final int level;
    private int money;
    private int fuel = 100;
    private int[] supplies = new int[6];
    private String[] shipmods = new String[3];

    public Character(boolean gender, int level) {
        this.gender = gender;
        this.level = level;
        if (level == 3) {
            money = 1000;
        } else if (level == 2) {
            money = 3000;
        } else {
            money = 5000;
        }
    }

    public int getFuel() {
        return fuel;
    }

    public boolean addorsubfuel(int fuel) {
        if (this.fuel + fuel > 0) {
            this.fuel += fuel;
            return true;
        }
        return false;
    }

    public int[] getSupplies() {
        return supplies;
    }

    public boolean addsubsupplies(int amount,int type) {
        if(type>5||type<0){
            return false;
        }
        if(supplies[type]+amount>0){
            supplies[type]+=amount;
            return true;
        }
        return false;
    }

    public boolean addshipmod(String mod) {
        for (int i = 0; i < 3; i++){
            if (shipmods[i] != null&&shipmods[i].equals(mod)) {
                return false;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (shipmods[i] == null) {
                shipmods[i] = mod;
                return true;
            }

        }
        return false;
    }
    public boolean removeshipmod(String mod) {
        for (int i = 0; i < 3; i++) {
            if (shipmods[i] != null&&shipmods[i].equals(mod)) {
                shipmods[i]=null;
                return true;
            }
        }
        return false;
    }

    public int getMoney() {
        return money;
    }

    public boolean addorsubMoney(int money) {
        if (this.money + money > 0) {
            this.money += money;
            return true;
        }
        return false;
    }
}
