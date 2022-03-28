// David Yu

/**
 * Class that represents a single Word object
 *
 * @author David Yu
 */

public class Word {
    /** The word */
    private String content;

    /** The length of the word */
    private int length;

    /**
     * Instantiates a Word object.
     *
     * @param content The word
     * @param length The length of the word
     */
    public Word (String content, int length) {
        this.content = content;
        this.length = length;
    }

    /**
     * Returns the word.
     *
     * @return The word
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the length of the word.
     *
     * @return The length of the word.
     */
    public int getLength() {
        return length;
    }
}
