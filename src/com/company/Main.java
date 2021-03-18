package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Tetris myGame = new Tetris();
        Scanner scanner = new Scanner(System.in);
        char choice;

        System.out.println(myGame);

        while (myGame.getGameState() != GameState.OVER) {
            System.out.println("Dove vuoi muovere? (A/S/D/X): ");
            choice = scanner.nextLine().toUpperCase().charAt(0);


            myGame.play(choice);

            System.out.println(myGame);

        }

    }
}
