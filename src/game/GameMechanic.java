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

/**
 * Core game mechanics controller for Croco Defender.
 * <p>
 * This class manages all game state and logic including:
 * <ul>
 *   <li>Tower placement and management</li>
 *   <li>Enemy (crocodile) spawning and movement</li>
 *   <li>Wave progression and difficulty scaling</li>
 *   <li>Projectile handling and collision detection</li>
 *   <li>Player statistics and game state</li>
 * </ul>
 * </p>
 *
 * <p>
 * The class serves as the central authority for all game operations,
 * coordinating between UI components (GamePanel) and game entities.
 * It implements the fixed update cycle called by {@link GameLoop}.
 * </p>
 *
 * @see GameLoop
 * @see WaveControl
 * @see GamePanel
 * @see PlayerStats
 * @author Nguyen Viet Hung
 */
public class GameMechanic {
    // core 
    /** Reference to the game's UI panel */
    private GamePanel gamePanel;
    /** Player statistics and resources tracker */
    private PlayerStats player;
    /** Flag indicating if the game has ended */
    private boolean isGameOver = false;

    // Towers on the Map
    /** List of all towers placed on the map */
    private ArrayList<TowerData> towers;
    /** Currently selected tower type for placement */
    private String selectedTower = null;

    // Enemy management
    /** List of waypoints defining the path for enemies */
    private ArrayList<Point> waypoints;

    /** List of active crocodile enemies on the map */
    private ArrayList<Croco> crocos;

     /** Frame counter for controlling enemy spawn timing */
    private int spawnTimer = GameConfig.getFirstRoundStartDelay();

    // Wave management
    /** System controlling wave progression and enemy types */
    private WaveControl waveSystem = new WaveControl();

    /** Counter for enemies spawned in current wave */
    private int spawnedInCurrentWave = 0;

    /** List of active projectiles currently in flight */
    private ArrayList<Projectile> projectiles;

    // Map data
    /** Name of the currently loaded map */
    private String currentMap;

    /** Layout and configuration of the current map */
    private MapLayout loadedMap;

    /**
     * Data container for placed towers.
     * <p>
     * Stores position, graphical resource, and functional specifications
     * for each tower in the game.
     * </p>
     */
    public static class TowerData {
        /** Position of the tower on the game map */
        public Point pos;

        /** Resource identifier for the tower's graphical representation */
        public String resID;

        /** Functional specifications and behavior of the tower */
        public Tower specs;

        /** Loaded graphical texture for the tower */
        public BufferedImage texture;

        /**
         * Constructs a new TowerData instance.
         *
         * @param pos the position of the tower on the map
         * @param resID the resource identifier for the tower's graphics
         * @param specs the functional specifications of the tower
         */
        public TowerData(Point pos, String resID, Tower specs) {
            this.pos = pos;
            this.resID = resID;
            this.specs = specs;
            this.texture = Resource.getResource(resID);
        }
    }

    /**
     * Constructs a new GameMechanic instance for the specified map.
     *
     * @param mapName the name of the map to load
     */

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
     /**
     * Sets the game panel reference for UI updates.
     *
     * @param panel the GamePanel instance to associate with this game
     */
    public void setGamePanel(GamePanel panel) {
        this.gamePanel = panel;
    }

    /**
     * Sets the currently selected tower type for placement.
     *
     * @param id the resource identifier of the tower to select
     */
    public void setSelectedTower(String id) {
        this.selectedTower = id;
    }

    // --- Getters ---

    /**
     * Returns a synchronized copy of the active crocodiles.
     * <p>
     * Provides thread-safe access to the enemy list for rendering.
     * </p>
     *
     * @return a copy of the active crocodiles list
     */
    public ArrayList<Croco> getCrocos() {
        synchronized (crocos) {
            return new ArrayList<>(crocos);
        }
    }

    /**
     * Returns the name of the currently loaded map.
     *
     * @return the current map name
     */
    public String getMapName() {
        return currentMap;
    }

    /**
     * Returns the list of active projectiles.
     *
     * @return the projectiles list
     */
    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    /**
     * Returns the list of currently placed towers.
     *
     * @return the towers list
     */
    public ArrayList<TowerData> getPlacedTowers() {
        return towers;
    }

    /**
     * Returns the current game over status.
     *
     * @return true if the game has ended, false otherwise
     */
    public boolean returnGameOver() {
        return isGameOver;
    }

    // --- Game Logic Methods ---

