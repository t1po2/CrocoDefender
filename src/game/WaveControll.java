package game;

import java.util.ArrayList;

import Crocodiles.TestCroco;

import java.awt.Point;


public class WaveControll {


    private int spawnCrocos;
    private int wave = 1;
    private int spawnDelay = 60;



    public WaveControll(){
        
        prepareNextWave(); 
    }

    public void prepareNextWave(){
        this.spawnCrocos = 5 + (wave*5);

        if (spawnDelay > 20) {
            spawnDelay -= 2; 
        }


    }






    //Getters

    public int getCurrentWave(){
        return spawnCrocos;
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
