package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DeroulementDuJeu {

    final static int TEXTSPEED = 10;
    final static String CLEAR = String.format("\033[2J");
    final static String cheminMessage = System.getProperty("user.dir") + "/res/main/Message.txt";
    final static Bateau[] BATEAUX = { new Croiseur(), new Croiseur(), new Destroyer(), new Cuirasser(), new PorteAvion() };

    public static void displayText(String texte) throws InterruptedException {
        for (int i = 0; i < texte.length(); i++) {
            System.out.print(texte.charAt(i));
            TimeUnit.MILLISECONDS.sleep(TEXTSPEED);
        }
    }

    public static ArrayList<String> loadMessages() {
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
        return messages;
    }

    public static void placeShipsManually(Plateau plateau, Plateau opponentPlateau) {
        for (Bateau bateau : BATEAUX) {
            boolean placed = false;
            while (!placed) {
                String posBat = Saisie.getPositionBateau();
                char yBat = posBat.charAt(0);
                int xBat = Integer.parseInt(posBat.substring(1));
                char HorVer = Saisie.getHorVer().charAt(0);
                boolean horizontal = (HorVer == 'H');
                placed = plateau.placerBateau(xBat, yBat, bateau, horizontal);
                System.out.println(plateau.getStringPlateau(opponentPlateau));
            }
        }
    }

    public static void placeShipsRandomly(Plateau plateau) {
        Random random = new Random();
        for (Bateau bateau : BATEAUX) {
            boolean placed = false;
            while (!placed) {
                int xBat = random.nextInt(10) + 1;
                char yBat = (char) (random.nextInt(10) + 'A');
                boolean horizontal = random.nextBoolean();
                placed = plateau.placerBateau(xBat, yBat, bateau, horizontal);
            }
        }
    }

    public static boolean gameTurn(String playerName, String opponentName, Plateau playerPlateau, Plateau opponentPlateau, ArrayList<String> messages, int tour) throws InterruptedException {
        boolean playerTurn = true;

        System.out.println(messages.get(5).replace("{Player}", playerName));
        TimeUnit.SECONDS.sleep(1);

        while (playerTurn) {
            Missile missile = Missile.CLASSIC;
            System.out.println(messages.get(13));
            System.out.println(missile.getAllMissiles());
            try {
                missile = Missile.values()[Integer.parseInt(Saisie.getSaisie()) - 1];
                System.out.println(CLEAR);
                if (missile.getDelay() != 0) {
                    if (tour % missile.getDelay() != 0 || tour == 0) {
                        throw new WrongMissileSelectionException("Le missile de " + playerName + " n'est pas encore chargé !");
                    }
                }
            } catch (WrongMissileSelectionException e) {
                missile = Missile.CLASSIC;
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("\u001B[31mUne erreur est survenue, veuillez contacter le support !\u001B[00m");
                System.exit(1);
            }

            System.out.println("Tour n°" + tour);
            System.out.println("Carte de " + opponentName + " :" + System.lineSeparator());
            System.out.println(playerPlateau.getHimPlateau(opponentPlateau) + System.lineSeparator());
            System.out.println("Carte de " + playerName + " :" + System.lineSeparator());
            System.out.println(playerPlateau.getStringPlateau(opponentPlateau) + System.lineSeparator());

            System.out.println("Missile sélectionné : " + missile);

            System.out.print("Entrez les coordonnées de tir (ex : A5) : ");
            String tir = Saisie.getPositionTir();

            System.out.print(CLEAR);
            TimeUnit.SECONDS.sleep(1);

            int x = tir.charAt(0) - 'A';
            int y = Integer.parseInt(tir.substring(1)) - 1;

            String msg;
            if (opponentPlateau.shootAvailable(x, y, missile, playerPlateau)) {
                opponentPlateau.fire(x, y, missile);
                Bateau boat = opponentPlateau.getCase(x, y);
                if (boat != null) {
                    if (boat.isSunk()) {
                        msg = messages.get(1).replace("{Boat}", boat.getName()).replace("{Player}", opponentName);
                        Historique.addString(msg);
                        System.out.println(msg);
                    } else {
                        msg = messages.get(0).replace("{Player}", opponentName);
                        Historique.addString(msg);
                        System.out.println(msg);
                    }
                    playerTurn = true;
                } else {
                    msg = messages.get(6).replace("{Player}", playerName); // Raté
                    Historique.addString(msg);
                    System.out.println(msg);
                    playerTurn = false;
                }
            } else {
                System.out.println(messages.get(16));
                playerTurn = false;
            }
            if (missile == Missile.HEAVY) {
                if (y - 1 >= 0 && y - 1 <= 9) playerPlateau.shooted(x, y - 1);
                if (y + 1 >= 0 && y + 1 <= 9) playerPlateau.shooted(x, y + 1);
                playerPlateau.shooted(x, y);
            } else if (missile != Missile.RECO) {
                playerPlateau.shooted(x, y);
            }

            if (opponentPlateau.checkIfGameFinished()) {
                System.out.println(messages.get(3));
                return true;
            }
            TimeUnit.SECONDS.sleep(1);
            System.out.print(CLEAR);
            TimeUnit.SECONDS.sleep(1);
        }
        return false;
    }
}