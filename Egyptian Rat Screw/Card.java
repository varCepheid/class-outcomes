/*
 * Alex Vennebush
 * vennebush@chapman.edu
 * ID: 2497086
 * CPSC 231-04 Computer Science II
 * 26 April 2026
 * Mastery Project #4: Card Game
 */

/**
 * Represents an individual card in a standard 52-card deck (no jokers).
 */
public class Card implements Comparable<Card> {
  // constants for suits
  public static final int SPADES = 0;
  public static final int DIAMONDS = 1;
  public static final int CLUBS = 2;
  public static final int HEARTS = 3;

  // constants for face cards
  public static final int JACK = 11;
  public static final int QUEEN = 12;
  public static final int KING = 13;
  public static final int ACE = 14;

  // private member variables
  private int rank;
  private int suit;

  // CONSTRUCTORS
  /**
   * Creates a new Ace of Spades Card.
   */
  public Card() {
    rank = Card.ACE;
    suit = Card.SPADES;
  }

  /**
   * Creates a new Card with specified rank and suit.
   * Use the Card class's constants for face cards and suits.
   * If either argument is out of bounds, the object will use the value -1.
   * 
   * @param rank this Card's rank/number, 2 to 14 or face-card constant
   * @param suit this Card's suit from class constants (0 to 3)
   */
  public Card(int rank, int suit) {
    if ((rank >= 2) && (rank <= 14)) {
      this.rank = rank;
    } else {
      this.rank = -1;
    }
    if ((suit >= 0) && (suit <= 3)) {
      this.suit = suit;
    } else {
      this.suit = -1;
    }
  }

  /**
   * Creates a new Card with the same rank and suit as another card.
   * 
   * @param toCopy Card to create a copy of
   */
  public Card(Card toCopy) {
    rank = toCopy.rank;
    suit = toCopy.suit;
  }

  // ACCESSORS AND MUTATORS
  /**
   * Get the rank (number) of this Card.
   * 
   * @return the rank
   */
  public int getRank() {
    return rank;
  }

  /**
   * Set the rank (number) of this Card.
   * 
   * @param rank the new rank
   */
  public void setRank(int rank) {
    this.rank = rank;
  }

  /**
   * Convert the rank of this Card to a String representation of its value.
   * 
   * @return the rank in String form
   */
  private String rankToString() {
    switch (rank) {
      case 2:
        return "Two";
      case 3:
        return "Three";
      case 4:
        return "Four";
      case 5:
        return "Five";
      case 6:
        return "Six";
      case 7:
        return "Seven";
      case 8:
        return "Eight";
      case 9:
        return "Nine";
      case 10:
        return "Ten";
      case 11:
        return "Jack";
      case 12:
        return "Queen";
      case 13:
        return "King";
      case 14:
        return "Ace";
      default:
        return "";
    }
  }

  /**
   * Get the suit of this Card.
   * Use the Card class constants to check the suit.
   * 
   * @return the suit value as an int
   */
  public int getSuit() {
    return suit;
  }

  /**
   * Set the suit of this Card.
   * Use the Card class constants as the argument.
   * 
   * @param suit the new suit
   */
  public void setSuit(int suit) {
    this.suit = suit;
  }

  /**
   * Convert the suit of this Card to a String representation of its value.
   * 
   * @return the suit in String form
   */
  private String suitToString() {
    switch (suit) {
      case 0:
        return "Spades";
      case 1:
        return "Diamonds";
      case 2:
        return "Clubs";
      case 3:
        return "Hearts";
      default:
        return "";
    }
  }

  /**
   * Returns a String representation of this Card, in the form "[rank] of [suit]".
   * 
   * @return the String
   */
  @Override
  public String toString() {
    return rankToString() + " of " + suitToString();
  }

  /**
   * Compares two Cards' ranks.
   * 
   * @param other another Card
   * @return true if the two Cards have the same rank, and false otherwise
   */
  public boolean equals(Card other) {
    return this.rank == other.rank;
  }

  /**
   * Compares two Cards' ranks.
   * 
   * @param other another Card
   * @return a negative number if this Card has the lower rank, a positive number
   *         if this Card has the higher rank, and 0 if they are equal
   */
  @Override
  public int compareTo(Card other) {
    return this.rank - other.rank;
  }

  /**
   * Check the rank of this Card. Inverse of isNumberCard().
   * 
   * @return true if this Card is a Jack, Queen, King, or Ace; false otherwise
   */
  public boolean isFaceCard() {
    return ((this.rank == Card.ACE) || (this.rank == Card.KING) || (this.rank == Card.QUEEN)
        || (this.rank == Card.JACK));
  }

  /**
   * Check the rank of this Card. Inverse of isFaceCard().
   * 
   * @return true if this Card is Two to Ten, false if it is a face card
   */
  public boolean isNumberCard() {
    return !this.isFaceCard();
  }
}
