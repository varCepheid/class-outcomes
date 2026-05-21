# Egyptian Rat Screw

**The Card class** represents a playing card. It has two member attributes, `rank` and `suit`, with getter and setter methods for them. It overrides `toString()`, `equals()`, and `compareTo()`, and uses private member methods to construct the String. It also has static methods `isFaceCard()` and `isNumberCard()` that do exactly what they say.
**The Deck class** holds a Collection of Cards in a LinkedList. The default constructor creates the Deck in factory-standard order, and there is also a copy constructor. Its most interesting method is `deal()`, which returns a Card at random from the deck and removes it from the LinkedList. The `toString()` method returns the Deck's LinkedList as a String, and the `size()` method returns the number of Cards in the LinkedList.
**The Dealer class** basically wraps Deck. Its constructor creates a default Deck, and its `toString()` and `size()` methods call the corresponding methods from the Deck. The `deals()` method returns `n` Cards dealt from the Deck using its `deal()` method, as a LinkedList, with a failsafe if there are not enough Cards left in the Deck.
**The Player class** holds the details of a player. Each Player has a number representing their position in the turn order, a LinkedList with the Cards they're holding, and the pattern they're watching for (as a String). There is a normal overloaded constructor, and I added a constructor without any cards in the deck, so that I can construct the Player objects before dealing the Cards in the Game. I added a static method `findPlayerByNum()` to help for converting between index in the Game's list of Players and the Players' numbers. All of the expected methods are also present.
**The Game class** plays a game of Egyptian Rat Screw. It has a list of Players, a list of Cards (the pile in the center of the table), a Dealer to deal the Cards at the beginning, and several other member variables for tracking properties of the game. The constructor initializes the Game's Players, giving each one a random pattern selected from the `patterns` array. In addition to the expected accessors and methods, I added a `getCurPlayer()` method that returns the Player object rather than just the number, and a private member method `handleSlaps()` to take some code out of `play()` (although `play()` is quite bloated anyway).
The meat of the Game is in the `play()` method, which does the following:

1. Deal out all of the Cards evenly to the Players, in the same way that human players would.
2. Initialize the private member variables. `cardsLeftInFaceOff` is a counter for face-offs, counting down from the number of tries that each face Card gives, so that when it reaches 0 the round is over. If a face-off is not happening, its value is -1. `slappingPlayer` stores the player-number of the Player who slaps a Card, or 0 if no Player slaps. `playerRemoved` keeps track of whether a Player has been removed within an iteration of the main while-loop. `curPlayer` is the index in the list `players` of the Player whose turn it currently is.
3. Iterate over steps 4-10 until there is only one Player left in the game.
4. The current Player flips their next Card, and it gets added to the pile.
5. Each Player checks if they can slap. If multiple Players slap, only one Player actually gets the slap. This is handled in the separate `handleSlaps()` method.
6. `cardsLeftInFaceOff` is updated to reflect the current status of a face-off. If there is no face-off and a face Card was not played, nothing happens here.
7. If the Player who just played is out of Cards, they are removed from the list.
8. If a Player just slapped the pile, they get the Cards in the pile, and the member variables reset for the next round beginning with the slapping Player.
9. If a Player ran out of tries in a face-off, the Player who played the face Card gets the Cards in the pile, and the member variables reset for the next round beginning with the winning Player.
10. If play should pass to the next Player, the value of `curPlayer` updates to reflect this, wrapping if play should pass back to the first Player.
11. Once only one Player is left, the Game ends, and the player-number of the last Player remaining is returned.
    Additionally, every time a Card is played, at the end of each round, and whenever a Player runs out of Cards, a relevant message is printed to the file `output.txt`. This tracks the details of the whole Game. I chose to use a file because it is far too long to print it all to the console.
    **The Driver class** creates a Game to play. If a command-line argument was provided for the number of Players, it will use that; otherwise the Game will have two Players. It creates the output file to print the Game details to, calls the `play()` method, and prints the player-number of the Player who wins the Game.

## How to Run

to compile: `javac *.java`
to run: `java Driver <number of players>` or `java Driver` (for a two-player game)
**NOTE:** If there is already a file called `output.txt` in the same folder as the project files, it WILL get overwritten. Make sure to save the project files in a new folder.
