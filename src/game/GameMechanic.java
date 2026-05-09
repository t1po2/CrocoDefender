package game;

import maps.MapLayout;
import maps.Test_map;
import player.PlayerStats;
import Crocodiles.*;

import java.awt.Point;
import java.util.ArrayList;



public class GameMechanic {
    //core 
    private GamePanel gamePanel;
    private PlayerStats player;

    // Towers on the Map 
    private ArrayList<TowerData> towers;
    private String selectedTower = null;

    // Crocos on the map + pathfinding
   private ArrayList<Point> waypoints;
   private ArrayList<Croco> crocos;


   //spawn Timer for crocs
    private int spawnedCrocos = 0; // Wie viele wurden schon gespawnt?
    private int maxCrocos = 10;    // Wie viele sollen maximal spawnen?
    private int spawnTimer = 0;    // Zählt die Frames (Ticks)
    private int spawnDelay = 60;

    







    //own class to store references to resouce manager
    public static class TowerData {
        public Point pos;
        public String resID;

        public TowerData(Point pos, String resID){
            this.pos = pos;
            this.resID = resID;
        }
    }

    public GameMechanic(){

        this.crocos = new ArrayList<>();
        this.towers = new ArrayList<>();
        this.player = new PlayerStats();        //later add the player and gold and stuff

        // laod in map
        MapLayout currentMap = new Test_map();
        this.waypoints = currentMap.getWaypoints();
        
     
    }


    // Connect the UI to the brain
    public void setGamePanel(GamePanel panel) {
        this.gamePanel = panel;
    }

    public void setSelectedTower(String id){
        this.selectedTower = id;
    }

    public void attemptPlacement(Point p) {
        if (selectedTower == null) return; // Drop out if nothing is selected

        // Don't place a tower too close to another tower
        for (TowerData existing : towers) {
            double distance = p.distance(existing.pos);
            if (distance < 50) {   // cant place tower within 50 pixels of their radius 
                System.out.println("Cannot place here! Too close to another tower.");
                return;     // Deny placement
            }
        }

        // If it passes the rules, add it
        towers.add(new TowerData(p, selectedTower));
        System.out.println("Placed " + selectedTower + " at " + p.x + "," + p.y);
    }

    public ArrayList<TowerData> getPlacedTowers() {
        return towers;
    }
    public ArrayList<Croco> getCrocos() {
        synchronized (crocos){
            return new ArrayList<>(crocos);
            // This acts like a "lock." If the GameLoop is currently adding a crocodile, the UI thread will wait a tiny fraction of a millisecond for it to finish before it tries to grab the list for drawing.
        }

    }

    public void update() {
        // Game Loop Logic: Move enemies, shoot bullets, etc


        //movement of crocos
        // Loop backwards safely
        synchronized (crocos){
            for (int i = crocos.size() - 1; i >= 0; i--) {
            Croco currentCroco = crocos.get(i);
            
            // Tell the crocodile to figure out its own movement
            currentCroco.move(waypoints);

            // Did it reach the end of the map?
            if (currentCroco.hasReachedEnd()) {
                System.out.println(currentCroco.getCrocoType() + " reached the base! You took damage!");
                crocos.remove(i); // Delete it from the game
            }
        }
          // --- Croco spawner ---
        if (spawnedCrocos < maxCrocos && waypoints != null && !waypoints.isEmpty()) {
            spawnTimer++; // Timer zählt jeden Frame hoch
            
            if (spawnTimer >= spawnDelay) {
                //times up spawn new croco
                crocos.add(new TestCroco(waypoints));
                spawnedCrocos++; // increment
                spawnTimer = 0;  // reset timer for next croco
            }
        }
    }
        
        

    }
    public void render(){
        // The Boss tells the UI to refresh
        if (gamePanel != null) {
            gamePanel.repaint();

        }
    }

}