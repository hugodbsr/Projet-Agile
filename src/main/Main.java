package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Main
 */
public class Main {

    final static String CLEAR = String.format("\033[2J");
    final static String cheminMessage = System.getProperty("user.dir") + "/res/main/Message.txt";

    public static void main(String[] args) {
        System.out.print(CLEAR);

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

        System.out.println(messages);

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

        System.out.println(playerCroiseur.getName());
        System.out.println(playerCuirasser.getName());
        System.out.println(playerDestroyer.getName());
        System.out.println(playerPorteAvion.getName());

        //création des bateaux du bot
        Croiseur botCroiseur = new Croiseur();
        Croiseur botCroiseur2 = new Croiseur();
        Cuirasser botCuirasser = new Cuirasser();
        Destroyer botDestroyer = new Destroyer();
        PorteAvion botPorteAvion = new PorteAvion();

        botPlateau.setBateau(3, 3, botPorteAvion);

        tryShoot(playerPlateau, botPlateau, 3, 3);

        System.out.println(botPlateau.getStringPlateau());
        System.out.println(playerPlateau.getHimPlateau(botPlateau));
    }

    public static void tryShoot(Plateau shooter, Plateau target, int x, int y){
        if(target.shootAvailable(x, y)){
            target.fire(x, y);
            shooter.shooted(x, y);
        }
    }
}