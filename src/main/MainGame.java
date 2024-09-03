package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MainGame {

    final static String CLEAR = String.format("\033[2J");
    final static String cheminMessage = System.getProperty("user.dir") + "/res/main/Message.txt";

    public static void main(String[] args) throws InterruptedException {
        String botName = "ORDI";

        System.out.print(CLEAR);
        
        TimeUnit.SECONDS.sleep(1);

        System.out.println("Saisissez votre nom :");
        String playerName = Saisie.getSaisie();

        System.out.print(CLEAR);

        TimeUnit.SECONDS.sleep(1);

        //recuperation des messages
        ArrayList<String> messages = new ArrayList<String>();
        try(BufferedReader bw = new BufferedReader(new FileReader(new File(cheminMessage)))){
            while(bw.ready()){
                messages.add(bw.readLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (Exception e) {
            System.err.println(e);
        }


        Plateau playerPlateau = new Plateau(true);

        Plateau botPlateau = new Plateau(false);

        Croiseur playerCroiseur = new Croiseur();
        Croiseur playerCroiseur2 = new Croiseur();
        Cuirasser playerCuirasser = new Cuirasser();
        Destroyer playerDestroyer = new Destroyer();
        PorteAvion playerPorteAvion = new PorteAvion();

        //création des bateaux du bot
        Croiseur botCroiseur = new Croiseur();
        Croiseur botCroiseur2 = new Croiseur();
        Cuirasser botCuirasser = new Cuirasser();
        Destroyer botDestroyer = new Destroyer();
        PorteAvion botPorteAvion = new PorteAvion();

        botPlateau.placerBateau(3, 'A', botPorteAvion);
        botPlateau.placerBateau(8, 'E', botCuirasser);
        botPlateau.placerBateau(6, 'F', botDestroyer);
        botPlateau.placerBateau(2, 'B', botCroiseur2);
        botPlateau.placerBateau(4, 'G', botCroiseur);


        playerPlateau.placerBateau(3, 'C', playerCroiseur);
        playerPlateau.placerBateau(8, 'E', playerCroiseur2);
        playerPlateau.placerBateau(6, 'F', playerCuirasser);
        playerPlateau.placerBateau(2, 'B', playerDestroyer);
        playerPlateau.placerBateau(4, 'G', playerPorteAvion);


        System.out.println("Carte de " + botName + " :");
        System.out.println(playerPlateau.getHimPlateau(botPlateau));
        System.out.println("Carte de " + playerName + " :");
        System.out.println(playerPlateau.getStringPlateau());

    }
}
