/*
 * Alex Vennebush
 * vennebush@chapman.edu
 * ID: 2497086
 * CPSC 231-04 Computer Science II
 * 26 April 2026
 * Mastery Project #4: Card Game
 */

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Represents the state and gameplay of a game of Egyptian Rat Screw.
 */
public class Game {
  private LinkedList<Player> players;
  private LinkedList<Card> pile;
  private Dealer dealer;
  private int curPlayer, slappingPlayer, cardsLeftInFaceOff;
  private boolean playerRemoved;

  /**
   * Holds the possible patterns that Players can watch for.
   * Each Player is randomly assigned one of these patterns at the beginning of
   * the game.
   */
  private final static String[] patterns = { "pairs", "sandwiches", "top-bottoms", "marriages" };

  // CONSTRUCTORS

  /**
   * Sets up a new Game with 2 Players. Assigns each Player a pattern to watch
   * for.
   */
  public Game() {
    this(2);
  }

  /**
   * Sets up a new Game with the specified number of Players. Assigns each Player
   * a pattern to watch
   * for.
   * 
   * @param numPlayers number of Players in this Game
   */
  public Game(int numPlayers) {
    players = new LinkedList<>();
    for (int i = 1; i <= numPlayers; i++) {
      int patternIndex = (int) Math.floor(Math.random() * patterns.length);
      String pattern = patterns[patternIndex];
      // System.out.println(patternIndex + " " + pattern);
      players.add(new Player(i, pattern));
    }

    pile = new LinkedList<>();
    dealer = new Dealer();
    curPlayer = 0;
  }

  // ACCESSORS

  /**
   * Get the list of Players in this Game.
   * 
   * @return the Players
   */
  public LinkedList<Player> getPlayers() {
    return players;
  }

  /**
   * Get the Cards currently in the center of the table.
   * 
   * @return the Cards
   */
  public LinkedList<Card> getPile() {
    return pile;
  }

  /**
   * Get the Dealer object dealing this Game.
   * 
   * @return the Dealer
   */
  public Dealer getDealer() {
    return dealer;
  }

  /**
   * Get the index in the list of Players corresponding to the Player whose turn
   * it is.
   * 
   * @return the Player's position
   */
  public int getCurPlayerIndex() {
    return curPlayer;
  }

  /**
   * Get the Player whose turn it currently is.
   * 
   * @return the Player
   */
  public Player getCurPlayer() {
    return players.get(curPlayer);
  }

  // PATTERN CHECKERS

  /**
   * Checks if there is a pair on top of the pile.
   * 
   * @param pile the pile of Cards to be checked
   * @return true if there is a pair, false otherwise
   */
  public static boolean isPair(LinkedList<Card> pile) {
    Card firstCard = pile.getLast();
    Card secondCard = pile.get(pile.size() - 2);
    return firstCard.equals(secondCard);
  }

  /**
   * Checks if there is a sandwich on top of the pile.
   * 
   * @param pile the pile of Cards to be checked
   * @return true if there is a sandwich, false otherwise
   */
  public static boolean isSandwich(LinkedList<Card> pile) {
    // cannot be a sandwich with only 2 cards
    if (pile.size() < 3) {
      return false;
    }

    Card firstCard = pile.getLast();
    Card secondCard = pile.get(pile.size() - 3);
    return firstCard.equals(secondCard);
  }

  /**
   * Checks if there is a top-bottom in the pile.
   * 
   * @param pile the pile of Cards to be checked
   * @return true if there is a top-bottom, false otherwise
   */
  public static boolean isTopBottom(LinkedList<Card> pile) {
    Card firstCard = pile.getLast();
    Card secondCard = pile.getFirst();
    return firstCard.equals(secondCard);
  }

  /**
   * Checks if there is a marriage on top of the pile.
   * 
   * @param pile the pile of Cards to be checked
   * @return true if there is a marriage, false otherwise
   */
  public static boolean isMarriage(LinkedList<Card> pile) {
    Card firstCard = pile.getLast();
    Card secondCard = pile.get(pile.size() - 2);
    return ((firstCard.getRank() == Card.QUEEN) && (secondCard.getRank() == Card.KING))
        || ((firstCard.getRank() == Card.KING) && (secondCard.getRank() == Card.QUEEN));
  }

  // GAMEPLAY METHODS

