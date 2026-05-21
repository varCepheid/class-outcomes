/*
 * Alex Vennebush
 * vennebush@chapman.edu
 * ID: 2497086
 * CPSC 231-04 Computer Science II
 * 26 April 2026
 * Mastery Project #4: Card Game
 */

import java.util.LinkedList;

/**
 * Represents one player in a game of Egyptian Rat Screw with their position in
 * turn order and current deck.
 */
public class Player {
  private int playerNum;
  private LinkedList<Card> deck;
  private String pattern;

  /**
   * Creates a new Player object with an empty deck.
   * 
   * @param position position in the turn order
   * @param pattern  slapping pattern to watch for
   */
  public Player(int position, String pattern) {
    this(position, pattern, new LinkedList<Card>());
  }

  /**
   * Creates a new Player object.
   * 
   * @param position     position in the turn order
   * @param pattern      slapping pattern to watch for
   * @param startingDeck
   */
  public Player(int position, String pattern, LinkedList<Card> startingDeck) {
    playerNum = position;
    this.pattern = pattern;
    deck = startingDeck;
  }

  /**
   * Gets the position of this Player in the turn order.
   * 
   * @return the Player's position
   */
  public int getPlayerNum() {
    return playerNum;
  }

  /**
   * Get the set of Cards that this player is currently holding.
   * 
   * @return the Cards in order
   */
  public LinkedList<Card> getDeck() {
    return deck;
  }

  /**
   * Get the slapping pattern that this Player is watching for.
   * 
   * @return the pattern
   */
  public String getPattern() {
    return pattern;
  }

  /**
   * Instantiates this Player's deck if the list is currently empty.
   * If the Player's deck already has Cards, does nothing.
   * 
   * @param deck the set of Cards to assign to this Player
   */
  public void createDeck(LinkedList<Card> deck) {
    if (this.deck.size() == 0)
      this.deck = deck;
  }

  /**
   * Appends to the bottom of this Player's deck all of the Cards in order.
   * 
   * @param cards the Cards to add
   */
  public void addCards(LinkedList<Card> cards) {
    deck.addAll(cards);
  }

  /**
   * Removes and returns the top Card from this Player's deck.
   * 
   * @return the Card
   */
  public Card playCard() {
    Card topCard = deck.getFirst();
    deck.remove(0);
    return topCard;
  }

  /**
   * Get this Player in String format. The String has the Player's position, the
   * pattern they are watching for,
   * and their deck in order.
   * 
   * @return the String
   */
  @Override
  public String toString() {
    return "Player #" + playerNum + " is watching for " + pattern + " and has the following cards:\n" + deck.toString();
  }

  /**
   * Determines whether or not the Player should slap this pile based on the
   * pattern they are watching for.
   * 
   * @param pile the current pile of Cards in play
   * @return true if the Player should slap, false otherwise
   */
  public boolean slaps(LinkedList<Card> pile) {
    // slaps can never happen on first card
    if (pile.size() < 2) {
      return false;
    }

    switch (pattern) {
      case "pairs":
        return Game.isPair(pile);
      case "sandwiches":
        return Game.isSandwich(pile);
      case "top-bottoms":
        return Game.isTopBottom(pile);
      case "marriages":
        return Game.isMarriage(pile);
      default:
        return false;
    }
  }

  /**
   * Searches a LinkedList of Players for the Player with the specified position.
   * 
   * @param players   list of Players
   * @param playerNum the position of the Player to find
   * @return the index in the list of the matching Player, or -1 if there is no
   *         matching Player in the list
   */
  public static int findPlayerByNum(LinkedList<Player> players, int playerNum) {
    // iterate over all Players in list
    for (int i = 0; i < players.size(); i++) {
      // compare this Player's number to the argument number
      if (players.get(i).playerNum == playerNum) {
        return i;
      }
    }

    // if no Player matched
    return -1;
  }
}
