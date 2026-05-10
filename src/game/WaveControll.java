package game;

import java.util.ArrayList;





public class WaveControll {


    private int wave = 1;
    private int spawnDelay = 60;
    private int totalCrocosThisWave;

    //List for spawning Diffrent types of crocs
    private ArrayList<String> spawnPattern = new ArrayList<>();

    private ArrayList<String> spawnQ = new ArrayList<>(); //Bug fixed: Seperate Lists for the the copy lastPattern mechancic


    public WaveControll(){
        
        prepareNextWave(); 
    }

    public void prepareNextWave(){
        ArrayList<String> lastPattern = new ArrayList<>(spawnPattern); // saves last Pattern 
        spawnPattern.clear(); // clear list of previous Pattern 
        


        // now kust define the next waves introducing new crocs and than we
        // we do a formula for combining last and now pattern together for next wave
        if (wave == 1){
            for (int i= 0;i<10;i++){
                spawnPattern.add("basic_croco");
            }
        } else if ( wave == 3){
            for (int i= 0;i<5;i++){
                spawnPattern.add("speedy_croco");
                spawnPattern.add("basic_croco");
                
            }
        } else {
             for (int i=0; i<lastPattern.size()/2; i++) { // formula for appending last Pattern/2
            spawnPattern.add(lastPattern.get(i));
             }

        }

       for (int i=0; i<lastPattern.size()/2; i++) { // formula for appending last Pattern/2
            spawnPattern.add(lastPattern.get(i));
        }
        totalCrocosThisWave = spawnPattern.size();   
        
        // transfer last Pattern to list that gets pulled
        spawnQ = new ArrayList<>(lastPattern);
    }


    //Getters
    public String pullNextCrocoType(){
        if (spawnQ != null && !spawnQ.isEmpty()) {
            return spawnQ.remove(0); // removes and returns fisrt croco
        } else {
            return "basic_croco";
        }
    }

    public int getCrocosToSpawn(){
        return totalCrocosThisWave;
    }

    public int curentWave(){
        return wave;
    }
    public int getSpawnDelay(){
        return spawnDelay;
    }

    
    // Setters 
    public void incrementWave(){
        this.wave=this.wave+1;
        prepareNextWave();
    }


}
