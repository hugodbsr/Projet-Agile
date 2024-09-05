package main;

public class MainMenu {
    public static void main(String[] args) {
        System.out.println("Welcome to Sink The Boat !" + System.getProperty("line.separator"));

        while(true) {
            System.out.println("Choissisez votre mode de jeu :");
            System.out.println("\t1. Vs Bot");
            System.out.println("\t2. Vs autre joueur");

            int choix = verifChoix();
            try {
                if(choix == 1) {
                    MainBot.main(args);
                } else {
                    MainLocal.main(args);
                }
            } catch (InterruptedException e) {
                System.out.println("Main Bot Interrupted !");
            }
        }
    }

    public static int verifChoix(){
        try{
            return Integer.parseInt(Saisie.getSaisie());
        }catch (NumberFormatException e) {
            System.out.println("Erreur choix !");
            System.out.println("Saisissez 1 ou 2 :");
            return verifChoix();
        }
    }
}
