package main;

import java.util.Scanner;

public class Saisie {

    public static String getSaisie(){
        Scanner sc = new Scanner(System.in);
        String texte = sc.next();
        return texte;
    }
}
