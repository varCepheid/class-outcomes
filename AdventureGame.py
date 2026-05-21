from random import randint

# global variables
alphabet = [
    "a",
    "b",
    "c",
    "d",
    "e",
    "f",
    "g",
    "h",
    "i",
    "j",
    "k",
    "l",
    "m",
    "n",
    "o",
    "p",
    "q",
    "r",
    "s",
    "t",
    "u",
    "v",
    "w",
    "x",
    "y",
    "z",
]
vowels = ["a", "e", "i", "o", "u"]
consonants = [
    "b",
    "c",
    "d",
    "f",
    "g",
    "h",
    "j",
    "k",
    "l",
    "m",
    "n",
    "p",
    "q",
    "r",
    "s",
    "t",
    "v",
    "w",
    "x",
    "z",
]
treasure_inventory = [
    # (name, cost, weight)
    ("sword", 40, 10),
    ("shield", 35, 12),
    ("potion", 25, 2),
    ("bar of gold", 50, 20),
    ("gem", 60, 10),
    ("puzzle piece", 0, 1),
    ("puzzle piece", 0, 1),
    ("puzzle piece", 0, 1),
    ("magic wand", 30, 1),
]

name = ""
lives = 4
coins = 0
treasures_collected = []
weight_remaining = 40
treasure_value = 0
puzzle_completion = {"north": None, "south": None, "east": None, "west": None}


def create_password():
    password = "123"

    # one vowel
    vowel = vowels[randint(0, 4)]
    vowel_index = randint(0, 2)
    password = password.replace(str(vowel_index + 1), vowel)

    # two consonants
    for i in range(3):
        if i == vowel_index:
            continue
        new_letter = consonants[randint(0, 19)]
        password = password.replace(str(i + 1), new_letter)

    # print(password)
    return password


def check_lives():
    global lives, coins, name
    if lives <= 0:
        print(
            f"Oh no! You're out of lives! You finished the game with {coins} coins. Thanks for playing, {name}!"
        )
        exit()


def password_guessing():
    global lives

    password = create_password()
    user_guess = "123"
    guesses_remaining = 5
    letters_guessed = 0
    hints = [0, 0, 0]

    print(
        "In front of you is a door with a three-letter combination lock. A placard tells you that the correct code has exactly one vowel, it won't have any Ys, and it may not be a real word."
    )
    print(
        "(On the screen, numbers will stand in for the letters you have not yet guessed.)"
    )

    while letters_guessed < 3:
        print()
        print(f"Current password guessed: {user_guess}")

        # get inputs of place and letter
        try:
            guess_index = int(input("Which place do you want to guess? ")) - 1
            if guess_index not in [1, 2, 0]:
                print("Choose 1, 2, or 3.")
                continue
        except:
            print("Choose 1, 2, or 3.")
            continue
        guess_letter = input("Which letter is in that place? ")
        if guess_letter not in alphabet:
            print("Your guess must be one lowercase letter.")
            continue

        # replace number with letter if guess is correct
        if password[guess_index] == guess_letter:
            print("That's right! You're one step closer to opening the door.")
            user_guess = user_guess.replace(str(guess_index + 1), guess_letter)
            letters_guessed += 1
            guesses_remaining -= 1

        # give a hint if guess is incorrect
        else:
            if hints[guess_index] == 0:
                # reveal either vowel or consonant
                if password[guess_index] in vowels:
                    print("Not quite. In that place is a vowel.")
                else:
                    print("Not quite. In that place is a consonant.")

                hints[guess_index] = 1
            elif hints[guess_index] == 1:
                # reveal neighboring letter
                letter_number = alphabet.index(password[guess_index])
                random_sign = (randint(0, 1) * 2) - 1
                try:
                    neighbor = alphabet[letter_number + random_sign]
                except:
                    neighbor = "the end of the alphabet"
                finally:
                    print(
                        f"Sorry, still wrong. The correct letter is next to {neighbor}."
                    )

                hints[guess_index] = 2
            elif hints[guess_index] == 2:
                print("Nearly there, keep guessing!")

            guesses_remaining -= 1

        # check if out of guesses
        if (guesses_remaining < 1) and (letters_guessed < 3):
            print()
            print(
                f"You're out of guesses! You have {lives} remaining. You can spend one life to keep playing, or spend two lives to break down the door and end this puzzle."
            )
            choice = input("Enter A to keep playing or B to end the puzzle: ")

            while (choice != "A") and (choice != "B"):
                choice = input("Please choose A or B. ")

            if choice == "A":
                lives -= 1
                guesses_remaining = 5
                check_lives()
                print(f"You now have {lives} lives.")
            else:
                lives -= 2
                check_lives()
                print("You break down the door and climb through the rubble.")
                print(f"You now have {lives} lives.")
                break

    # report win
    if letters_guessed == 3:
        print()
        print(
            f"You guessed the password! It was {password}. The door swings open, and you step through."
        )
        print(f"Lives remaining: {lives}")


