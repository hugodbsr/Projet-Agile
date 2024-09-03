package main;

/**
 * Plateau
 */
public class Plateau {
    private Bateau plt[][];
    private boolean tire[][];
    private int tour;
    private boolean our;

    public Plateau(boolean our) {
        this.plt = new Bateau[10][10];
        this.tire = new boolean[10][10];
        this.tour = 0;
        this.our = our;
    }

    public String getStringPlateau() {
        String res = new String();
        char lettre = 'A';
        if (this.our) {
            for (int i = 0; i < this.plt.length; i++) {
                res += lettre;
                for (int j = 0; j < this.plt[i].length; j++) {
                    if (this.plt[i][j] == null) {
                        res += "|  ";
                    } else {
                        res += "|  ";
                    }
                }
                lettre++;
                res += "|\n";
            }
        } else {
            for (int i = 0; i < this.tire.length; i++) {
                res += lettre;
                for (int j = 0; j < this.tire[i].length; j++) {
                    if (this.plt[i][j] == null) {
                        res += "|  ";
                    } else {
                        res += "| x";
                    }
                }
                lettre++;
                res += "|\n";
            }

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
        this.tire = new boolean[10][10];
    }
}