package game;

import java.util.ArrayList;
import java.util.Random;



/**
 * Manages enemy wave progression, spawning patterns, and difficulty scaling
 * in the tower defense game.
 *
 * <p>This class controls:
 * <ul>
 *   <li>Wave numbering and progression</li>
 *   <li>Enemy spawning patterns for each wave</li>
 *   <li>Introduction of new enemy types at specific waves</li>
 *   <li>Difficulty scaling by adjusting the quantity and types of enemies</li>
 *   <li>Spawn timing between enemies</li>
 * </ul>
 *
 * <p>The class implements a wave preparation system that creates interesting,
 * increasingly challenging enemy spawn patterns while maintaining gameplay flow.
 * Each wave introduces more enemies and often new enemy types, building upon
 * patterns from previous waves.
 *
 * @see GameConfig
 */
public class WaveControl {

    /** Current wave number */
    private int wave = 1;
    /** Delay between enemy spawns in milliseconds */
    private int spawnDelay = GameConfig.getSpawnDelay();

    /** Total number of crocodiles that will spawn in the current wave */
    private int totalCrocosThisWave;

    /**
     * Blueprint for enemy spawning pattern in current wave.
     * This list defines the ordered sequence of enemies that will spawn.
     */
    private ArrayList<String> spawnPattern = new ArrayList<>();

    /**
     * Active spawning queue that tracks which enemies still need to spawn.
     * This is initialized from spawnPattern and enemies are removed as they spawn.
     */
    private ArrayList<String> spawnQ = new ArrayList<>();

    /**
     * Constructs a new WaveControl and prepares the initial wave.
     *
     * @see #prepareNextWave()
     */



    public WaveControl(){
        
        prepareNextWave(); 
    }



    /**
     * Prepares and configures the spawn pattern for the next wave.
     *
     * <p>This method:
     * <ul>
     *   <li>Creates specific enemy patterns for milestone waves</li>
     *   <li>Builds intermediate waves by combining previous patterns</li>
     *   <li>Introduces new enemy types at predetermined waves</li>
     *   <li>Calculates the total number of enemies for the wave</li>
     *   <li>Initializes the spawn queue</li>
     * </ul>
     *
     * <p>Special waves that introduce new enemy types include:
     * <ul>
     *   <li>Wave 1: Basic crocodiles</li>
     *   <li>Wave 3: Speedy crocodiles</li>
     *   <li>Wave 6: Mid-level and fat crocodiles</li>
     *   <li>Wave 10, 15, 20: Arnab boss crocodile</li>
     * </ul>
     */
    public void prepareNextWave(){
        ArrayList<String> lastPattern = new ArrayList<>(spawnPattern); // saves last Pattern 
        spawnPattern.clear(); // clear list of previous Pattern 
        
        // now kust define the next waves introducing new crocs and than we
        // we do a formula for combining last and now pattern together for next wave

        
        

        String[] quotes = {
            "Let your plans be dark and impenetrable as night, and when you move, fall like a thunderbolt.",
            "“In the midst of chaos, there is also opportunity”",
            "“To know your Enemy, you must become your Enemy.”",
            "“Treat your men as you would your own beloved sons. And they will follow you into the deepest valley.”",
            "“Even the finest sword plunged into salt water will eventually rust.”"
        };
        int len = quotes.length;
        Random randInt = new Random();
        int rng = randInt.nextInt(len);
        System.out.println(quotes[rng]);

        // -- Configure Wave difficulty --


        if (wave == 1){
            for (int i= 0;i<20;i++){
                spawnPattern.add("basic_croco");
            }
        } else if ( wave == 3){                        //wave 3 +speedy_croco
            for (int i= 0;i<10;i++){
                spawnPattern.add("speedy_croco");
                spawnPattern.add("basic_croco");
            }
        } else if (wave==6){                        // wave 6   +mid_croco , +fat_croco
            spawnPattern.add("fat_croco");
            for (int i=0;i<30;i++){
                spawnPattern.add("mid_croco");
            } 
        } else if (wave== 7){                       // wave 7
            for (int i = 0;i<30;i++){
                spawnPattern.add("basic_croco");
            }
        } else if (wave == 8){                  //wave 8   +fat_Croco
            for (int i = 0;i<20;i++){
                spawnPattern.add("fat_croco");
            }
            for (int i = 0 ; i<10;i++){
                spawnPattern.add("speedy_croco");
                spawnPattern.add("mid_croco");
            }
            for (int i=0;i<40;i++){
                spawnPattern.add("basic_croco");
            }
        } else if (wave == 10){
            spawnPattern.add("arnab");
        } else if (wave == 15){
            for (int i = 0 ; i<5;i++){
            spawnPattern.add("arnab");
            }
        } else if (wave == 20){
            spawnPattern.add("arnab");
            spawnPattern.add("arnab");
        } else {
            for (int i=0; i<lastPattern.size()/2; i++) { //for the rounds in between without new Crocos
                spawnPattern.add(lastPattern.get(i));
            }
        }
       spawnPattern.addAll(lastPattern);

        totalCrocosThisWave = spawnPattern.size();   
        // transfer last Pattern to list that gets pulled
        spawnQ = new ArrayList<>(spawnPattern);
    }

    /**
     * Retrieves and removes the next crocodile type to spawn from the queue.
     *
     * @return The resource ID of the next crocodile type to spawn,
     *         or "basic_croco" if the queue is empty
     */
    //Getters
    public String pullNextCrocoType(){
        if (spawnQ != null && !spawnQ.isEmpty()) {
            return spawnQ.remove(0); // removes and returns fisrt croco
        } else {
            return "basic_croco";
        }
    }


     /**
     * Returns the total number of crocodiles that will spawn in the current wave.
     *
     * @return The size of the current wave's spawn pattern
     */
    public int getCrocosToSpawn(){
        return totalCrocosThisWave;
    }


    /**
     * Returns the current wave number.
     *
     * @return The 1-based index of the current wave
     */
    public int curentWave(){
        return wave;

    /**
     * Returns the configured delay between enemy spawns.
     *
     * @return The spawn delay in milliseconds
     */
    }
    public int getSpawnDelay(){
        return spawnDelay;
    }

    
    // Setters
     /**
     * Advances to the next wave and prepares its spawn pattern.
     *
     * <p>This method is typically called when the current wave is defeated
     * to advance the game progression.
     *
     * @see #prepareNextWave()
     */ 
    public void incrementWave(){
        this.wave=this.wave+1;
        prepareNextWave();
    }


}
