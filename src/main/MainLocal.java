package main;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainLocal {

    public static void main(String[] args) throws InterruptedException {
        String player1Name = "Joueur 1";
        String player2Name = "Joueur 2";

        
        boolean gameFinished = false;
        int tourJ1 = 0;
        int tourJ2 = 0;
        
        System.out.print(DeroulementDuJeu.CLEAR);
        TimeUnit.SECONDS.sleep(1);
        
        System.out.print("Saisissez le nom du joueur 1 : ");
        player1Name = Saisie.getSaisie();
        
        System.out.print("Saisissez le nom du joueur 2 : ");
        player2Name = Saisie.getSaisie();
        
        Historique.createGameFile(player1Name, player2Name);
        
        System.out.print(DeroulementDuJeu.CLEAR);
        TimeUnit.SECONDS.sleep(1);
        
        ArrayList<String> messages = DeroulementDuJeu.loadMessages();
        /* DeroulementDuJeu.displayText(messages.get(14));
        TimeUnit.SECONDS.sleep(1);
        System.out.println();
        DeroulementDuJeu.displayText(messages.get(15)); */
        
        TimeUnit.SECONDS.sleep(3);
        
        System.out.print(DeroulementDuJeu.CLEAR);
        TimeUnit.SECONDS.sleep(1);
        
        Plateau player1Plateau = new Plateau(true);
        Plateau player2Plateau = new Plateau(true);

        
        System.out.println("Placement des bateaux pour " + player1Name);
        System.out.print("Entrez 'R' pour placer aléatoirement ou autre pour placer manuellement: ");
        String posBat1 = Saisie.getSaisie();
        if (posBat1.equalsIgnoreCase("R")) {
            DeroulementDuJeu.placeShipsRandomly(player1Plateau);
        } else {
            System.out.println(player1Plateau.getStringPlateau(player2Plateau));
            DeroulementDuJeu.placeShipsManually(player1Plateau, player2Plateau);
        }

        System.out.println(String.format("\033[2J"));

        System.out.println("Placement des bateaux pour " + player2Name);
        System.out.print("Entrez 'R' pour placer aléatoirement ou autre pour placer manuellement: ");
        String posBat2 = Saisie.getSaisie();
        if (posBat2.equalsIgnoreCase("R")) {
            DeroulementDuJeu.placeShipsRandomly(player2Plateau);
        } else {
            System.out.println(player2Plateau.getStringPlateau(player1Plateau));
            DeroulementDuJeu.placeShipsManually(player2Plateau, player1Plateau);
        }
        
        Historique.addPlateau(player1Plateau, player2Plateau);

        while (!gameFinished) {
            gameFinished = DeroulementDuJeu.gameTurn(player1Name, player2Name, player1Plateau, player2Plateau, messages, tourJ1);
            if (gameFinished) {
                break;
            }
            tourJ1++;
            gameFinished = DeroulementDuJeu.gameTurn(player2Name, player1Name, player2Plateau, player1Plateau, messages, tourJ2);
            tourJ2++;
        }
    }
}