def puzzleN_dice():
    global name, coins, lives
    die_roll = randint(1, 12)
    print(
        f"Hello, {name}! I rolled a die with the numbers 1 through 12 on its faces. Guess what number I rolled! You'll earn coins based on how close you are."
    )
    guess = int(input("Enter a guess between 1 and 12: "))
    while not guess in range(1, 13):
        guess = int(
            input("Your guess must be a number between 1 and 12. Guess again: ")
        )

    error = abs(guess - die_roll)
    print(f"I rolled {die_roll}, so you were off by {error}.")
    if error < 5:
        coins += 5 - error
        print(f"For being that close, you earned {5 - error} coins. That's a win!")
        return True

    print(
        "Unfortunately, that isn't close enough to win. You lose five coins and one life."
    )
    coins -= 5
    lives -= 1
    check_lives()
    return False


def puzzleS_backwards():
    global name, coins, lives

    # create word
    word = ""
    backword = ""
    for i in range(4):
        new_letter = alphabet[randint(0, 25)]
        word += new_letter
        backword = new_letter + backword

    # guess word
    print(
        f"Hello, {name}! I'm thinking of a four-letter passcode. I'll tell you the letters backwards. If you can give me the passcode forwards, you win!"
    )
    print(f"The passcode backwards is {backword}. What's the passcode forwards?")
    guess = input("Type the passcode forwards: ")

    if guess == word:
        print("That's right, well done! For winning my game, you earn five coins.")
        coins += 5
        return True

    print(
        f"That wasn't quite right. The correct passcode was {word}. Because you lost, you lose five coins and one life."
    )
    coins -= 5
    lives -= 1
    check_lives()
    return False


def puzzleE_sumDigits():
    global name, coins, lives

    # generate number
    digits = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"]
    number = ""
    sum = 0
    for i in range(4):
        nextIndex = randint(0, len(digits) - 1)
        number += digits[nextIndex]
        sum += int(digits[nextIndex])
        digits.remove(digits[nextIndex])

    # guess sum
    print(
        f"Hello, {name}! I'm thinking of a four-digit number. In order to return to the dungeon, you must tell me the sum of the digits of my number."
    )
    guess = int(input(f"What is the sum of the digits of {number}? "))

    if guess == sum:
        print("That's right! For winning my game, you get five more coins.")
        coins += 5
        return True

    print(
        f"Actually, the sum was {sum}. Because you lost, you lose five coins and one life."
    )
    coins -= 5
    lives -= 1
    check_lives()
    return False