    /**
     * Places a new tower at the specified location.
     * <p>
     * Validates that:
     * <ul>
     *   <li>A tower type is selected</li>
     *   <li>The location isn't too close to existing towers</li>
     *   <li>The player has sufficient gold</li>
     * </ul>
     * </p>
     *
     * @param p the position where the tower should be placed
     */
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
            case "coca_farm":
                newTowerStats = new towers.CocaFarm();
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
        PlayerStats.removeGold(newTowerStats.getCost());
    }


    // --- Game Logic Down Below ---
     /**
     * Updates all game state for the current frame.
     * <p>
     * Handles:
     * <ul>
     *   <li>Crocodile movement and life cycle</li>
     *   <li>Wave progression and enemy spawning</li>
     *   <li>Tower targeting and shooting</li>
     *   <li>Projectile movement and collisions</li>
     * </ul>
     * </p>
     */
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
                    PlayerStats.addGold(currentCroco.killRward());
                    crocos.remove(i);
                    Resource.playSound("kill_sound");
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
                        System.out.println("I WILL TOLERATE YOUR WEAKNESS NO LONGER.");
                        Resource.playSound("darth_vader");
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

    /**
     * Initiates the next wave of enemies.
     * <p>
     * Increments the wave counter and resets spawn tracking variables.
     * </p>
     */
    private void startNextWave() {
        waveSystem.incrementWave();
        spawnedInCurrentWave = 0;
        spawnTimer = GameConfig.getNextRoundDelay();  // spawndelay in frames so basically croco spawner spawns every 60 frames a croco but if a new wave starts subtract 120 frames so update need to count 120 frames until 1 spawn
        System.out.println("wave " + waveSystem.curentWave() + " beginns!");
    }

    // --- Render GamePanel ---
     /**
     * Triggers the game UI to update and repaint.
     */
    public void render() {
        // The Boss tells the UI to refresh
        if (gamePanel != null) {
            gamePanel.updatePlayerStats(player.getPlayerHp(), player.getGold(), waveSystem.curentWave()); // update UI!                                           
            gamePanel.repaint();
        }
    }

    /**
     * Returns the player's current gold amount.
     *
     * @return current gold amount
     */
    public int getPlayerGold() {
        return player.getGold();
    }

    /**
     * Deducts gold from the player's resources.
     *
     * @param amount the amount of gold to spend
     */
    public void spendGold(int amount) {
        PlayerStats.removeGold(amount);
    }

    /**
     * Adds gold to the player's resources.
     *
     * @param amount the amount of gold to add
     */
    public void addGold(int amount) {
        PlayerStats.addGold(amount);
    }

    /**
     * Removes a tower from the game and clears any associated UI elements.
     *
     * @param tower the tower to remove
     */
    public void sellTower(TowerData tower) {
        // ... existing sellTower ...
    }

    /**
     * Saves the player's score and handles game over procedures.
     * <p>
     * Prompts for player name, saves high score, and returns to main menu.
     * </p>
     */
    // helper method to save Game highscore 
    public void saveGameHighscore() {
        isGameOver = true;
        
        // Wir nutzen SwingUtilities, damit das Eingabefenster flüssig läuft
        javax.swing.SwingUtilities.invokeLater(() -> {
            // 1. Namenseingabe anzeigen
            String playerName = javax.swing.JOptionPane.showInputDialog(
                null, 
                "The wise warrior avoids the battle.\nWhat is your name?", 
                "Game Over", 
                javax.swing.JOptionPane.PLAIN_MESSAGE
            );

            // Failsafe falls abgebrochen wurde
            if (playerName == null || playerName.trim().isEmpty()) {
                playerName = "Secret Super Hero";
            }

            // 2. Highscore in JSON speichern
            HighScoreManager hm = new HighScoreManager();
            hm.addScore(playerName, waveSystem.curentWave()); 
            System.out.println("Highscore for " + playerName + " saved in JSON!");
            
            // 3. JETZT NEU: Fenster schließen und zurück ins Hauptmenü!
            if (gamePanel != null) {
                java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(gamePanel);
                if (window != null) {
                    window.dispose(); // Schließt das Spielfenster
                }
                new game.main_menu.MainMenu(); // Öffnet das Hauptmenü neu
            }
        });
    }



    // does the game run and which wave
    @Override
    public String toString() {
    return "GameMechanic {Wave: " + waveSystem.curentWave() + 
           ", GameOver: " + isGameOver + "}";
    }
}
