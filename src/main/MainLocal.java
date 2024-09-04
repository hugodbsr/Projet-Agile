package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * MainLocal
 */
public class MainLocal {
    
    final static int TEXTSPEED = 10;
    final static String CLEAR = String.format("\033[2J");
    final static String cheminMessage = System.getProperty("user.dir") + "/res/main/Message.txt";

    public static void main(String[] args) throws InterruptedException {
        //String botName = "ORDI";
        boolean gameFinished = false;
        int tourJ1 = 0;
        int tourJ2 = 0;

        System.out.print(CLEAR);

        TimeUnit.SECONDS.sleep(1);

        System.out.print("Saisissez le nom du joueur 1 : ");
        String playerName1 = Saisie.getSaisie();


        System.out.print("Saisissez le nom du joueur 2 : ");
        String playerName2 = Saisie.getSaisie();

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

        diplayText(messages.get(14));
        TimeUnit.SECONDS.sleep(1);
        System.out.println();
        diplayText(messages.get(15));

        TimeUnit.SECONDS.sleep(3);

        System.out.print(CLEAR);
        TimeUnit.SECONDS.sleep(1);
        System.out.println(messages.get(12));
        TimeUnit.SECONDS.sleep(1);
        System.out.print(CLEAR);

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

            System.out.println(messages.get(5).replace("{Player}", playerName1));
            TimeUnit.SECONDS.sleep(1);

            while (playerTurn && !gameFinished) {
                Missile missileJ1 = Missile.CLASSIC;
                System.out.println(messages.get(13));
                System.out.println(missileJ1.getAllMissiles());
                try {
                    missileJ1 = Missile.values()[Integer.parseInt(Saisie.getSaisie()) - 1];
                    System.out.println(CLEAR);
                    if (missileJ1.getDelay() != 0) {
                        if (tourJ1 % missileJ1.getDelay() != 0 || tourJ1 == 0) {
                            throw new WrongMissileSelectionException("Le missile de " + playerName1 + " n'est pas encore chargé !");
                        }
                    }
                } catch (WrongMissileSelectionException e) {
                    missileJ1 = Missile.CLASSIC;
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println("\u001B[31mUne erreur est survenue veuillez contacter le support !\u001B[00m");
                    System.exit(1);
                }

                System.out.println("Tour n°" + tourJ1);
                System.out.println("Carte de " + playerName2 + " :" + System.lineSeparator());
                System.out.println(playerPlateau.getHimPlateau(botPlateau) + System.lineSeparator());
                System.out.println("Carte de " + playerName1 + " :" + System.lineSeparator());
                System.out.println(playerPlateau.getStringPlateau(botPlateau) + System.lineSeparator());

                System.out.println("Missile sélectionné : " + missileJ1);

                System.out.print("Entrez les coordonnées de tir (ex : A5) : ");
                String tir = Saisie.getPositionTir();

                System.out.print(CLEAR);
                TimeUnit.SECONDS.sleep(1);

                int x = tir.charAt(0) - 'A';
                int y = Integer.parseInt(tir.substring(1)) - 1;

                if (botPlateau.shootAvailable(x, y, missileJ1, playerPlateau)) {
                    botPlateau.fire(x, y, missileJ1);
                    Bateau boat = botPlateau.getCase(x, y);
                    if (boat != null) {
                        if (boat.isSunk()) {
                            System.out.println(messages.get(1).replace("{Boat}", boat.getName()).replace("{Player}", playerName2));
                        } else {
                            System.out.println(messages.get(0).replace("{Player}", playerName2));
                        }
                        playerTurn = true;
                    } else {
                        System.out.println(messages.get(6).replace("{Player}", playerName1)); // Raté
                        playerTurn = false;
                    }
                } else {
                    System.out.println("Miss !");
                    playerTurn = false;
                }
                if (missileJ1 == Missile.HEAVY) {
                    if (y - 1 >= 0 && y - 1 <= 9)
                        playerPlateau.shooted(x, y - 1);
                    if (y + 1 >= 0 && y + 1 <= 9)
                        playerPlateau.shooted(x, y + 1);
                    playerPlateau.shooted(x, y);
                } else if (missileJ1 != Missile.RECO) {
                    playerPlateau.shooted(x, y);
                }

                gameFinished = botPlateau.checkIfGameFinished();
                if (gameFinished) {
                    System.out.println(messages.get(3));
                    break;
                }
                tourJ1++;
                TimeUnit.SECONDS.sleep(1);
                System.out.print(CLEAR);
                TimeUnit.SECONDS.sleep(1);
            }

            if (!gameFinished) {
                boolean botTurn = true;


                System.out.println(messages.get(5).replace("{Player}", playerName2));
                TimeUnit.SECONDS.sleep(1);

                while (botTurn && !gameFinished) {
                    Missile missileJ2 = Missile.CLASSIC;
                    System.out.println(messages.get(13));
                    System.out.println(missileJ2.getAllMissiles());
                    try {
                        missileJ2 = Missile.values()[Integer.parseInt(Saisie.getSaisie()) - 1];
                        System.out.println(CLEAR);
                        if (missileJ2.getDelay() != 0) {
                            if (tourJ2 % missileJ2.getDelay() != 0 || tourJ2 == 0) {
                                throw new WrongMissileSelectionException("Le missile de " + playerName2 + " n'est pas encore chargé !");
                            }
                        }
                    } catch (WrongMissileSelectionException e) {
                        missileJ2 = Missile.CLASSIC;
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        System.out.println("\u001B[31mUne erreur est survenue veuillez contacter le support !\u001B[00m");
                        System.exit(1);
                    }

                    System.out.println("Tour n°" + tourJ2);
                    System.out.println("Carte de " + playerName1 + " :" + System.lineSeparator());
                    System.out.println(botPlateau.getHimPlateau(playerPlateau) + System.lineSeparator());
                    System.out.println("Carte de " + playerName2 + " :" + System.lineSeparator());
                    System.out.println(botPlateau.getStringPlateau(playerPlateau) + System.lineSeparator());

                    System.out.println("Missile sélectionné : " + missileJ2);

                    System.out.print("Entrez les coordonnées de tir (ex : A5) : ");
                    String tir = Saisie.getPositionTir();

                    System.out.print(CLEAR);
                    TimeUnit.SECONDS.sleep(1);

                    int botX = tir.charAt(0) - 'A';
                    int botY = Integer.parseInt(tir.substring(1)) - 1;


                    if (playerPlateau.shootAvailable(botX, botY, missileJ2, botPlateau)) {
                        playerPlateau.fire(botX, botY, missileJ2);
                        Bateau boat = playerPlateau.getCase(botX, botY);
                        if (boat != null) {
                            if (boat.isSunk()) {
                                System.out.println(messages.get(1).replace("{Boat}", boat.getName()).replace("{Player}", playerName1));
                            } else {
                                System.out.println(messages.get(0).replace("{Player}", playerName1));
                            }
                            botTurn = true;
                        } else {
                            System.out.println(messages.get(6).replace("{Player}", playerName2));
                            botTurn = false;
                        }
                    } else {
                        System.out.println("Miss !");
                        botTurn = false;
                    }
                    if (missileJ2 == Missile.HEAVY) {
                        if (botY - 1 >= 0 && botY - 1 <= 9)
                            botPlateau.shooted(botX, botY - 1);
                        if (botY + 1 >= 0 && botY + 1 <= 9)
                            botPlateau.shooted(botX, botY + 1);
                        botPlateau.shooted(botX, botY);
                    } else if (missileJ2 != Missile.RECO) {
                        botPlateau.shooted(botX, botY);
                    }

                    gameFinished = playerPlateau.checkIfGameFinished();
                    if (gameFinished) {
                        System.out.println(messages.get(4));
                        break;
                    }
                    TimeUnit.SECONDS.sleep(1);
                    System.out.print(CLEAR);
                    TimeUnit.SECONDS.sleep(1);
                }
            }
            tourJ2++;
            TimeUnit.SECONDS.sleep(1);
            System.out.print(CLEAR);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    static void diplayText(String texte) throws InterruptedException {
        for (int i = 0; i < texte.length(); i++) {
            System.out.print(texte.charAt(i));
            TimeUnit.MILLISECONDS.sleep(TEXTSPEED);
        }
    }

}