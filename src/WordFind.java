// David Yu

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * This class handles the logic and play functions of Wordle
 *
 * @author David Yu
 */

public class WordFind {
    /** An ArrayList used to contain all possible 5 letter words */
    private ArrayList<Word> words;

    /** Scanner object used for reading user's guesses */
    private Scanner scanner;

    /** A 2D array used to represent the grid for Wordle */
    private String[][] wordGrid;

    /** The word that the user would have to guess */
    private String wordle;

    /** Boolean used to represent whether the user has won or loss */
    private boolean won;

    /** Constant that represents green for Strings */
    public static final String GREEN = "\033[0;32m";    // GREEN
    /** Constant that represents yellow for Strings */
    public static final String YELLOW = "\033[0;33m";   // YELLOW
    /** Constant that represents white for Strings */
    public static final String WHITE = "\u001B[37m";    // WHITE
    /** Constant that represents red for Strings */
    public static final String RED = "\033[0;31m";      // RED

    /**
     * Instantiates a WordFind object.
     *
     * @param fileName Name of the file that contains all possible 5 letter words.
     */
    public WordFind(String fileName) {
        importWordList(fileName);
        scanner = new Scanner(System.in);
        wordGrid = new String[6][5];
        // Initializes the word grid to be all empty strings instead of null values
        for (int row = 0; row < wordGrid.length; row++) {
            for (int col = 0; col < wordGrid[0].length; col++) {
                wordGrid[row][col] = " ";
            }
        }
        wordle = words.get((int) (Math.random() * words.size())).getContent();
        won = false;
    }

    /**
     * Starts the game and handles the main play functions of Wordle
     */
    public void play() {
        System.out.println("-----WORDLE-----");
        drawGrid();

        System.out.println("HOW TO PLAY\n" +
                "Guess the WORDLE in six tries.\n" +
                "Each guess must be a valid five-letter word.\n" +
                "After each guess, the color of the tiles will change to show how close your guess was to the word.\n" +
                GREEN + "Green" + WHITE + " means the letter is in the word and is in the correct spot." + YELLOW + "\nYellow" + WHITE + " means the letter is in the word but not in the right spot." + RED + "\nRed" + WHITE + " means the letter isn't in the word.\n");

        for (int guesses = 0; guesses < 6; guesses++) {
            boolean keepGoing = true;
            while (keepGoing) {
                System.out.print("Enter a guess: ");
                String guess = scanner.nextLine();
                guess = guess.toLowerCase();
                if (guess.length() == 5) {
                    for (int i = 0; i < words.size(); i++) {
                        if (guess.equals(words.get(i).getContent())) {
                            keepGoing = false;
                            processGuess(guess, guesses);
                            if (won) {
                                guesses = 7;
                            } else if (guesses == 5 && !won) {
                                System.out.println("Better luck next time! The wordle was " + GREEN + wordle);
                            }
                            break;
                        } else if (i == words.size() - 1 && !guess.equals(words.get(i).getContent())) {
                            System.out.println("Invalid! Enter a word that exists.");
                        }
                    }
                } else {
                    System.out.println("Invalid! Enter a 5 letter word that exists.");
                }
            }
        }

    }

    /**
     * Private helper method that handles the logic of guesses in Wordle.
     * <p>
     *     Determines whether each letter of the user's guess will be red, green, or yellow depending on the game's rules.
     * </p>
     * @param guess The user's guess
     * @param guessNum The number of times the user has guessed
     */
    private void processGuess(String guess, int guessNum) {
        if (guess.equals(wordle)) {
            for (int i = 0; i < guess.length(); i++) {
                wordGrid[guessNum][i] = GREEN + guess.charAt(i);
            }
            System.out.println("Congrats! You guessed the wordle!");
            won = true;
        }

        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == (wordle.charAt(i))) {
                wordGrid[guessNum][i] = GREEN + guess.charAt(i);
            } else if (wordle.indexOf(guess.charAt(i)) == -1) {
                wordGrid[guessNum][i] = RED + guess.charAt(i);
            } else {
                wordGrid[guessNum][i] = YELLOW + guess.charAt(i);
            }
        }

        drawGrid();
    }

    /**
     * Private helper method that prints the wordGrid to the console in an easily readable format.
     */
    private void drawGrid() {
        for (String[] strings : wordGrid) {
            for (int col = 0; col < wordGrid[0].length; col++) {
                System.out.print(WHITE + "[" + strings[col] + WHITE + "]");
            }
            System.out.println();
        }
    }

    /**
     * Private helper method that takes all the words in a file and adds it into the words ArrayList.
     *
     * @param fileName The name of the file that contains all the 5 letter words.
     */
    private void importWordList(String fileName) {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            words = new ArrayList<Word>();

            while ((line = bufferedReader.readLine()) != null)
            {
                int length = line.length();
                Word nextWord = new Word(line, length);
                if (nextWord.getLength() == 5) {
                    words.add(nextWord);
                }
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}
