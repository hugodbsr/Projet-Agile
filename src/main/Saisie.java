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

    public static String getPositionTir(){
        boolean correct = false;
        String texte = "";
        while(!correct){
            texte = getSaisie();
            if(texte.length()>1 || texte.length()<4){
                if((texte.charAt(0)>='A' && texte.charAt(0)>='Z') || (texte.charAt(0)>='a' && texte.charAt(0)>='z')){
                    texte = Character.toUpperCase(texte.charAt(0)) + texte.substring(1);
                    if((texte.charAt(1)>='1' && texte.charAt(1)>='9')){
                        if((texte.length()==3) && texte.charAt(1)=='1'){
                            if((texte.charAt(2)=='0')){
                                correct = true;
                            }
                        }
                    }
                }
                System.err.println("Veillez entrer des coordonnées de tir dans le bon format (ex : A5)");             
            }
        }
        return texte;
    }
}
