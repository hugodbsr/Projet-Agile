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
        
        Historique.createGameFile(playerName, "ORDI");
        
        System.out.print("Entrez 'R' pour placer aléatoirement ou autre pour placer manuellement: ");
        String posBat = Saisie.getSaisie();
        if (posBat.equalsIgnoreCase("R")) {
            DeroulementDuJeu.placeShipsRandomly(playerPlateau);
        } else {
            System.out.println(playerPlateau.getStringPlateau(botPlateau));
            DeroulementDuJeu.placeShipsManually(playerPlateau, botPlateau);
        }

        // Création et placement des bateaux du bot
        DeroulementDuJeu.placeShipsRandomly(botPlateau);

        Historique.addPlateau(playerPlateau, botPlateau);

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

                String msg;

                if (playerPlateau.shootAvailable(botX, botY, Missile.CLASSIC, botPlateau)) {
                    playerPlateau.fire(botX, botY, Missile.CLASSIC, botPlateau);
                    Bateau boat = playerPlateau.getCase(botX, botY);
                    if (boat != null) {
                        if (boat.isSunk()) {
                            msg = messages.get(1).replace("{Boat}", boat.getName()).replace("{Player}", playerName);
                            Historique.addString(msg);
                            System.out.println(msg);
                        } else {
                            msg = messages.get(0).replace("{Player}", playerName);
                            Historique.addString(msg);
                            System.out.println(msg);
                        }
                        botTurn = true;
                    } else {
                        msg = messages.get(6).replace("{Player}", botName);
                        Historique.addString(msg);
                        System.out.println(msg);
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
