package main;

/**
 * Bateau
 */
public abstract class Bateau {
    protected int health;
    protected boolean sunk;
    protected String color;

    public int getHealth() {
        return health;
    }

    public String getName() {
        return this.color + this.getClass().getSimpleName() + "\u001B[0m";
    }

    public boolean isSunk() {
        if (this.health <= 0) {
            this.sunk = true;
        }
        return sunk;
    }

    public String getColor() {
        return color;
    }

    public void hit() {
        this.health--;
    }
}