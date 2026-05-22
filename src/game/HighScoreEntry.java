package game;



/**
 * This class is used to write a HighScoreEntry
 * 
 * 
 * @author Nguyen Viet Hung
 */


public class HighScoreEntry implements Comparable<HighScoreEntry> {
    private String name;
    private int wave;

    public HighScoreEntry(String name, int wave) {
        this.name = name;
        this.wave = wave;
    }



    //Getters
    public String getName() { 
        return name; 
    }

    public int getWave() { 
        return wave; 
    }

    //for the grade requirements
    @Override
    public String toString() {
        return name + " - Reached Wave: " + wave;
    }

    @Override
    public int compareTo(HighScoreEntry other) {
        return Integer.compare(other.wave, this.wave); //sorts score asc according to waves completed 
    }
}