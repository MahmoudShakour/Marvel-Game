package model.world;

import java.awt.Point;
import java.util.Random;

public class Cover implements Damageable {
    private static final int minimumHealth = 100;
    private static final int maximumHealth = 1000;
    private int currentHP;
    private Point location;

    public Cover(int x, int y) {
        location = new Point(x, y);
        currentHP = new Random().nextInt(maximumHealth-minimumHealth)+minimumHealth;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        if(currentHP>=maximumHealth){
            this.currentHP=maximumHealth;
        }
        else if(currentHP <= 0){
            this.currentHP=0;
        }else{
            this.currentHP = currentHP;
        }
    }

    public Point getLocation() {
        return location;
    }
    
}