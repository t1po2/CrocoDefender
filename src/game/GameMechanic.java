package game;

import maps.MapLayout;
import maps.Test_map;
import player.PlayerStats;
import Crocodiles.*;
import towers.Projectile;
import towers.Tower;

import java.awt.Point;
import java.util.ArrayList;

public class GameMechanic {
    // core
    private GamePanel gamePanel;
    private PlayerStats player;
    private boolean isGameOver = false;

    // Towers on the Map
    private ArrayList<TowerData> towers;
    private String selectedTower = null;

    // Crocos on the map + pathfinding
    private ArrayList<Point> waypoints;
    private ArrayList<Croco> crocos;

    // spawn Timer for crocs
    private int spawnedCrocos = 0; // Wie viele wurden schon gespawnt?
    private int maxCrocos = 250; // Wie viele sollen maximal spawnen?
    private int spawnTimer = 0; // Zählt die Frames (Ticks)
    private int spawnDelay = 60; // after x frames it spawn 1 new croco

    // Projectile stuff
    private ArrayList<Projectile> projectiles;

    // own class to store references to resouce manager
    public static class TowerData {
        public Point pos;
        public String resID;
        public Tower specs;
        public long lastShotTime = 0;

        public TowerData(Point pos, String resID, Tower specs) {
            this.pos = pos;
            this.resID = resID;
            this.specs = specs;
        }
    }

    public GameMechanic() {

        this.crocos = new ArrayList<>();
        this.towers = new ArrayList<>();
        this.player = new PlayerStats(); // later add the player and gold and stuff
        this.projectiles = new ArrayList<>();

        // laod in map
        MapLayout currentMap = new Test_map();
        this.waypoints = currentMap.getWaypoints();
    }





    // --- Setters ---

    public void setGamePanel(GamePanel panel) {
        this.gamePanel = panel; // Connect the UI to the brain
    }

    public void setSelectedTower(String id) {
        this.selectedTower = id;
    }





    // --- Getters ---

    public ArrayList<Croco> getCrocos() {
        synchronized (crocos) {
            return new ArrayList<>(crocos);
            // This acts like a "lock." If the GameLoop is currently adding a crocodile, the
            // UI thread will wait a tiny fraction of a millisecond for it to finish before
            // it tries to grab the list for drawing.
        }

    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles; // reference to GamePanel
    }

    public ArrayList<TowerData> getPlacedTowers() {
        return towers;
    }
    public boolean returnGameOver(){
        return isGameOver;
    }
    







    // --- Place Tower ---
    public void placeTower(Point p) {
        if (selectedTower == null)
            return; // Drop out if nothing is selected

        

        // Don't place a tower too close to another tower
        for (TowerData existing : towers) {
            double distance = p.distance(existing.pos);
            if (distance < 50) { // cant place tower within 50 pixels of their radius
                System.out.println("Cannot place here! Too close to another tower.");
                return; // Deny placement
            }
        }


        Tower newTowerStats = new towers.BasicT(); // NOT GOOD AT ALL I HAVBE TO FIX THAT THERE IS ONLZ ONE TOWER TYPE?
        if (player.getGold() < newTowerStats.getCost()) { 
            System.out.println("Not enough Gold");
            return;
        }
        // If it passes the rules, add it
        towers.add(new TowerData(p, selectedTower, newTowerStats));
        System.out.println("Placed " + selectedTower + " at " + p.x + "," + p.y);
        player.removeGold(newTowerStats.getCost());
    }









    // --- Game Logic Down Below ---
    public void update() {

        if(isGameOver){
            return;
        }
        



        // --- Croco movement ---
        // Loop backwards safely
        synchronized (crocos) {
            for (int i = crocos.size() - 1; i >= 0; i--) {
                Croco currentCroco = crocos.get(i);

                // Check if Crocodiles have hp
                if (currentCroco.getHealth() <= 0) {
                    System.out.println("Crocodile defeated! You earned gold!"); // add player.addGold here later!
                    this.player.addGold(currentCroco.killRward());
                    crocos.remove(i);
                    continue; // jumps to next croco
                }

                // Tell the crocodile to figure out its own movement
                currentCroco.move(waypoints);

                // Did it reach the end of the map?
                if (currentCroco.hasReachedEnd()) {
                    System.out.println(currentCroco.getCrocoType() + " reached the base! You took" + currentCroco.getDmg() + "damage!");
                    this.player.takeDamage(currentCroco.getDmg());
                    crocos.remove(i); // Delete it from the game

                    // --- check if playerHp = 0 then game over
                    if (this.player.getPlayerHp() <= 0) {
                        isGameOver = true;
                        System.out.println("GAME OVER! Die Krokodile haben die Basis zerstört.");
                       
                    }

                }
            }
            // --- Croco spawner ---
            if (spawnedCrocos < maxCrocos && waypoints != null && !waypoints.isEmpty()) {
                spawnTimer++; // Timer zählt jeden Frame hoch

                if (spawnTimer >= spawnDelay) {
                    // times up spawn new croco
                    crocos.add(new TestCroco(waypoints));
                    spawnedCrocos++; // increment
                    spawnTimer = 0; // reset timer for next croco
                }
            }



            // --- TOWER SHOOTING LOGIC ---
            long currentTime = System.currentTimeMillis();
            for (TowerData tower : towers) {
                // Check if the tower is ready to shoot based on its fire rate
                if (currentTime - tower.lastShotTime >= tower.specs.getFireRate()) {

                    // Scan all crocos to find a target in range
                    for (Croco target : crocos) {
                        double dist = tower.pos.distance(target.getX(), target.getY());

                        if (dist <= tower.specs.getRange()) {
                            // Target found! Create projectile and reset the tower's cooldown timer
                            projectiles.add(new Projectile(tower.pos.x, tower.pos.y, target, tower.specs.getDamage()));
                            tower.lastShotTime = currentTime;
                            break; // Break the loop so it only shoots ONE croco at a time
                        }
                    }
                }
            }
            // --- PROJECTILE MOVEMENT ---
            for (int i = projectiles.size() - 1; i >= 0; i--) {
                Projectile p = projectiles.get(i);
                p.update();
                if (!p.isActive()) {
                    projectiles.remove(i);
                }
            }
        }
    }





    // --- Render GamePanel ---
    public void render() {
        // The Boss tells the UI to refresh
        if (gamePanel != null) {
            gamePanel.updatePlayerStats(player.getPlayerHp(), player.getGold()); // UI aktualisieren!
            gamePanel.repaint();

        }
    }

}