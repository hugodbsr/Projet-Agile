package main;

/**
 * Bateau
 */
public abstract class Bateau {
    private int health;
    private boolean sunk;

    public int getHealth(){
        return health;
    }

    public String getName(){
        return this.getClass().getName();
    }
    
    public boolean isSunk(){
        return sunk;
    };
}