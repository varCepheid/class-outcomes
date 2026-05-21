public class Mario {
  private int lives, coins, power, posX, posY, enemiesDefeated;
  final private int gridSize;

  /**
   * Instantiates Mario with 0 coins and power level 0.
   * 
   * @param startingLives initial value of lives
   * @param size          value of gridSize, just for reference
   */
  public Mario(int startingLives, int size) {
    lives = startingLives;
    coins = 0;
    power = 0;
    gridSize = size;
    enemiesDefeated = 0;
  }

  // This class has a large number of basic getter and setter methods that I don't
  // feel the need to explain.

  public int getLives() {
    return lives;
  }

  /**
   * Increments the number of lives.
   */
  public void addLife() {
    lives++;
    enemiesDefeated = 0;
  }

  /**
   * Decrements the number of lives.
   */
  public void loseLife() {
    lives--;
    enemiesDefeated = 0;
  }

  public int getCoins() {
    return coins;
  }

  public boolean isAlive() {
    return lives > 0;
  }

  /**
   * Adds one to Mario's coin count.
   * If Mario has 20 coins, gain a life and reset to 0 coins.
   */
  public void gainCoin() {
    coins++;

    if (coins >= 20) {
      addLife();
      coins = 0;
    }
  }

  public int getPower() {
    return power;
  }

  /**
   * Changes power level by the argument. Mario loses a life if power goes below
   * 0. Power cannot be above 2.
   * 
   * @param change The number of power levels to gain or lose.
   */
  public void changePower(int change) {
    power += change;

    if (power < 0) {
      power = 0;
      loseLife();
    }

    if (power > 2) {
      power = 2;
    }
  }

  public int getPosX() {
    return posX;
  }

  public void setPosX(int newPos) {
    posX = newPos;
  }

  public int getPosY() {
    return posY;
  }

  public void setPosY(int newPos) {
    posY = newPos;
  }

  /**
   * Decrements posX by 1, and resets to the right side of the grid if posX is
   * less than 0.
   */
  public void moveLeft() {
    posX--;

    if (posX < 0) {
      posX = gridSize - 1;
    }
  }

  /**
   * Increments posX by 1, and resets to the left side of the grid if posX is
   * greater than the size of the grid.
   */
  public void moveRight() {
    posX++;

    if (posX >= gridSize) {
      posX = 0;
    }
  }

  /**
   * Decrements posY by 1, and resets to the bottom of the grid if posY is
   * less than 0.
   */
  public void moveUp() {
    posY--;

    if (posY < 0) {
      posY = gridSize - 1;
    }
  }

  /**
   * Increments posY by 1, and resets to the top of the grid if posY is
   * greater than the size of the grid.
   */
  public void moveDown() {
    posY++;

    if (posY >= gridSize) {
      posY = 0;
    }
  }

  public int getEnemiesDefeated() {
    return enemiesDefeated;
  }

  /**
   * Adds 1 to the running total of defeated enemies. If enemiesDefeated reaches
   * 7, Mario gains a life, and enemiesDefeated is reset to 0.
   */
  public void defeatEnemy() {
    enemiesDefeated++;

    if (enemiesDefeated >= 7) {
      addLife();
      enemiesDefeated = 0;
    }
  }
}
