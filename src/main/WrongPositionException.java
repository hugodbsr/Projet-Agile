package main;

public class WrongPositionException extends Exception {
    public WrongPositionException() {
        System.out.println("Mauvais placement d'un bateau !");
    }

    public WrongPositionException(String s) {
        System.out.println(s);
    }

    public WrongPositionException(String s, Throwable t) {
        System.out.println(s);
        System.out.println(t);
    }
}
