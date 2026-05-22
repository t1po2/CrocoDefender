package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class HighScoreManager {
    private static final String FILE_PATH = "score.json";
    private ArrayList<HighScoreEntry> scores;

    public HighScoreManager() {
        scores = new ArrayList<>();
        loadScores();
    }

    public void addScore(String name, int wave) {
        scores.add(new HighScoreEntry(name, wave));
        Collections.sort(scores); // asks the method compareTo in HighScoreEntry.java to compare 2 queries in  array
        saveScores();
    }

    // getter
    public ArrayList<HighScoreEntry> getScores() {
        return scores;
    }

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
