package game;

import maps.MapLayout;
import maps.Mt_Croco;
import maps.Swamp_Map;
import player.PlayerStats;
import projectiles.Projectile;
import Crocodiles.*;
import towers.Tower;

import java.awt.Point;
import java.awt.image.BufferedImage;
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
    private ArrayList<Croco> crocos; // add wave managaging class

    // spawn Timer for crocs
    private int spawnTimer = GameConfig.getFirstRoundStartDelay(); // counts frames negative because start of game needs a little delay to palce first tower
                                   

    // some variables for wavecontroll
    private WaveControl waveSystem = new WaveControl();
    private int spawnedInCurrentWave = 0;

    // Projectile stuff
    private ArrayList<Projectile> projectiles;

    //
    private String currentMap;
    private MapLayout loadedMap;

    // own class to store references to resouce manager
    public static class TowerData {
        public Point pos;
        public String resID;
        public Tower specs;
        public BufferedImage texture;

        public TowerData(Point pos, String resID, Tower specs) {
            this.pos = pos;
            this.resID = resID;
            this.specs = specs;
            this.texture = Resource.getResource(resID);
        }
    }

    public GameMechanic(String mapName) {

        this.crocos = new ArrayList<>();
        this.towers = new ArrayList<>();
        this.player = new PlayerStats(); // later add the player and gold and stuff
        this.projectiles = new ArrayList<>();
        this.currentMap = mapName;

        // laod in map
        // add all map Keys here

        switch (currentMap) {
            case "swamp_map":
                loadedMap = new Swamp_Map();
                break;
            case "mt_croco":
                loadedMap = new Mt_Croco();
                break;
        }
        this.waypoints = loadedMap.getWaypoints();
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
        synchronized (crocos) { // let the gamepanel wait before it draws anopther one
            return new ArrayList<>(crocos);
        }
    }

    public String getMapName() {
        return currentMap;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles; // reference to GamePanel
    }

    public ArrayList<TowerData> getPlacedTowers() {
        return towers;
    }

    public boolean returnGameOver() {
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

        Tower newTowerStats;

        // Decide which Tower class to instantiate based on the ID string
        switch (selectedTower) {
            case "basic_tower":
                // Assuming you created a CannonT class in your towers package!
                newTowerStats = new towers.BasicT();
                break;
            case "sniper_tower":
                newTowerStats = new towers.SniperT();
                break;
            case "duck_tower":
                newTowerStats = new towers.DuckT();
                break;
            case "tank_tower":
                newTowerStats = new towers.TankT();
                break;
            case "strong_duck":
                newTowerStats = new towers.StrongDuckT();
                break;
            default:
                newTowerStats = new towers.BasicT();
                System.out.println("newTowerStats coudn't load in Tower");
                break;
        }

        if (player.getGold() < newTowerStats.getCost()) {
            System.out.println("Not enough Gold");
            return;
        }
        // If it passes the rules, add it

        towers.add(new TowerData(p, selectedTower, newTowerStats));
        // System.out.println("Placed " + selectedTower + " at " + p.x + "," + p.y);
        player.removeGold(newTowerStats.getCost());
    }


    // --- Game Logic Down Below ---
    public void update() {

        if (isGameOver) {
            return;
        }

        // --- Croco movement ---
        // Loop backwards safely
        synchronized (crocos) {
            for (int i = crocos.size() - 1; i >= 0; i--) {
                Croco currentCroco = crocos.get(i);

                // Check if Crocodiles have hp
                if (currentCroco.getHealth() <= 0) {
                    System.out.println("+" + currentCroco.killRward());
                    this.player.addGold(currentCroco.killRward());
                    crocos.remove(i);
                    Resource.playSound("kill_sound");
                    continue; // jumps to next croco
                }

                // Tell the crocodile to figure out its own movement
                currentCroco.move(waypoints);

                // Did it reach the end of the map?
                if (currentCroco.hasReachedEnd()) {
                    System.out.println(currentCroco.getCrocoType() + " reached the base! You took"
                            + currentCroco.getDmg() + "damage!");
                    this.player.takeDamage(currentCroco.getDmg());
                    crocos.remove(i); // Delete it from the game

                    // --- check if playerHp = 0 then game over
                    if (this.player.getPlayerHp() <= 0) {
                        isGameOver = true;
                        System.out.println("I WILL TOLERATE YOUR WEAKNESS NO LONGER.");
                        saveGameHighscore();
                    }

                }
            }
        }

        // Check if wavge is active
        if (spawnedInCurrentWave >= waveSystem.getCrocosToSpawn() && crocos.isEmpty()) {
            startNextWave();
        }

        // --- Croco spawner ---
        spawnTimer++; // counts frames

        if (spawnTimer < 0 && spawnTimer % 60 == 0) {
            System.out.println("next wave in " + (spawnTimer / -60));
        }

        if (spawnTimer >= 0 && spawnedInCurrentWave < waveSystem.getCrocosToSpawn()) { // checks if max Croc limit is
                                                                                       // reached and spawn delay
            if (spawnTimer > waveSystem.getSpawnDelay()) { // checks for nex wave spawn time
                String nextCrocoType = waveSystem.pullNextCrocoType();

                // -- spawns crocoType acording to WaveControll --

                switch (nextCrocoType) {
                    case "basic_croco":
                        crocos.add(new BasicCroco(waypoints));
                        break;
                    case "speedy_croco":
                        crocos.add(new SpeedyCroco(waypoints));
                        break;
                    case "mid_croco":
                        crocos.add(new MidCroco(waypoints));
                        break;
                    case "fat_croco":
                        crocos.add(new FatCroco(waypoints));
                    default:
                        break;
                }

                spawnedInCurrentWave++;
                spawnTimer = 0;
            }
        }

        // --- TOWER SHOOTING LOGIC ---
        long currentTime = System.currentTimeMillis();

        synchronized (towers){
            for (TowerData tower : towers){
                tower.specs.updateShooting(currentTime, tower.pos, crocos, projectiles);
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

    

    private void startNextWave() {
        waveSystem.incrementWave();
        spawnedInCurrentWave = 0;
        spawnTimer = GameConfig.getNextRoundDelay();  // spawndelay in frames so basically croco spawner spawns every 60 frames a croco but if a new wave starts subtract 120 frames so update need to count 120 frames until 1 spawn
        System.out.println("wave " + waveSystem.curentWave() + " beginns!");
    }

    // --- Render GamePanel ---
    public void render() {
        // The Boss tells the UI to refresh
        if (gamePanel != null) {
            gamePanel.updatePlayerStats(player.getPlayerHp(), player.getGold(), waveSystem.curentWave()); // update UI!                                           
            gamePanel.repaint();
        }
    }

    // some helper methods to control spend gold on Upgrades

    // In GameMechanic.java
    public int getPlayerGold() {
        return player.getGold(); // Getter
    }

    public void spendGold(int amount) {
        player.removeGold(amount); // setter
    }

    public void addGold(int amount) {
        player.addGold(amount);
    }

    public void sellTower(TowerData tower) {
        synchronized (towers) { // when projectile still lives and sell tower --> ConcurrentModificationException Fixed!
            towers.remove(tower);
        }
        if (gamePanel != null) {
            gamePanel.clearRangeHighlight();
        }
    }

    // helper method to save Game highscore 
    public void saveGameHighscore() {
        isGameOver = true;
        //opens new Panel for Player name input
        String playerName = javax.swing.JOptionPane.showInputDialog(
            null, 
            "The wise warrior avoids the battle.\nWhat is your name?", 
            "Game Over", 
            javax.swing.JOptionPane.PLAIN_MESSAGE
        );

        //failsafe if player only types spaces or aborts window 
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Secret Super Hero";
        }

        HighScoreManager hm = new HighScoreManager();
        hm.addScore(playerName, waveSystem.curentWave()); 
        System.out.println("Highscore for " + playerName + " saved in JSON!");
    }

}
