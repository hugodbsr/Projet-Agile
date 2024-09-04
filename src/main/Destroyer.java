package main;

public class Destroyer extends Bateau {
    public Destroyer() {
        this.health = 2;
        this.sunk = false;
        this.color = "\u001B[33m";
    }
}
