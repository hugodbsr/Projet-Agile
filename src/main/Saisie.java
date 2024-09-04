package main;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Saisie {

    public static String getSaisie(){
        try (Scanner sc = new Scanner(System.in)){
            String texte = sc.next();
            return texte;
        } catch (IllegalStateException e) {
            System.out.println(e.getStackTrace());
        } catch (NoSuchElementException e) {
            System.out.println(e.getStackTrace());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return getSaisie();
    }
}
