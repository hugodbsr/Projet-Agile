package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainGame {

    final static String CLEAR = String.format("\033[2J");
    final static String cheminMessage = System.getProperty("user.dir") + "/res/main/Message.txt";

    public static void main(String[] args) throws InterruptedException {
        String botName = "ORDI";
        boolean gameFinished = false;
        int tour = 0;

        System.out.print(CLEAR);

        TimeUnit.SECONDS.sleep(1);

        System.out.print("Saisissez votre nom : ");
        String playerName = Saisie.getSaisie();

        System.out.print(CLEAR);

        TimeUnit.SECONDS.sleep(1);

        // récupération des messages
        ArrayList<String> messages = new ArrayList<>();
        try (BufferedReader bw = new BufferedReader(new FileReader(new File(cheminMessage)))) {
            while (bw.ready()) {
                messages.add(bw.readLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (Exception e) {
            System.err.println(e);
        }

        System.out.println(messages.get(12));

        Plateau playerPlateau = new Plateau(true);
        Plateau botPlateau = new Plateau(false);

        // Création et placement des bateaux du joueur
        playerPlateau.placerBateau(3, 'C', new Croiseur(), true);
        playerPlateau.placerBateau(8, 'E', new Croiseur(), false);
        playerPlateau.placerBateau(6, 'F', new Cuirasser(), true);
        playerPlateau.placerBateau(2, 'B', new Destroyer(), false);
        playerPlateau.placerBateau(4, 'G', new PorteAvion(), true);

        // Création et placement des bateaux du bot
        botPlateau.placerBateau(3, 'A', new PorteAvion(), true);
        botPlateau.placerBateau(8, 'E', new Cuirasser(), false);
        botPlateau.placerBateau(6, 'F', new Destroyer(), true);
        botPlateau.placerBateau(2, 'B', new Croiseur(), false);
        botPlateau.placerBateau(4, 'G', new Croiseur(), true);

        while (!gameFinished) {
            boolean playerTurn = true;

            while (playerTurn && !gameFinished) {
                System.out.println("Tour n°" + tour);
                System.out.println("Carte de " + botName + " :" + System.lineSeparator());
                System.out.println(playerPlateau.getHimPlateau(botPlateau) + System.lineSeparator());

                Missile mis = Missile.CLASSIC;
                System.out.println(messages.get(13));
                System.out.println(mis.getAllMissiles());
                Missile missile = Missile.CLASSIC;
                try {
                    missile = Missile.values()[Integer.parseInt(Saisie.getSaisie()) - 1];
                    System.out.println(CLEAR);
                    if (missile.getDelay() != 0) {
                        if (tour % missile.getDelay() != 0 || tour == 0) {
                            throw new WrongMissileSelectionException("Le missile n'est pas encore chargé !");
                        }
                    }
                } catch (Exception e) {
                    missile = Missile.CLASSIC;
                    System.out.println(e.getMessage());
                }

                System.out.println("Carte de " + botName + " :" + System.lineSeparator());
                System.out.println(playerPlateau.getHimPlateau(botPlateau) + System.lineSeparator());
                System.out.println("Carte de " + playerName + " :" + System.lineSeparator());
                System.out.println(playerPlateau.getStringPlateau(botPlateau) + System.lineSeparator());

                System.out.println("Missile sélectionné " + missile);

                System.out.print("Entrez les coordonnées de tir (ex : A5) : ");
                String tir = Saisie.getPositionTir();

                int x = tir.charAt(0) - 'A';
                int y = Integer.parseInt(tir.substring(1)) - 1;

                if (botPlateau.shootAvailable(x, y, missile)) {
                    botPlateau.fire(x, y, missile);
                    Bateau boat = botPlateau.getCase(x, y);
                    if (boat != null) {
                        if (boat.isSunk()) {
                            System.out.println(messages.get(1).replace("{Boat}", boat.getName()).replace("{Player}", botName));
                        } else {
                            System.out.println(messages.get(0).replace("{Boat}", boat.getName()).replace("{Player}", botName));
                        }
                        playerTurn = true;
                    } else {
                        System.out.println(messages.get(6).replace("{Player}", playerName));  // Raté
                        playerTurn = false;
                    }
                } else {
                    System.out.println(messages.get(6).replace("{Player}", playerName));
                    playerTurn = false;
                }
                playerPlateau.shooted(x, y);

                gameFinished = botPlateau.checkIfGameFinished();
                if (gameFinished) {
                    System.out.println(messages.get(3));
                    break;
                }
            }

            if (!gameFinished) {
                boolean botTurn = true;

                System.out.println(messages.get(5).replace("{Player}", botName));
                TimeUnit.SECONDS.sleep(1);

                while (botTurn && !gameFinished) {
                    int botX = (int) (Math.random() * 10);
                    int botY = (int) (Math.random() * 10);

                    if (playerPlateau.shootAvailable(botX, botY)) {
                        playerPlateau.fire(botX, botY);
                        Bateau boat = playerPlateau.getCase(botX, botY);
                        if (boat != null) {
                            if (boat.isSunk()) {
                                System.out.println(messages.get(1).replace("{Boat}", boat.getName()).replace("{Player}", playerName));
                            } else {
                                System.out.println(messages.get(0).replace("{Boat}", boat.getName()).replace("{Player}", playerName));
                            }
                            botTurn = true;
                        } else {
                            System.out.println(messages.get(6).replace("{Player}", botName));
                            botTurn = false;
                        }
                    } else {
                        System.out.println(messages.get(6).replace("{Player}", botName));
                        botTurn = false;
                    }
                    botPlateau.shooted(botX, botY);

                    gameFinished = playerPlateau.checkIfGameFinished();
                    if (gameFinished) {
                        System.out.println(messages.get(4));
                        break;
                    }
                }
            }
        }
    }
}
