package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Manages high score storage, retrieval, and manipulation for the tower defense game.
 *
 * <p>This class provides functionality to:
 * <ul>
 *   <li>Store player high scores in a JSON-formatted file</li>
 *   <li>Load scores from the file at startup</li>
 *   <li>Add new scores and maintain them in sorted order</li>
 *   <li>Retrieve the current high score list</li>
 * </ul>
 *
 * <p>Scores are automatically sorted in descending order (highest waves first)
 * using the {@link HighScoreEntry#compareTo} method.
 *
 * @see HighScoreEntry
 */
public class HighScoreManager {
    /** Path to the JSON file used for storing high scores */
    private static final String FILE_PATH = "score.json";
    /** Collection of high score entries, sorted by wave number */
    private ArrayList<HighScoreEntry> scores;


    /**
     * Constructs a new HighScoreManager and loads existing scores from the storage file.
     *
     * <p>If no existing score file is found, an empty score list is created.
     *
     * @see #loadScores()
     */
    public HighScoreManager() {
        scores = new ArrayList<>();
        loadScores();
    }


    /**
     * Adds a new high score to the collection and persists it to the storage file.
     *
     * <p>After adding the new score, the collection is re-sorted in descending order
     * and saved to the JSON file.
     *
     * @param name The name of the player who achieved the score
     * @param wave The highest wave number reached by the player
     * @see #saveScores()
     * @see HighScoreEntry
     */
    public void addScore(String name, int wave) {
        scores.add(new HighScoreEntry(name, wave));
        Collections.sort(scores); // asks the method compareTo in HighScoreEntry.java to compare 2 queries in  array
        saveScores();
    }

    // getter
    /**
     * Returns the current list of high scores.
     *
     * <p>The returned list is the actual collection used by this manager and
     * should not be modified directly. Scores are already sorted in descending order.
     *
     * @return An ArrayList of HighScoreEntry objects representing current high scores
     * @see HighScoreEntry
     */
    public ArrayList<HighScoreEntry> getScores() {
        return scores;
    }


    /**
     * Saves the current collection of high scores to the JSON storage file.
     *
     * <p>The scores are written in valid JSON format with the following structure:
     * <pre>
     * [
     *  {
     *    "name": "Player1",
     *    "wave": "25"
     *  },
     *  {
     *    "name": "Player2",
     *    "wave": "20"
     *  }
     * ]
     * </pre>
     *
     * @throws IOException If an I/O error occurs while writing to the file
     */
    private void saveScores() {
        try (BufferedWriter write = new BufferedWriter(new FileWriter(FILE_PATH))) {
            write.write("[");
            write.newLine();

            for (int i = 0; i < scores.size(); i++) {
                HighScoreEntry oneScore = scores.get(i);

                write.write(" {");
                write.newLine();
                write.write("    \"name\": \"" + oneScore.getName() + "\",");
                write.newLine();
                write.write("    \"wave\": \"" + oneScore.getWave() + "\",");
                write.newLine();

                if (i < scores.size() - 1) {
                    write.write("   }");
                    write.newLine();
                } else {
                    write.write("   }");
                    write.newLine();
                }
            }
            write.write("]");
            write.newLine();

        } catch (IOException e) {
            System.out.println("Error creating json file " + e.getMessage());
        }
    }


    /**
     * Loads high scores from the JSON storage file into memory.
     *
     * <p>This method:
     * <ul>
     *   <li>Checks if the score file exists first</li>
     *   <li>Parses the JSON file line by line</li>
     *   <li>Extracts name and wave information</li>
     *   <li>Creates HighScoreEntry objects for each valid score found</li>
     *   <li>Sorts the loaded scores in descending order</li>
     * </ul>
     *
     * <p>If the file exists but is corrupted, an error message is printed
     * to the console and an empty score list is maintained.
     *
     * @see HighScoreEntry
     */
    private void loadScores(){
        File file = new File(FILE_PATH);
        if ( !file.exists()){
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            String currentName = null;
            int currentWave = -1;

            //read json file line for line 
        while ((line = reader.readLine()) != null){
            line = line.trim();        // trims all spaces start and EOF 

            if (line.startsWith("\"name\":")){
                currentName = line.split("\"")[3];

            } else if (line.startsWith("\"wave\":")) {
                String numStr = line.replaceAll("[^0-9]","");
                currentWave = Integer.parseInt(numStr);

            } else if (line.startsWith("}") || line.startsWith("},")) {
                    // EOF
                    if (currentName != null && currentWave != -1) {
                        scores.add(new HighScoreEntry(currentName, currentWave));
                        currentName = null; 
                        currentWave = -1;   
                    }
                }   
            }
            Collections.sort(scores); 
        } catch (Exception e){
            System.out.println("Error loading Highscore file " + e.getMessage());
        }    
    }
}
