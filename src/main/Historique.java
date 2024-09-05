package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Historique {
    private static String filePath = "";
    private static File file;
    private static String playername_p1 = "";
    private static String playername_p2 = "";

    // public static boolean createGameFile(String name) {
    //     Historique.playername_p1 = name;
    //     Historique.filePath = System.getProperty("user.dir") + "/res/historique/"
    //             + LocalDateTime.now().toString().substring(0, 19).replace("T", "_") + "_joueur_" + playername_p1 + ".txt";
    //     Historique.file = new File(filePath);
    //     try (BufferedWriter bw = new BufferedWriter(new FileWriter(Historique.file))) {
    //         bw.write("-------------------------------------------------------");
    //         bw.newLine();
    //         bw.newLine();
    //         bw.write("Partie de " + playername_p1 + " le " + LocalDateTime.now().toString());
    //         bw.newLine();
    //         bw.newLine();
    //         bw.write("-------------------------------------------------------");
    //         bw.newLine();
    //         bw.newLine();
    //         bw.newLine();
    //     } catch (Exception e) {
    //         System.err.println("Erreur lors de la création du fichier d'historique de la partie.");
    //         e.printStackTrace();
    //         return false;
    //     }
    //     return true;
    // }

    public static boolean createGameFile(String name_p1, String name_p2) {
        Historique.playername_p1 = name_p1;
        Historique.playername_p2 = name_p2;
        Historique.filePath = System.getProperty("user.dir") + "/res/historique/"
                + LocalDateTime.now().toString().substring(0, 19).replace("T", "_") + "_joueurs_" + playername_p1 + "_" + playername_p2 + ".txt";
        Historique.file = new File(filePath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Historique.file))) {
            bw.write("-------------------------------------------------------");
            bw.newLine();
            bw.newLine();
            bw.write("Partie de " + playername_p1 + " et de " + playername_p2 + " le " + LocalDateTime.now().toString().substring(0, 19).replace("T", " à "));
            bw.newLine();
            bw.newLine();
            bw.write("-------------------------------------------------------");
            bw.newLine();
            bw.newLine();
            bw.newLine();
        } catch (Exception e) {
            System.err.println("Erreur lors de la création du fichier d'historique de la partie.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean addString(String msg) {
        String ancienTexte = "";
        try (BufferedReader br = new BufferedReader(new FileReader(Historique.file))) {
            while (br.ready()) {
                ancienTexte += br.readLine() + '\n';
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Historique.file))) {
            bw.write(ancienTexte);
            bw.write(msg);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout de texte au fichier d'historique de la partie.");
            return false;
        }
        return true;
    }

    // public static boolean addPlateau(Plateau playerPlateau, Plateau botPlateau) {
    //     String msg = "Bateaux de " + Historique.playername_p1 + '\n'
    //             + playerPlateau.getStringPlateau(botPlateau).replace("\u001B[", "").replace("0m", "").replace("31m", "")
    //                     .replace("32m", "").replace("33m", "").replace("34m", "")
    //             + "\n\n"
    //             + "Bateaux de l'ORDI" + '\n'
    //             + botPlateau.getStringPlateau(playerPlateau).replace("\u001B[", "").replace("0m", "").replace("31m", "")
    //                     .replace("32m", "").replace("33m", "").replace("34m", "")
    //             + "\n\n\n";
    //     return Historique.addString(msg);
    // }

    public static boolean addPlateau(Plateau playerPlateau, Plateau playerPlateau2) {
        String msg = "Bateaux de " + Historique.playername_p1 + '\n'
                + playerPlateau.getStringPlateau(playerPlateau2).replace("\u001B[", "").replace("0m", "").replace("31m", "")
                        .replace("32m", "").replace("33m", "").replace("34m", "")
                + "\n\n"
                + "Bateaux de " + Historique.playername_p2 + '\n'
                + playerPlateau2.getStringPlateau(playerPlateau).replace("\u001B[", "").replace("0m", "").replace("31m", "")
                        .replace("32m", "").replace("33m", "").replace("34m", "")
                + "\n\n\n";
        return Historique.addString(msg);
    }

    // méthode de test de la classe historique.

    /*
     * public static void main(String[] args) {
     * Historique.createGameFile("florine");
     * 
     * Plateau playerPlateau = new Plateau(true);
     * Plateau botPlateau = new Plateau(false);
     * 
     * playerPlateau.placerBateau(3, 'C', new Croiseur(), true);
     * playerPlateau.placerBateau(8, 'E', new Croiseur(), false);
     * playerPlateau.placerBateau(6, 'F', new Cuirasser(), true);
     * playerPlateau.placerBateau(2, 'B', new Destroyer(), false);
     * playerPlateau.placerBateau(4, 'G', new PorteAvion(), true);
     * 
     * botPlateau.placerBateau(3, 'A', new PorteAvion(), true);
     * botPlateau.placerBateau(8, 'E', new Cuirasser(), false);
     * botPlateau.placerBateau(6, 'F', new Destroyer(), true);
     * botPlateau.placerBateau(2, 'B', new Croiseur(), false);
     * botPlateau.placerBateau(4, 'G', new Croiseur(), true);
     * 
     * Historique.addPlateau(playerPlateau, botPlateau);
     * Historique.addString("{Number} bateau ont été détecté dans cette zone !");
     * Historique.addString("{test} bateau ont été détecté dans cette zone !");
     * 
     * Historique.createGameFile("pasdutoutflorine");
     * 
     * playerPlateau = new Plateau(true);
     * botPlateau = new Plateau(false);
     * 
     * playerPlateau.placerBateau(3, 'C', new Croiseur(), true);
     * playerPlateau.placerBateau(8, 'E', new Croiseur(), false);
     * playerPlateau.placerBateau(6, 'F', new Cuirasser(), true);
     * playerPlateau.placerBateau(2, 'B', new Destroyer(), false);
     * playerPlateau.placerBateau(4, 'G', new PorteAvion(), true);
     * 
     * botPlateau.placerBateau(3, 'A', new PorteAvion(), true);
     * botPlateau.placerBateau(8, 'E', new Cuirasser(), false);
     * botPlateau.placerBateau(6, 'F', new Destroyer(), true);
     * botPlateau.placerBateau(2, 'B', new Croiseur(), false);
     * botPlateau.placerBateau(4, 'G', new Croiseur(), true);
     * 
     * Historique.addPlateau(playerPlateau, botPlateau);
     * Historique.addString("{Number} bateau ont été détecté dans cette zone !");
     * Historique.addString("{test} bateau ont été détecté dans cette zone !");
     * }
     */
}
