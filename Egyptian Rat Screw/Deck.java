/*
 * Alex Vennebush
 * vennebush@chapman.edu
 * ID: 2497086
 * CPSC 231-04 Computer Science II
 * 16 April 2026
 * Mastery Project #4: Card Game
 */

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Represents a standard deck of playing cards of variable size, implementing
 * the Card class.
 */
public class Deck {
  private final LinkedList<Card> cards;

  /**
   * Creates a new Deck with 52 Cards, sorted by suits and then by ranks (2 to
   * ace).
   */
  public Deck() {
    cards = new LinkedList<>();
    for (int suit = 0; suit < 4; suit++) {
      for (int rank = 2; rank <= Card.ACE; rank++) {
        cards.add(new Card(rank, suit));
      }
    }
  }

  /**
   * Creates a new Deck with the same Cards as the parameter Deck.
   * 
   * @param other another Deck to copy
   */
  public Deck(Deck other) {
    cards = new LinkedList<>();
    Iterator<Card> otherIter = other.cards.iterator();
    Card tempCard;

    while (otherIter.hasNext()) {
      tempCard = otherIter.next();
      cards.add(new Card(tempCard));
    }
  }

  /**
   * Turns the Deck into a String, listing each Card inside square brackets
   * separated by commas.
   * 
   * @return the String representation
   */
  @Override
  public String toString() {
    return cards.toString();
  }

  /**
   * Get the number of cards in the Deck.
   * 
   * @return number of cards
   */
  public int size() {
    return cards.size();
  }

  /**
   * Deals and removes one random Card from the Deck.
   * 
   * @return the dealt Card
   */
  public Card deal() {
    int index = (int) Math.floor(Math.random() * size());
    Card tempCard = cards.get(index);
    cards.remove(index);
    return tempCard;
  }
}
