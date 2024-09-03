package main;

/**
 * Plateau
 */
public class Plateau {
    private Bateau plt[][];
    private boolean tirs[][];
    private int tour;
    private boolean our;

    public Plateau(boolean our) {
        this.plt = new Bateau[10][10];
        this.tirs = new boolean[10][10];
        this.tour = 0;
        this.our = our;
    }

    public String getStringPlateau() {
        String res = new String();
        char lettre = 'A';
        for (int i = 0; i < this.plt.length; i++) {
            res += lettre;
            for (int j = 0; j < this.plt[i].length; j++) {
                if (this.plt[i][j] == null) {
                    res += "|  ";
                } else {
                    res += "| B";
                }
            }
            lettre++;
            res += "|\n";
        }
        return res + " | 1| 2| 3| 4| 5| 6| 7| 8| 9|10|";
    }

    public String getHimPlateau(Plateau target) {
        String res = new String();
        char lettre = 'A';
        for (int i = 0; i < this.tirs.length; i++) {
            res += lettre;
            for (int j = 0; j < this.tirs[i].length; j++) {
                if (this.tirs[i][j]) {
                    if (target.plt[i][j] == null) {
                        res += "| X";
                    } else {
                        res += "| \u001B[31m" + "X" + "\u001B[00m";
                    }
                } else {
                    res += "|  ";
                }
            }
            lettre++;
            res += "|\n";
        }
        return res + " | 1| 2| 3| 4| 5| 6| 7| 8| 9|10|";
    }

    public Bateau[][] getPlateau() {
        return this.plt;
    }

    public void setBateau(int x, int y, Bateau bt) {
        this.plt[x][y] = bt;
    }

    public String getTour() {
        return "Tour n°" + this.tour;
    }

    public Bateau getCase(int x, int y) {
        return this.plt[x][y];
    }

    public void reset() {
        this.plt = new Bateau[10][10];
        this.tirs = new boolean[10][10];
    }

    public boolean shootAvailable(int x, int y){
        return this.plt[x][y] != null;
    }

    public void fire(int x, int y){
        this.plt[x][y].hit();
    }

    public void shooted(int x, int y){
        this.tirs[x][y] = true;
    }
}