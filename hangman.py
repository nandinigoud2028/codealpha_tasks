import random

# List of 5 predefined words
words = ["apple", "python", "school", "garden", "computer"]

# Select a random word
word = random.choice(words)

# List to store guessed letters
guessed_letters = []

# Number of incorrect guesses allowed
max_attempts = 6
wrong_guesses = 0

print("===== HANGMAN GAME =====")
print("Guess the word one letter at a time!")
print("You can make up to 6 incorrect guesses.")

# Game loop
while wrong_guesses < max_attempts:

    # Display the word with guessed letters
    display = ""

    for letter in word:
        if letter in guessed_letters:
            display += letter + " "
        else:
            display += "_ "

    print("\nWord:", display)
    print("Wrong guesses:", wrong_guesses, "/", max_attempts)

    # Check if the complete word has been guessed
    if all(letter in guessed_letters for letter in word):
        print("\nCongratulations! You guessed the word:", word)
        break

    # Get a letter from the player
    guess = input("Enter a letter: ").lower()

    # Check if input is valid
    if len(guess) != 1 or not guess.isalpha():
        print("Please enter only one letter.")
        continue

    # Check if the letter was already guessed
    if guess in guessed_letters:
        print("You already guessed that letter!")
        continue

    # Add the guess to the list
    guessed_letters.append(guess)

    # Check whether the guess is correct
    if guess in word:
        print("Correct guess!")
    else:
        wrong_guesses += 1
        print("Incorrect guess!")

# If the player uses all 6 incorrect guesses
if wrong_guesses == max_attempts:
    print("\nGame Over!")
    print("The correct word was:", word)
