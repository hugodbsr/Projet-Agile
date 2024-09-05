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

    public boolean getOur() {
        return this.our;
    }

    public String getStringPlateau(Plateau ennemi) {
        String res = new String();
        char lettre = 'A';
        for (int i = 0; i < this.plt.length; i++) {
            res += lettre;
            for (int j = 0; j < this.plt[i].length; j++) {
                if (this.plt[i][j] == null) {
                    res += "|  ";
                } else {
                    if (ennemi.tirs[i][j]) {
                        res += "| \u001B[31m" + "X" + "\u001B[00m";
                    } else {
                        res += "| " + this.plt[i][j].getColor() + "B" + "\u001B[0m";
                    }
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

    public void setPlateau(String s) {
        String[] tab = s.split("/");
        Bateau[][] res = new Bateau[10][10];
        int idx = 0;
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[1].length; j++) {
                if (tab[idx].equals("Croiseur")) {
                    res[i][j] = new Croiseur();
                } else if (tab[idx].equals("Cuirasser")) {
                    res[i][j] = new Cuirasser();
                } else if (tab[idx].equals("Destroyer")) {
                    res[i][j] = new Destroyer();
                } else if (tab[idx].equals("PorteAvion")) {
                    res[i][j] = new PorteAvion();
                } 
                idx++;
            }
        }
        plt = res;
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

    public boolean shootAvailable(int x, int y, Missile mis, Plateau p) {
        if (mis == Missile.RECO)
            return true;
            int x2 = x;
            int y2 = y;
            if (x > 10) {
                x2 = 10;
            } else if (x < 0) {
                x2 = 0;
            }
            if (y > 10) {
                y2 = 10;
            } else if (x < 0) {
                y2 = 0;
            }
        return (this.plt[x2][y2] != null && !p.tirs[x2][y2]);
    }

    public void fire(int x, int y, Missile mis, Plateau ennemy) {
        int posx = x;
        int posy = y;
        if (mis == Missile.RECO) {
            boolean find = false;
            posx--;
            posy--;
            for (int i = 0; i < Missile.RECO.getZone().length; i++) {
                for (int j = 0; j < Missile.RECO.getZone()[1].length; j++) {
                    if ((posx >= 0 && posx <= 9) && (posy >= 0 && posy <= 9) && Missile.RECO.getZone()[i][j]) {
                        if (ennemy.plt[posx][posy] != null) {
                            find = true;
                        }
                    }
                    posx++;
                }
                posy++;
            }
            if (find)
                System.out.println("\u001B[31mUn navire a été détecté dans cette zone !\u001B[00m");
        } else {
            this.plt[x][y].hit();
        }
    }

    public void shooted(int x, int y) {
        this.tirs[x][y] = true;
    }

    public boolean placerBateau(int x, char y, Bateau bt, boolean horizontal) {
        int newX = x - 1;
        int newY = y - 'A';
        boolean placeable = true;
        if (horizontal) {
            if (newY < 0) {
                newY = 0;
            }
            if (newY > 9) {
                newY = 9;
            }

            if (newX + bt.health > 10) {
                while (newX + bt.health > 10) {
                    newX = newX - 1;
                }
                for (int i = 0; i < bt.health; i++) {
                    if (!(this.plt[newY][newX + i] == null)) {
                        placeable = false;
                    }
                }
                if (placeable) {
                    for (int i = 0; i < bt.health; i++) {
                        this.plt[newY][newX + i] = bt;
                    }
                }
            } else if (newX < 0) {
                while (newX < 0) {
                    newX = newX + 1;
                }
                for (int i = 0; i < bt.health; i++) {
                    if (!(this.plt[newY][newX + i] == null)) {
                        placeable = false;
                        return placeable;
                    }
                }
                if (placeable) {
                    for (int i = 0; i < bt.health; i++) {
                        this.plt[newY][newX + i] = bt;
                    }
                } else {
                    return placeable;
                }
            } else {
                for (int i = 0; i < bt.health; i++) {
                    if (!(this.plt[newY][newX + i] == null)) {
                        placeable = false;
                    }
                }
                if (placeable) {
                    for (int i = 0; i < bt.health; i++) {
                        this.plt[newY][newX + i] = bt;
                    }
                } else {
                    return placeable;
                }

            }
        } else {
            return pivoterBateau(x, y, bt);
        }
        return placeable;
    }

    public boolean checkIfGameFinished() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Bateau bateau = getCase(i, j);
                if (bateau != null && !bateau.isSunk()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean pivoterBateau(int x, char y, Bateau bt) {
        boolean placeable = true;
        int newX = x - 1;
        int newY = y - 'A';
        if (newX < 0) {
            newX = 0;
        }
        if (newX > 9) {
            newX = 9;
        }
        if (newY + bt.health > 10) {
            while (newY + bt.health > 10) {
                newY = newY - 1;
            }
            for (int i = 0; i < bt.health; i++) {
                if (!(this.plt[newY + i][newX] == null)) {
                    placeable = false;
                }
            }
            if (placeable) {
                for (int i = 0; i < bt.health; i++) {
                    this.plt[newY + i][newX] = bt;
                }
                return placeable;
            } else {
                return placeable;
            }
        } else if (newY < 0) {
            while (newY < 0) {
                newY = 0;
            }
            for (int i = 0; i < bt.health; i++) {
                if (!(this.plt[newY + i][newX] == null)) {
                    placeable = false;
                }
            }
            if (placeable) {
                for (int i = 0; i < bt.health; i++) {
                    this.plt[newY + i][newX] = bt;
                }
                return placeable;
            }
        } else {
            for (int i = 0; i < bt.health; i++) {
                if (!(this.plt[newY + i][newX] == null)) {
                    placeable = false;
                }
            }
            if (placeable) {
                for (int i = 0; i < bt.health; i++) {
                    this.plt[newY + i][newX] = bt;
                }
                return placeable;
            }
        }
        return placeable;

    }

}