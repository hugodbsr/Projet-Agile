package main;

/**
 * Plateau
 */
public class Plateau {
    private Bateau plt[][];
    private int tour;

    public Plateau() {
        this.plt = new Bateau[10][10];
        this.tour = 0;
    }

    public String getPlateau() {
        String res = new String();
        for (int i = 0; i < this.plt.length; i++) {
            for (int j = 0; j < this.plt[i].length; j++) {
                res += this.plt[i][j];
            }
        }
        return res;
    }

    public String getTour() {
        return "Tour n°" + this.tour;
    }

    public void reset() {
        this.plt = new Bateau[10][10];
    }
}