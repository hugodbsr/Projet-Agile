package main;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainBot {

    public static void main(String[] args) throws InterruptedException {
        String botName = "ORDI";
        boolean gameFinished = false;
        int tour = 0;

        System.out.print(DeroulementDuJeu.CLEAR);
        TimeUnit.SECONDS.sleep(1);

        System.out.print("Saisissez votre nom : ");
        String playerName = Saisie.getSaisie();

        System.out.print(DeroulementDuJeu.CLEAR);
        TimeUnit.SECONDS.sleep(1);

        // Récupération des messages
        ArrayList<String> messages = DeroulementDuJeu.loadMessages();
        DeroulementDuJeu.displayText(messages.get(14));
        TimeUnit.SECONDS.sleep(1);
        System.out.println();
        DeroulementDuJeu.displayText(messages.get(15));

        TimeUnit.SECONDS.sleep(3);

        System.out.print(DeroulementDuJeu.CLEAR);
        TimeUnit.SECONDS.sleep(1);
        System.out.println(messages.get(12));
        TimeUnit.SECONDS.sleep(1);
        System.out.print(DeroulementDuJeu.CLEAR);

        Plateau playerPlateau = new Plateau(true);
        Plateau botPlateau = new Plateau(false);

        String posBat = Saisie.getPositionBateau();
        if (posBat.equals("R")) {
            DeroulementDuJeu.placeShipsRandomly(playerPlateau);
        } else {
            DeroulementDuJeu.placeShipsManually(playerPlateau, botPlateau);
        }

        // Création et placement des bateaux du bot
        botPlateau.placerBateau(3, 'A', new PorteAvion(), true);
        botPlateau.placerBateau(8, 'E', new Cuirasser(), false);
        botPlateau.placerBateau(6, 'F', new Destroyer(), true);
        botPlateau.placerBateau(2, 'B', new Croiseur(), false);
        botPlateau.placerBateau(4, 'G', new Croiseur(), true);

        Historique.createGameFile(playerName, "ORDI");

        while (!gameFinished) {

            gameFinished = DeroulementDuJeu.gameTurn(playerName, botName, playerPlateau, botPlateau, messages, tour);
            if (gameFinished) {
                break;
            }
            TimeUnit.SECONDS.sleep(1);
            System.out.print(DeroulementDuJeu.CLEAR);
            TimeUnit.SECONDS.sleep(1);

            boolean botTurn = true;

            System.out.println(messages.get(5).replace("{Player}", botName));
            TimeUnit.SECONDS.sleep(1);

            while (botTurn) {
                int botX = (int) (Math.random() * 10);
                int botY = (int) (Math.random() * 10);

                if (playerPlateau.shootAvailable(botX, botY, Missile.CLASSIC, botPlateau)) {
                    playerPlateau.fire(botX, botY, Missile.CLASSIC);
                    Bateau boat = playerPlateau.getCase(botX, botY);
                    if (boat != null) {
                        if (boat.isSunk()) {
                            System.out.println(messages.get(1).replace("{Boat}", boat.getName()).replace("{Player}",
                                    playerName));
                        } else {
                            System.out.println(messages.get(0).replace("{Player}", playerName));
                        }
                        botTurn = true;
                    } else {
                        System.out.println(messages.get(6).replace("{Player}", botName));
                        botTurn = false;
                    }
                } else {
                    System.out.println("Loupé !");
                    botTurn = false;
                }
                botPlateau.shooted(botX, botY);

                gameFinished = playerPlateau.checkIfGameFinished();
                if (gameFinished) {
                    System.out.println(messages.get(4));
                    break;
                }
            }

            tour++;
            TimeUnit.SECONDS.sleep(1);
            System.out.print(DeroulementDuJeu.CLEAR);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
