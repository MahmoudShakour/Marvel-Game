package world;

import java.awt.Point;
import java.util.Random;

public class Cover {
    private static final int minimumHealth = 100;
    private static final int maximumHealth = 1000;
    private int currentHP;
    private Point location;

    public Cover(int x, int y) {
        location = new Point(x, y);
        currentHP = new Random().nextInt(minimumHealth, maximumHealth);
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public Point getLocation() {
        return location;
    }
    
}