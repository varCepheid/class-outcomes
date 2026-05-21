/*
 * Alex Vennebush
 * vennebush@chapman.edu
 * ID: 2497086
 * CPSC 231-04 Computer Science II
 * 26 April 2026
 * Mastery Project #4: Card Game
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Creates objects to simulate the gameplay of one game of Egyptian Rat Screw.
 */
public class Driver {
  public static void main(String[] args) {
    Game game;

    // number of players as command-line argument
    // if no argument provided, play with 2
    try {
      game = new Game(Integer.parseInt(args[0]));
    } catch (Exception e) {
      game = new Game();
    }

    PrintWriter outFile = null;
    try {
      outFile = new PrintWriter(new FileWriter("output.txt"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("Player #" + game.play(outFile) + " won the game. The game logs are in the file output.txt.");

    outFile.close();
  }
}
