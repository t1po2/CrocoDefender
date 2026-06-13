package game;



/**
 * Represents a single high score entry in the tower defense game.
 *
 * <p>This class stores player achievements in the form of a name and the highest
 * wave number they reached. It implements {@link Comparable} to enable sorting
 * high scores by wave number in descending order (higher waves come first).
 *
 * @author Nguyen Viet Hung
 */
public class HighScoreEntry implements Comparable<HighScoreEntry> {
    /** The name of the player who achieved this score */
    private String name;
    /** The highest wave number the player reached */
    private int wave;

    /**
     * Constructs a new HighScoreEntry with the specified player name and wave number.
     *
     * @param name The name of the player
     * @param wave The highest wave number reached by the player
     */
    public HighScoreEntry(String name, int wave) {
        this.name = name;
        this.wave = wave;
    }



    //Getters
    /**
     * Returns the name of the player who achieved this high score.
     *
     * @return The player's name
     */
    public String getName() { 
        return name; 
    }

    /**
     * Returns the highest wave number reached by the player.
     *
     * @return The highest wave number
     */
    public int getWave() { 
        return wave; 
    }

    //for the grade requirements
    /**
     * Returns a string representation of this high score entry.
     * The format is "[Player Name] - Reached Wave: [Wave Number]".
     *
     * @return A formatted string representation of the high score
     */
    @Override
    public String toString() {
        return name + " - Reached Wave: " + wave;
    }

    /**
     * Compares this high score entry with another based on wave numbers.
     * Used to sort high scores in descending order (higher waves come first).
     *
     * @param other The other HighScoreEntry to compare with
     * @return A negative integer, zero, or a positive integer if this entry
     *         has a lower, equal, or higher wave number than the other entry
     */
    @Override
    public int compareTo(HighScoreEntry other) {
        return Integer.compare(other.wave, this.wave); //sorts score asc according to waves completed 
    }
}