package main;

/**
 * Bateau
 */
public abstract class Bateau {
    protected int health;
    protected boolean sunk;
    protected String color;

    public int getHealth(){
        return health;
    }

    public String getName(){
        return this.color + this.getClass().getName() + "\u001B[0m";
    }
    
    public boolean isSunk(){
        return sunk;
    }

    public String getColor(){
        return color;
    }
}