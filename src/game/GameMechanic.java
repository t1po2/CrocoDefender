package game;

import maps.MapLayout;
import maps.Swamp_Map;
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
    private ArrayList<Croco> crocos; // add wave managaging class

    // spawn Timer for crocs
    private int spawnTimer = -600; // counts frames negative because start of game needs a little delay to palce first tower

    // some variables for wavecontroll
    private WaveControl waveSystem = new WaveControl();
    private int spawnedInCurrentWave = 0;

    // Projectile stuff
    private ArrayList<Projectile> projectiles;

    //
    private String currentMap;


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

    public GameMechanic(String mapName) {

        this.crocos = new ArrayList<>();
        this.towers = new ArrayList<>();
        this.player = new PlayerStats(); // later add the player and gold and stuff
        this.projectiles = new ArrayList<>();
        this.currentMap = mapName;

        // laod in map 
        //add all map Keys here

        if (currentMap.equals("swamp_map")){
            MapLayout currentMap = new Swamp_Map();
            this.waypoints = currentMap.getWaypoints(); 
        }
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
        synchronized (crocos) {     //let the gamepanel wait before it draws anopther one
            return new ArrayList<>(crocos);
        }
    }

    public String getMapName(){
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
            default:
                newTowerStats = new towers.BasicT();
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
                    continue; // jumps to next croco
                }

                // Tell the crocodile to figure out its own movement
                currentCroco.move(waypoints);

                // Did it reach the end of the map?
                if (currentCroco.hasReachedEnd()) {
                    System.out.println(currentCroco.getCrocoType() + " reached the base! You took"+ currentCroco.getDmg() + "damage!");
                    this.player.takeDamage(currentCroco.getDmg());
                    crocos.remove(i); // Delete it from the game

                    // --- check if playerHp = 0 then game over
                    if (this.player.getPlayerHp() <= 0) {
                        isGameOver = true;
                        System.out.println("GAME OVER! Die Krokodile haben die Basis zerstört.");

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

        if (spawnTimer >= 0 && spawnedInCurrentWave < waveSystem.getCrocosToSpawn()) { // checks if max Croc limit is reached and spawn delay                                            
            if (spawnTimer > waveSystem.getSpawnDelay()) { // checks for nex wave spawn time
                String nextCrocoType = waveSystem.pullNextCrocoType();


                // -- spawns crocoType acording to WaveControll -- 

                
                switch (nextCrocoType) {
                    case "basic_croco":
                        crocos.add(new TestCroco(waypoints));
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

    private void startNextWave() {
        waveSystem.incrementWave();
        spawnedInCurrentWave = 0;
        spawnTimer = -240; // spawndelay in frames so basically croco spawner spawns every 60 frames a
                           // croco but if a new wave starts subtract 120 frames so update need to count
                           // 120 frames until 1 spawn
        System.out.println("Welle " + waveSystem.curentWave() + " startet!");
    }

    // --- Render GamePanel ---
    public void render() {
        // The Boss tells the UI to refresh
        if (gamePanel != null) {
            gamePanel.updatePlayerStats(player.getPlayerHp(), player.getGold(),waveSystem.curentWave()); // UI aktualisieren!
            gamePanel.repaint();

        }
    }

}
