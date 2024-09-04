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
    private static String room, player1, player2, currentPlayer;
    final static String CLEAR = String.format("\033[2J");

    public static String loadFile() {
        String res = new String();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(PATH + "Multi.csv")))) {
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
        String rooms = loadFile();
        String[] tab = rooms.split("\n");
        for (int i = 0; i < tab.length; i++) {
            if (tab[i].split(";")[0].equals(name)) return i;
        }
        return -1;
    }

    public static boolean createGame(String room, String player1) {
        String multi = loadFile();
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
            String multi = loadFile();
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
            System.out.println(e);
            System.err.println("\u001B[31mUne erreur est survenue veuillez contacter le support !\u001B[00m");
            System.exit(1);
        }
        return true;
    }

    public static boolean checkPLayer2() {
        String[] rooms = loadFile().split("\n");
        int idx = checkRoom(room);
        if (rooms[idx].split(";").length == 3) return true;
        return false;
    }
    
    public static void gamePlayer1() {
        int waitMsg = 0;
        try {
            while (checkPLayer2()) {
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
    }
    
    public static void gamePlayer2() {
        System.out.println(CLEAR);
    }

    public static void main(String[] args) {
        System.out.print("Saisir le nom d'une salle : ");
        room = Saisie.getSaisie().toLowerCase();
        System.out.print("Quelle est votre pseudo ? : ");
        int roomIdx = checkRoom(room);
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