  /**
   * Plays through this Game of Egyptian Rat Screw according to the rules until
   * only one Player is left. Prints the progress of the Game as it plays to a
   * specified output file.
   * 
   * @param outFile the file to print the Game progress details to
   * @return the position of the winning Player
   */
  public int play(PrintWriter outFile) {
    // divide the Cards evenly among the Players
    int cardsPerPlayer = 52 / players.size();
    for (int i = 0; i < players.size(); i++) {
      players.get(i).createDeck(dealer.deals(cardsPerPlayer));
    }
    curPlayer = 0;
    while (dealer.size() > 0) {
      players.get(curPlayer).addCards(dealer.deals(1));
      curPlayer++;
    }

    // for (int i = 0; i < players.size(); i++) {
    // outFile.println(players.get(i));
    // }
    // outFile.println();

    // set up
    cardsLeftInFaceOff = -1;
    slappingPlayer = 0;
    playerRemoved = false;
    curPlayer = 0;

    // continue as long as two or more players are in the game
    while (players.size() > 1) {
      // outFile.println("It is now player #" + getCurPlayer().getPlayerNum() + "'s
      // turn.\n");

      // clear playerRemoved for next turn
      playerRemoved = false;

      // play next card
      Card nextCard = getCurPlayer().playCard();
      pile.add(nextCard);

      // handle slaps
      slappingPlayer = handleSlaps();

      outFile.print("Player #" + getCurPlayer().getPlayerNum() + " plays " +
          nextCard);
      outFile.println(" (" + getCurPlayer().getDeck().size() + " cards left)");
      // if (slappingPlayer == 0) {
      // outFile.println("No one slapped.");
      // } else {
      // outFile.println("Player #" + slappingPlayer + " slapped the pile.");
      // }

      // handle face-offs
      switch (nextCard.getRank()) {
        // if a face card was just played, reset counter
        case Card.JACK:
          cardsLeftInFaceOff = 1;
          break;
        case Card.QUEEN:
          cardsLeftInFaceOff = 2;
          break;
        case Card.KING:
          cardsLeftInFaceOff = 3;
          break;
        case Card.ACE:
          cardsLeftInFaceOff = 4;
          break;
        default:
          // if in a face-off, decrement counter
          if (cardsLeftInFaceOff > 0) {
            cardsLeftInFaceOff--;
          }
      }

      // if (cardsLeftInFaceOff >= 0) {
      // outFile.println("There are " + cardsLeftInFaceOff + " tries left in this
      // face-off.");
      // } else {
      // outFile.println("Not currently in a face-off.");
      // }

      // handle player out of cards
      if (getCurPlayer().getDeck().size() == 0) {
        // remove Player from list
        players.remove(curPlayer);

        outFile.println("   This player ran out of cards and was removed from the game.");
        // for (int i = 0; i < players.size(); i++) {
        // outFile.println(players.get(i));
        // }
        // outFile.println();

        // don't need to move to next Player since the Player was removed
        // if in the middle of a face-off, the next player will take over
        playerRemoved = true;
      }

      // handle end of round
      if (slappingPlayer > 0) {
        // a Player slapped this pile

        outFile.println("Player #" + slappingPlayer + " slapped the pile. This round is over.\n");

        // index in players list of the Player that slapped
        slappingPlayer = Player.findPlayerByNum(players, slappingPlayer);

        if (slappingPlayer != -1) {
          // confirm that the Player is really in the list

          // add pile to bottom of the Player's deck
          players.get(slappingPlayer).addCards(pile);

          // reset for next round
          cardsLeftInFaceOff = -1;
          pile.clear();
          curPlayer = slappingPlayer;
          continue;
        }
      }
      if (cardsLeftInFaceOff == 0) {
        // in a face-off and counter has run out

        outFile.println("This player lost the face-off. This round is over.\n");

        // determine which Player won the face-off
        if (curPlayer == 0) {
          // first Player in the list, wrap around to last Player
          curPlayer = players.size() - 1;
        } else {
          // Player before this one won
          curPlayer--;
        }

        // add pile to bottom of the Player's deck
        // curPlayer now points to the Player who won the face-off
        getCurPlayer().addCards(pile);

        // reset for next round
        cardsLeftInFaceOff = -1;
        pile.clear();
        continue;
      }

      // move to next player
      if (((cardsLeftInFaceOff == -1) || (nextCard.isFaceCard())) && !playerRemoved) {
        // not in a face-off -- next player's turn
        // or this player played a face card, moving to a face-off with the next player
        // if a player was removed, moving on happened automatically
        curPlayer++;
      }

      // wrap around if out of Players
      if (curPlayer >= players.size()) {
        curPlayer = 0;
      }
    }

    return players.getFirst().getPlayerNum();
  }

  /**
   * Handles slaps for all of the Players given the current pile.
   * 
   * @return the player-number of the Player who slaps, or 0 if no Player slaps
   */
  private int handleSlaps() {
    // collect players that slap
    LinkedList<Player> slapPlayers = new LinkedList<>();

    // check if each player slaps

    Iterator<Player> playerIter = players.iterator();

    // iterate over list of players
    while (playerIter.hasNext()) {
      Player thisPlayer = playerIter.next();
      if (thisPlayer.slaps(pile)) {
        // add players that should slap to list
        slapPlayers.add(thisPlayer);
      }
    }

    if (slapPlayers.isEmpty()) {
      // no Players should slap
      return 0;
    } else if (slapPlayers.size() == 1) {
      // only one Player slaps
      return slapPlayers.getFirst().getPlayerNum();
    } else {
      // multiple Players slap, randomly select from among list
      int playerIndex = (int) Math.floor(Math.random() * slapPlayers.size());
      return slapPlayers.get(playerIndex).getPlayerNum();
    }
  }
}
