package main;

public class Croiseur extends Bateau {
    public Croiseur() {
        this.health = 3;
        this.sunk = false;
        this.color = "\u001B[31m";
    }
}
