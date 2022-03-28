// David Yu

/**
 * This class will run the Wordle program
 *
 * @author David Yu
 */
public class Main {
    public static void main(String[] args) {
        WordFind wordle = new WordFind("src\\words.txt");
        wordle.play();
    }
}