def puzzleW_treasure():
    global name, coins, lives, treasures_collected, weight_remaining, treasure_value

    print(
        f"Welcome to the treasure room, {name}! Here you can draw treasures from the chest."
    )
    print("But beware: if you draw a treasure you cannot afford, you will lose a life.")
    print(
        "You also must carry all of your treasures in your backpack. If your backpack gets too heavy, you will also lose a life."
    )

    print(
        f"Right now, you have {coins} coins, and your backpack can hold {weight_remaining} more pounds of treasure."
    )
    choice = input("Would you like to draw from the treasure chest? Enter yes or no: ")
    while not choice in ["yes", "no"]:
        choice = input("Please enter yes or no to make your choice: ")
    print()

    if choice == "yes":
        item_index = randint(1, len(treasure_inventory) - 1)
        item, cost, weight = treasure_inventory[item_index]

        # reduce cost by 10 if have wand
        try:
            treasures_collected.index("magic wand")
            if cost > 10:
                cost -= 10
        finally:
            print(
                f"You drew a {item}. It costs {cost} coins and weighs {weight} pounds."
            )
            if cost > coins:
                print(
                    "Unfortunately, you do not have enough coins to afford this item. You can get more coins by opening the other doors."
                )
                print("Because you can't afford it, you lose a life.")
                lives -= 1
                check_lives()
                return False
            elif weight > weight_remaining:
                print(
                    "Unfortunately, your backpack is not heavy enough to hold this item. Because you can't carry it, you lose a life."
                )
                lives -= 1
                check_lives()
                return False
            else:
                # collect item
                treasures_collected.append(item)
                treasure_value += cost
                weight_remaining -= weight
                coins -= cost

                # remove item collected
                for this_item, this_cost, this_weight in treasure_inventory:
                    if this_item == item:
                        treasure_inventory.remove((this_item, this_cost, this_weight))
                        break

                # logic for all three puzzle pieces
                if (item == "puzzle piece") and (
                    treasures_collected.count("puzzle piece") == 3
                ):
                    print(
                        "You put all three puzzle pieces together, and they magically transform into a special elixir!\nYou gain one life, and you no longer have the puzzle pieces."
                    )
                    lives += 1
                    weight_remaining += 3
                    for i in range(3):
                        treasures_collected.remove("puzzle piece")

                if item == "magic wand":
                    print(
                        "This wand allows you to conjure coins. All treasure prices will be reduced by 10 coins."
                    )

                print(
                    "A valuable addition to your collection. Your backpack now contains:"
                )
                for item in treasures_collected:
                    print(f"a {item}")
                print(
                    "You can draw another treasure from the chest by entering this room again."
                )
                return True

    else:
        least_value = 100
        least_weight = 100
        for this_item, this_cost, this_weight in treasure_inventory:
            if this_cost < least_value:
                least_value = this_cost
            if this_weight < least_weight:
                least_weight = this_weight

        if (coins < least_value) or (weight_remaining < least_weight):
            print(
                f"You cannot buy or carry any of the remaining items. By choosing to stop at the right time, you've won.\nCongratulations, {name}! You finished the game with {lives} lives, and your backpack has {treasure_value} coins' worth of treasure."
            )
            exit()
        else:
            print(
                f"Actually, there were items you could have bought. For choosing not to play, you lose one life."
            )
            lives -= 1
            check_lives()
            return False


# ACTUAL GAMEPLAY
# stage 0 greeting
print("Welcome to the Python Adventure Game!")
name = input("What is your name? ")
print(f"Hello, {name}. You will start with 4 lives and 0 coins.")
print()

# stage 1 password
password_guessing()
print("For completing the first stage of the game, you get 100 coins.")
coins += 100
print()

# stage 2 four puzzles
print(
    "Suddenly, you fall down into a dark dungeon. You see four stone walls, each with a door set into it, and a compass rose on the ground."
)
while True:
    print(f"Right now, you have {lives} lives and {coins} coins.")
    choice = input("Which door would you like to enter? Choose N, E, S, or W: ")
    while not choice in ["N", "E", "S", "W"]:
        choice = input("Your selection must be N, E, S, or W. Choose again: ")
    print()

    match (choice):
        case "N":
            if puzzle_completion["north"] == None:
                if puzzleN_dice():
                    puzzle_completion["north"] = True
                else:
                    puzzle_completion["north"] = False
            else:
                print(
                    "You have already opened the north door. Please choose a different door."
                )
        case "S":
            if puzzle_completion["south"] == None:
                if puzzleS_backwards():
                    puzzle_completion["south"] = True
                else:
                    puzzle_completion["south"] = False
            else:
                print(
                    "You have already opened the south door. Please choose a different door."
                )
        case "E":
            if puzzle_completion["east"] == None:
                if puzzleE_sumDigits():
                    puzzle_completion["east"] = True
                else:
                    puzzle_completion["east"] = False
                print(
                    "You have already opened the east door. Please choose a different door."
                )
        case "W":
            puzzleW_treasure()
            puzzle_completion["west"] = True

    print()
    message = "Progress so far:\n"
    for direction in ["north", "south", "east", "west"]:
        if puzzle_completion[direction] == None:
            message += f"You have not yet opened the {direction} door.\n"
        elif puzzle_completion[direction] == True:
            message += f"You have completed the puzzle behind the {direction} door.\n"
            if direction == "west":
                message += "   You may choose the west door again to continue drawing treasures. The end of the game is behind the west door.\n"
        else:
            message += f"You have failed the puzzle behind the {direction} door.\n"
    print(message)
