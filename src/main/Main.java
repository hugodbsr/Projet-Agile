package main;

/**
 * Main
 */
public class Main {
    public static void main(String[] args) {
        System.out.print(String.format("\033[2J"));
        Plateau plt = new Plateau(true);
        System.out.println(plt.getStringPlateau());
    }
}