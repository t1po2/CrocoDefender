package game;

import java.util.ArrayList;
import java.util.Random;




public class WaveControl {


    private int wave = 1;
    private int spawnDelay = GameConfig.getSpawnDelay();
    private int totalCrocosThisWave;

    //List for spawning Diffrent types of crocs
    private ArrayList<String> spawnPattern = new ArrayList<>(); // this list is the Blue prinbt for the spawnQ later last pattern gets appended to this list

    private ArrayList<String> spawnQ = new ArrayList<>(); //Bug fixed: Seperate Lists for the the copy lastPattern mechancic sapwnQ is the List that gets subtracted 


    public WaveControl(){
        
        prepareNextWave(); 
    }

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
            for (int i= 0;i<25;i++){
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
                spawnPattern.addAll(lastPattern);
            }
        }
       for (int i=0; i<lastPattern.size()/2; i++) { // formula for appending last Pattern/2
            spawnPattern.add(lastPattern.get(i));
        }

        totalCrocosThisWave = spawnPattern.size();   
        // transfer last Pattern to list that gets pulled
        spawnQ = new ArrayList<>(spawnPattern);
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
