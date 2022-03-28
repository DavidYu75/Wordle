import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

public class WordFind {
    private ArrayList<String> words;
    private Scanner scanner;
    private String[][] wordGrid;
    private String wordle;
    private boolean won;

    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    private final String WHITE = "\u001B[37m";         // WHITE
    public static final String RED = "\033[0;31m";     // RED

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
        wordle = words.get((int) (Math.random() * words.size()));
        // wordle = "crane";
        won = false;
    }

    public void play() {
        System.out.println("-----WORDLE-----");
        drawGrid();

        System.out.println("HOW TO PLAY\n" +
                "Guess the WORDLE in six tries.\n" +
                "Each guess must be a valid five-letter word.\n" +
                "After each guess, the color of the tiles will change to show how close your guess was to the word.\n" +
                "Green means the letter is in the word and is in the correct spot. Yellow means the letter is in the word but not in the right spot. Red means the letter isn't in the word.\n");

        for (int guesses = 0; guesses < 6; guesses++) {
            boolean keepGoing = true;
            while (keepGoing) {
                System.out.print("Enter a guess: ");
                String guess = scanner.nextLine();
                guess = guess.toLowerCase();
                if (guess.length() == 5 && words.contains(guess)) {
                    keepGoing = false;
                    processGuess(guess, guesses);
                    if (won) {
                        guesses = 7;
                    } else if (guesses == 5 && !won) {
                        System.out.println("Better luck next time! The wordle was " + GREEN + wordle);
                    }
                } else {
                    System.out.println("Invalid! Enter a 5 letter word that exists.");
                }
            }
        }

    }

    private void processGuess(String guess, int guessNum) {
        if (words.contains(guess)) {

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
    }

    private void drawGrid() {
        for (String[] strings : wordGrid) {
            for (int col = 0; col < wordGrid[0].length; col++) {
                System.out.print(WHITE + "[" + strings[col] + WHITE + "]");
            }
            System.out.println();
        }
    }

    private void importWordList(String fileName) {
//        FileReader fileReader = new FileReader(fileName);
//        BufferedReader bufReader = new BufferedReader(fileReader);
//        words = new ArrayList<String>();
//
//        String line = bufReader.readLine();
//        while (line != null) {
//            words.add(line);
//            line = bufReader.readLine();
//        }
//
//        bufReader.close();
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            words = new ArrayList<String>();

            while ((line = bufferedReader.readLine()) != null)
            {
                words.add(line);
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
