package main;

/**
 * Main
 */
public class Main {

    final static String CLEAR = String.format("\033[2J");

    public static void main(String[] args) {
        System.out.print(CLEAR);

        //création des plateaux
        Plateau playerPlateau = new Plateau(true);
        System.out.println(playerPlateau.getStringPlateau());

        Plateau botPlateau = new Plateau(false);
        System.out.println(botPlateau.getStringPlateau());

        //création des bateaux du joueur
        Croiseur playerCroiseur = new Croiseur();
        Croiseur playerCroiseur2 = new Croiseur();
        Cuirasser playerCuirasser = new Cuirasser();
        Destroyer playerDestroyer = new Destroyer();
        PorteAvion playerPorteAvion = new PorteAvion();
        playerPlateau.placerBateau(3, 'E', playerPorteAvion);
        System.out.println(playerPlateau.getStringPlateau());

        //création des bateaux du bot
        Croiseur botCroiseur = new Croiseur();
        Croiseur botCroiseur2 = new Croiseur();
        Cuirasser botCuirasser = new Cuirasser();
        Destroyer botDestroyer = new Destroyer();
        PorteAvion botPorteAvion = new PorteAvion();
    }
}