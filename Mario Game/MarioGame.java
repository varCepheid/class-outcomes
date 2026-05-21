/*
 * Alex Vennebush
 * vennebush@chapman.edu
 * ID: 2497086
 * CPSC 231-04 Computer Science II
 * Mastery Project #3 (Substitute): Not-So-Super Mario Bros.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MarioGame {
  // global variables
  static Mario mario;
  static char[][][] world; // X increases to the right; Y increases going down
  static int gridSize, numLevels, level; // level begins at 1, but array starts at 0
  static double coinThresh, noneThresh, goombaThresh, koopaThresh, shroomThresh;
  static int totalMoves;

  /**
   * Handle the effects of one move.
   * 
   * @param dir The direction Mario moves. 0 is up, 1 is right, 2 is down, 3 is
   *            left, and 4 is none.
   * @returns A message describing the result of the move.
   */
  public static String move(int dir) {
    // move Mario to next position
    switch (dir) {
      case 0:
        mario.moveUp();
        break;
      case 1:
        mario.moveRight();
        break;
      case 2:
        mario.moveDown();
        break;
      case 3:
        mario.moveLeft();
        break;
    }

    // determine what is there
    char atThisPlace = world[level - 1][mario.getPosY()][mario.getPosX()];

    // resolve the correct action for what is there
    switch (atThisPlace) {
      case 'x': // nothing
        return "did nothing";
      case 'c': // coin
        mario.gainCoin();
        world[level - 1][mario.getPosY()][mario.getPosX()] = 'x';
        return "collected a coin";
      case 'm': // mushroom
        mario.changePower(1);
        world[level - 1][mario.getPosY()][mario.getPosX()] = 'x';
        return "ate a mushroom";
      case 'g': // Goomba enemy
        if (fightEnemy(0.8, false)) {
          // Mario won, clear enemy
          world[level - 1][mario.getPosY()][mario.getPosX()] = 'x';
          return "fought a Goomba and won";
        } else {
          // Mario lost
          return "fought a Goomba and lost";
        }
      case 'k': // Koopa Troopa enemy, same structure as Goomba
        if (fightEnemy(0.65, false)) {
          world[level - 1][mario.getPosY()][mario.getPosX()] = 'x';
          return "fought a Koopa and won";
        } else {
          return "fought a Koopa and lost";
        }
      case 'b': // boss enemy, same structure as Goomba
        if (fightEnemy(0.5, true)) {
          world[level - 1][mario.getPosY()][mario.getPosX()] = 'x';
          return "fought the boss and won";
        } else {
          return "fought the boss and lost";
        }
      case 'w': // warp pipe
        return "warped";
      default: // something else? just to catch problems
        return "";
    }
  }

  /**
   * Handle the effects of moving onto an enemy.
   * 
   * @param prob Probability of winning the fight.
   * @returns Whether or not Mario won.
   */
  public static boolean fightEnemy(double prob, boolean boss) {
    // determine win or loss by random chance
    boolean win = Math.random() < prob;

    if (win) {
      // Mario won, report a defeated enemy
      mario.defeatEnemy();
      return true;
    } else {
      // Mario lost, lose power (which triggers loss of life)
      mario.changePower(boss ? -2 : -1);
      return false;
    }
  }

  /**
   * Prints to the output file the current level and Mario's power level before
   * his move.
   * 
   * @param output Object that can write to the output file.
   */
  public static void printBeforeMove(PrintWriter output) {
    output.print("Level " + level + " | ");
    output.println("Mario's power level: PL" + mario.getPower());
  }

  /**
   * Prints to the output file Mario's new position after moving, the outcome of
   * his move, his current number of lives and coins, and which direction he will
   * move next.
   * 
   * @param output  Object that can write to the output file.
   * @param outcome A description of the outcome of Mario's last move.
   * @param nextDir The direction that Mario will move next. 0 is up, 1 is right,
   *                2 is down, 3 is left, and 4 is none.
   */
  public static void printAfterMove(PrintWriter output, String outcome, int nextDir) {
    // print position and outcome -- posX and posY are zero-indexed
    output.print("Mario is now at row " + (mario.getPosY() + 1) + ", column " + (mario.getPosX() + 1) + ". ");
    output.println("After moving here, Mario " + outcome + ".");

    // print lives, coins, and enemies defeated
    output.print("Mario now has " + mario.getLives() + " lives and " + mario.getCoins() + " coins. ");
    output
        .println("Mario has defeated " + mario.getEnemiesDefeated() + " enemies since last gaining or losing a life.");

    if (!outcome.equals("warped")) {
      // print next move-direction if still in this leveel
      output.print("Next, Mario will ");
      // handle direction information stored as int
      switch (nextDir) {
        case 0:
          output.println("move UP.");
          break;
        case 1:
          output.println("move RIGHT.");
          break;
        case 2:
          output.println("move DOWN.");
          break;
        case 3:
          output.println("move LEFT.");
          break;
        case 4:
          output.println("STAY PUT.");
          break;
      }
    }
  }

  /**
   * Prints to the output file the current level layout.
   * 
   * @param output Object that can write to the output file.
   */
  public static void printLevel(PrintWriter output) {
    output.println("CURRENT LEVEL LAYOUT:");
    // iterate over rows and columns
    for (int row = 0; row < gridSize; row++) {
      for (int col = 0; col < gridSize; col++) {
        if ((row == mario.getPosY()) && (col == mario.getPosX())) {
          // Mario is here
          output.print("H ");
        } else {
          // Mario is not here
          // print character at row and column in world-grid
          output.print(world[level - 1][row][col] + " ");
        }
      }
      // end each row with new-line
      output.println();
    }
  }

  /**
   * Generate the world array.
   */
  public static void createWorld() {
    int warpX, warpY, bossX, bossY;
    double rand;

    // iterate over all levels
    for (int l = 0; l < numLevels; l++) {
      if (l == numLevels - 1) {
        // last level, so no warp pipe
        // -1s make sure comparison below fails
        warpX = -1;
        warpY = -1;
      } else {
        // warp pipe
        warpX = (int) Math.floor(Math.random() * gridSize);
        warpY = (int) Math.floor(Math.random() * gridSize);
        world[l][warpX][warpY] = 'w';
      }

      // level boss -- cannot be in same place as warp pipe
      do {
        bossX = (int) Math.floor(Math.random() * gridSize);
        bossY = (int) Math.floor(Math.random() * gridSize);
      } while ((bossX == warpX) && (bossY == warpY));
      world[l][bossX][bossY] = 'b';

      // fill in remaining spaces
      rand = 0.0;
      // iterate over whole world-grid
      for (int x = 0; x < gridSize; x++) {
        for (int y = 0; y < gridSize; y++) {
          // don't overwrite boss or warp pipe
          if (world[l][x][y] == 0) {
            // use thresholds to determine entry in this space
            rand = Math.random();
            if (rand < coinThresh) {
              world[l][x][y] = 'c';
            } else if (rand < noneThresh) {
              world[l][x][y] = 'x';
            } else if (rand < goombaThresh) {
              world[l][x][y] = 'g';
            } else if (rand < koopaThresh) {
              world[l][x][y] = 'k';
            } else {
              world[l][x][y] = 'm';
            }
          }
        }
      }
    }
  }

  /**
   * Display every level of the world.
   * 
   * @param output Object that can write to the output file.
   */
  public static void printWorld(PrintWriter output) {
    output.println("ALL LEVEL LAYOUTS:");
    // iterate over levels (first dimension of world array)
    for (int l = 0; l < numLevels; l++) {
      // iterate over rows and columns
      for (int row = 0; row < gridSize; row++) {
        for (int col = 0; col < gridSize; col++) {
          // print value at each space
          output.print(world[l][row][col] + " ");
        }
        // end each row with new-line
        output.println();
      }
      // extra new-line between each level
      output.println();
    }
  }

  /**
   * Place Mario at a random position in a level.
   * 
   * @param mario Mario
   */
  public static void placeMario() {
    int marioX, marioY;

    // determine Mario's position
    marioX = (int) Math.floor(Math.random() * gridSize);
    marioY = (int) Math.floor(Math.random() * gridSize);

    // set position values in Mario object
    mario.setPosX(marioX);
    mario.setPosY(marioY);
  }

  public static void main(String[] args) {
    // check for correct number of arguments
    if (args.length != 2) {
      System.err.println("Run command must include filenames for input file and output file.");
      return;
    }

    String inputFile = args[0];
    String outputFile = args[1];

    // SET UP I/O

    // get input values from input file
    Scanner inputRd = null;
    int initialLives;
    try {
      // create file reader
      inputRd = new Scanner(new File(inputFile));

      // first three are just the numbers in the file
      numLevels = inputRd.nextInt();
      gridSize = inputRd.nextInt();
      initialLives = inputRd.nextInt();

      // thresholds are cumulative, not individual
      coinThresh = inputRd.nextInt() / 100.0;
      noneThresh = (inputRd.nextInt() / 100.0) + coinThresh;
      goombaThresh = (inputRd.nextInt() / 100.0) + noneThresh;
      koopaThresh = (inputRd.nextInt() / 100.0) + goombaThresh;
      shroomThresh = (inputRd.nextInt() / 100.0) + koopaThresh;

      // verify that the total probability is 1
      if (Math.abs(shroomThresh - 1) > 0.001) {
        throw new ArithmeticException("Total probabilities of position entries do not add up to 1.");
      }
    } catch (FileNotFoundException e) {
      // exception if the given input file doesn't exist
      System.err.println("Unable to open file: " + inputFile);
      e.printStackTrace();
      return;
    } catch (ArithmeticException e) {
      // exception if the probabilities don't add to 1
      System.err.println(e.getMessage());
      e.printStackTrace();
      return;
    } catch (InputMismatchException e) {
      // exception if file values aren't integers
      System.err.println("Incorrect inputs given in: " + inputFile + ". Make sure all input values are integers.");
      e.printStackTrace();
      return;
    } finally {
      // close file reader
      if (inputRd != null) {
        inputRd.close();
      }
    }

    // System.out.println("Levels: " + numLevels + " Grid size: " + gridSize + "
    // Starting lives: " + initialLives);
    // System.out.println(
    // "Thresholds: " + coinThresh + " " + noneThresh + " " + goombaThresh + " " +
    // koopaThresh + " " + shroomThresh);

    // set up output file
    PrintWriter output = null;
    FileWriter fw = null;
    try {
      // create objects
      fw = new FileWriter(outputFile);
      output = new PrintWriter(fw);

      // no writing in here -- outputs will happen during gameplay
    } catch (IOException e) {
      // exception for FileWriter class
      System.err.println("Could not write to file: " + outputFile);
      e.printStackTrace();
      return;
    }

    // PREPARE FOR GAMEPLAY

    // instantiate Mario and world
    mario = new Mario(initialLives, gridSize);
    world = new char[numLevels][gridSize][gridSize];

    // create and display world
    createWorld();
    printWorld(output);

    // set variables to beginning
    totalMoves = 0;
    level = 1;
    placeMario();

    int nextDir = 4;
    String outcome;

    // GAMEPLAY

    // loop stops when Mario dies or at last level
    while (true) {
      // print level number and power level
      printBeforeMove(output);

      // move to next position
      outcome = move(nextDir);
      totalMoves++;

      // determine next move direction
      nextDir = (int) Math.floor(Math.random() * 4); // 0 is up, 1 is right, 2 is down, 3 is left, 4 is "stay put"

      // stay put if lost to boss
      if (outcome.equals("fought the boss and lost")) {
        nextDir = 4;
      }

      // print other move-information
      printAfterMove(output, outcome, nextDir);

      if (outcome.equals("warped")) {
        // handle warp: next level, Mario random spot
        level++;
        placeMario();
        nextDir = 4;
      } else if ((level == numLevels) && outcome.equals("fought the boss and won")) {
        // last level and defeated boss --> win message and stop playing
        output.println();
        output.println("Mario defeated the final boss and saved the princess! That's a win!");
        break;
      } else if (!mario.isAlive()) {
        // no lives left --> loss message and stop playing
        output.println();
        output.println("Oh no! Mario is out of lives, which is a loss.");
        break;
      } else {
        // none of these situations: print new level layout
        printLevel(output);
      }
      output.println();
    }

    // report total moves
    output.println("The game took " + totalMoves + " moves to finish.");

    // close output file
    output.close();
  }
}