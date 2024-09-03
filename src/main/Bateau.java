package main;

/**
 * Bateau
 */
public abstract class Bateau {
    protected int health;
    protected boolean sunk;

    public int getHealth(){
        return health;
    }

    public String getName(){
        return this.getClass().getName();
    }
    
    public boolean isSunk(){
        return sunk;
    }
}