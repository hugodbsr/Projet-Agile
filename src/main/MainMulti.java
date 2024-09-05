package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.concurrent.TimeUnit;

/**
 * MainMulti
 */
public class MainMulti {
    private final static String PATH = "res/main/";
    private static String room, player1, player2, currentPlayer, roomFile;
    private final static String CLEAR = String.format("\033[2J");
    private static Plateau ourPlt1, ourPlt2, plt1, plt2;

    public static String loadFile(String file) {
        String res = new String();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(PATH + file)))) {
            while (br.ready()) {
                res += br.readLine() + '\n';
            }
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            System.out.println("\u001B[31mUne erreur est survenue veuillez contacter le support !\u001B[00m");
        }
        return res;
    }

    public static int checkRoom(String name) {
        String rooms = loadFile("Multi.csv");
        String[] tab = rooms.split("\n");
        for (int i = 0; i < tab.length; i++) {
            if (tab[i].split(";")[0].equals(name)) return i;
        }
        return -1;
    }

    public static boolean createGame(String room, String player1) {
        String multi = loadFile("Multi.csv");
        try {
            FileWriter writer = new FileWriter(PATH + "Multi.csv");
            writer.write(multi + room + ";" + player1 + ";");
            writer.close();
        } catch (Exception e) {
            System.out.println("\u001B[31mUne erreur est survenue veuillez contacter le support !\u001B[00m");
            return false;
        }
        return true;
    }

    public static boolean addPlayer(int idx, String player2) {
        try {
            String multi = loadFile("Multi.csv");
            String[] tab = multi.split("\n");
            if (tab[idx].split(";").length == 3) throw new RoomFullException("\u001B[31mLa salle est remplie !\u001B[00m");
            tab[idx] = tab[idx].split(";")[0] + ";" + tab[idx].split(";")[1] + ";" + player2;
            FileWriter writer = new FileWriter(PATH + "Multi.csv");
            multi = new String();
            for (String room : tab) {
                multi += room + '\n';
            }
            writer.write(multi);
            writer.close();
        } catch (RoomFullException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("\u001B[31mUne erreur est survenue veuillez contacter le support !\u001B[00m");
            System.exit(1);
        }
        return true;
    }

    public static boolean checkPLayer2() {
        String[] rooms = loadFile("Multi.csv").split("\n");
        int idx = checkRoom(room);
        if (rooms[idx].split(";").length == 3) return true;
        return false;
    }
    
    public static void gamePlayer1() {
        int waitMsg = 0;
        try {
            while (!checkPLayer2()) {
                System.out.println(CLEAR);
                System.out.println("Bonjour \u001B[35m" + currentPlayer + "\u001B[00m, bienvenue dans \u001B[36mSink The Boat\u001B[00m !");
                String waitingMsg = new String();
                for (int i = 0; i < waitMsg; i++) {
                    waitingMsg += '.';
                }
                System.out.println("En attente d'un joueur " + waitingMsg);
                TimeUnit.SECONDS.sleep(1);
                waitMsg++;
                waitMsg %= 4;
            }
        } catch (Exception e) {
            System.err.println("\u001B[31mUne erreur est survenue veuillez contacter le support !\u001B[00m");
            System.exit(1);
        }
        createRoom();
        System.out.println(CLEAR);
        System.out.println("\u001B[31m" + loadFile("Multi.csv").split("\n")[checkRoom(room)].split(";")[1] + "\u001B[00m vs \u001B[34m" + loadFile("Multi.csv").split("\n")[checkRoom(room)].split(";")[2] + "\u001B[00m");
        ourPlt1 = new Plateau(true);
        plt2 = new Plateau(false);
        String posBat = Saisie.getPositionBateau();
        if (posBat.equals("R")) {
            DeroulementDuJeu.placeShipsRandomly(ourPlt1);
        } else {
            DeroulementDuJeu.placeShipsManually(ourPlt1, plt2);
        }

        try {  
            String roomCsv = loadFile(roomFile + ".csv");
            String tab = new String();
            for (Bateau[] iterable_element : ourPlt1.getPlateau()) {
                for (Bateau bateau : iterable_element) {
                    if (bateau == null) {
                        tab += bateau + "/";
                    } else {
                        tab += bateau.getClass().getSimpleName() + "/";
                    }
                }
            }
            tab = tab.substring(0, tab.length() - 1);
            String res = roomCsv.split("\n")[0].split(";")[0] + ";" + tab + ";;\n" + roomCsv.split("\n")[1];
            FileWriter writer = new FileWriter(PATH + roomFile + ".csv");
            writer.write(res);
            writer.close();
        } catch (Exception e) {
            System.err.println("\u001B[31mUne erreur est survenue veuillez contacter le support !\u001B[00m");
            System.exit(1);
        }
    }
    
    public static void gamePlayer2() {
        System.out.println(CLEAR);
        System.out.println("\u001B[31m" + loadFile("Multi.csv").split("\n")[checkRoom(room)].split(";")[1] + "\u001B[00m vs \u001B[34m" + loadFile("Multi.csv").split("\n")[checkRoom(room)].split(";")[2] + "\u001B[00m");
        ourPlt2 = new Plateau(true);
        plt1 = new Plateau(false);
        String posBat = Saisie.getPositionBateau();
        if (posBat.equals("R")) {
            DeroulementDuJeu.placeShipsRandomly(ourPlt2);
        } else {
            DeroulementDuJeu.placeShipsManually(ourPlt2, plt1);
        }

        try {  
            String roomCsv = loadFile(roomFile + ".csv");
            String tab = new String();
            for (Bateau[] iterable_element : ourPlt2.getPlateau()) {
                for (Bateau bateau : iterable_element) {
                    if (bateau == null) {
                        tab += bateau + "/";
                    } else {
                        tab += bateau.getClass().getSimpleName() + "/";
                    }
                }
            }
            tab = tab.substring(0, tab.length() - 1);
            String res = roomCsv.split("\n")[0] + "\n" + roomCsv.split("\n")[1].split(";")[0] + ";" + tab + ";;";
            FileWriter writer = new FileWriter(PATH + roomFile + ".csv");
            writer.write(res);
            writer.close();
        } catch (Exception e) {
            System.err.println("\u001B[31mUne erreur est survenue veuillez contacter le support !\u001B[00m");
            System.exit(1);
        }
    }

    public static void createRoom() {
        try {  
            FileWriter writer = new FileWriter(PATH + "R" + room + ".csv");
            writer.write(loadFile("Multi.csv").split("\n")[checkRoom(room)].split(";")[1] + ";;;\n" + loadFile("Multi.csv").split("\n")[checkRoom(room)].split(";")[2] + ";;;");
            writer.close();
        } catch (Exception e) {
            System.err.println("\u001B[31mUne erreur est survenue veuillez contacter le support !\u001B[00m");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        System.out.print("Saisir le nom d'une salle : ");
        room = Saisie.getSaisie().toLowerCase();
        System.out.print("Quelle est votre pseudo ? : ");
        int roomIdx = checkRoom(room);
        roomFile = "Rooms/R" + room;
        if (roomIdx == -1) {
            player1 = Saisie.getSaisie().toLowerCase();
            createGame(room, player1);
            currentPlayer = player1;
            gamePlayer1();
        } else {
            player2 = Saisie.getSaisie().toLowerCase();
            addPlayer(roomIdx, player2);
            currentPlayer = player2;
            gamePlayer2();
        }
    }
}