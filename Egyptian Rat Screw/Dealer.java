/*
 * Alex Vennebush
 * vennebush@chapman.edu
 * ID: 2497086
 * CPSC 231-04 Computer Science II
 * 16 April 2026
 * Mastery Project #4: Card Game
 */

import java.util.LinkedList;

/**
 * Utility class for managing a Deck and dealing the Cards from the Deck.
 */
public class Dealer {
  private final Deck deck;

  /**
   * Creates a new Dealer with a fresh Deck.
   */
  public Dealer() {
    deck = new Deck();
  }

  /**
   * Deals out multiple Cards from the Deck.
   * If 0 cards are requested, returns an empty list.
   * If there are not enough Cards in the Deck, returns as many Cards as are
   * available.
   * 
   * @param n the number of Cards to deal
   * @return a LinkedList with the dealt Cards
   */
  public LinkedList<Card> deals(int n) {
    if ((n == 0) || (deck.size() == 0)) {
      return new LinkedList<Card>();
    } else {
      LinkedList<Card> list = new LinkedList<>();
      for (int i = 0; i < n; i++) {
        if (deck.size() == 0) {
          break;
        }
        list.add(deck.deal());
      }
      return list;
    }
  }

  /**
   * @return the size of the Deck
   */
  public int size() {
    return deck.size();
  }

  /**
   * @return the String representation of the Deck
   */
  @Override
  public String toString() {
    return deck.toString();
  }
}
