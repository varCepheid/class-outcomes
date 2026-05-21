# Not-So-Super Mario Bros.

This program simulates a top-down video game resembling the Super Mario platformers. There is a succession of levels, each containing a mixture of coins, mushrooms, enemies, and empty spaces. Mario moves randomly around each level, collecting the coins and fighting the enemies, until he reaches the warp pipe and moves to the next level. The game ends when Mario defeats the boss-enemy in the last level (win) or when Mario runs out of lives (loss).
The program reads the number of levels, the size of each level, the initial number of lives, and the mixture of elements of the levels from a given input file. The program first creates a world using those conditions and displays the entire world in an output file. At each move, the program writes to the output file the current level, what happened at that move, and Mario's current status. The program keeps a running tally of the number of moves, and at the end of the game it displays that number and why the game ended.
In order to run the program, there must be an input file with one integer on each line:

- Line 1 is the number of levels.
- Line 2 is the dimension of the grid.
- Line 3 is the number of initial lives.
- Line 4 is the approximate percentage of the positions in each level with coins.
- Line 5 is the approximate percentage of the positions in each level with nothing.
- Line 6 is the approximate percentage of the positions in each level with Goombas.
- Line 7 is the approximate percentage of the positions in each level with Koopas.
- Line 8 is the approximate percentage of the positions in each level with mushrooms.

The sum of the numbers on lines 4 through 8 should be 100. The program will end without outputting anything if it is not.

## References

I used ChatGPT to find out what values an uninitialized char array has. All of the code is my own.

## How to Run

to compile: `javac MarioGame.java Mario.java`
to run: `java MarioGame <inputfile> <outputfile>`
