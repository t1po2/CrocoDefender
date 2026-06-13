package game;

// this class file loads all nessecery game resources before launching saving memory and increases

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL; // Needs this lib to compress all pngs and wav to .jar file
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;



/**
 * Central resource management system for the tower defense game.
 *
 * <p>This class handles loading, caching, and providing access to all game resources including:
 * <ul>
 *   <li>Game maps (PNG images)</li>
 *   <li>Tower sprites (PNG images)</li>
 *   <li>Crocodile enemy sprites (PNG images)</li>
 *   <li>Projectile sprites (PNG images)</li>
 *   <li>Sound effects (WAV files)</li>
 * </ul>
 *
 * <p>All resources are loaded once at game startup and stored in memory for efficient access
 * during gameplay. The class implements a resource pooling system for sound effects to
 * allow multiple simultaneous playbacks of the same sound.
 *
 * @see BufferedImage
 * @see Clip
 */

public class Resource {

    //String type is the resourceID
    /** Cache for storing loaded game images with their resource IDs as keys */
    private static final Map<String, BufferedImage> gameResources = new HashMap<>();
    
    // Clip[] for overlapping sounds
     /**
     * Cache for storing sound effect pools.
     * Each sound entry contains an array of Clip objects to enable overlapping playback.
     */
    private static final Map<String, Clip[]> gameSounds = new HashMap<>();

    /**
     * Preloads all game resources including images and sound effects.
     *
     * <p>This method should be called once at game startup. It loads:
     * <ul>
     *   <li>Map textures</li>
     *   <li>Crocodile enemy sprites</li>
     *   <li>Tower sprites</li>
     *   <li>Projectile sprites</li>
     *   <li>Sound effects with multiple instances for overlapping playback</li>
     * </ul>
     *
     * @throws RuntimeException if any resource fails to load
     * @see #loadClipPool(String, int)
     */
    public static void loadGameResources(){

        // loading all nessecary game resources 
        try {
            //map 
            gameResources.put("swamp_map", ImageIO.read(Resource.class.getResource("/resources/maps/swamp_map.png")));
            gameResources.put("mt_croco", ImageIO.read(Resource.class.getResource("/resources/maps/mt_croco.png")));
            
            //Croco textures
            gameResources.put("basic_croco", ImageIO.read(Resource.class.getResource("/resources/crocodiles/basic_croco.png")));
            gameResources.put("speedy_croco",ImageIO.read(Resource.class.getResource("/resources/crocodiles/speedy_croco.png")));
            gameResources.put("mid_croco", ImageIO.read(Resource.class.getResource("/resources/crocodiles/mid_croco.png")));
            gameResources.put("fat_croco", ImageIO.read(Resource.class.getResource("/resources/crocodiles/fat_croco.png")));
            gameResources.put("arnab",ImageIO.read(Resource.class.getResource("/resources/crocodiles/arnab.png")));

            //Towers
            gameResources.put("basic_tower", ImageIO.read(Resource.class.getResource("/resources/towers/mortar.png")));
            gameResources.put("sniper_tower",ImageIO.read(Resource.class.getResource("/resources/towers/sniper.png")));
            gameResources.put("duck_tower", ImageIO.read(Resource.class.getResource("/resources/towers/duck.png")));
            gameResources.put("tank_tower", ImageIO.read(Resource.class.getResource("/resources/towers/tank.png")));
            gameResources.put("strong_duck",ImageIO.read(Resource.class.getResource("/resources/towers/strong_duck.png")));
            gameResources.put("coca_farm",ImageIO.read(Resource.class.getResource("/resources/towers/strong_duck.png")));

            //projectiles 
            gameResources.put("splitter_proj",ImageIO.read(Resource.class.getResource("/resources/projectiles/default_proj.png")));    
            gameResources.put("default_proj",ImageIO.read(Resource.class.getResource("/resources/projectiles/default_proj.png")));
            gameResources.put("laser_proj",ImageIO.read(Resource.class.getResource("/resources/projectiles/laser_proj.png")));


            // -- Sounds -- 
            gameSounds.put("kill_sound", loadClipPool("/resources/sounds/kill_sound.wav", 6));
            gameSounds.put("take_damage",loadClipPool("/resources/sounds/take_damage.wav", 10));
            gameSounds.put("laser_sound",loadClipPool("/resources/sounds/laser_sound.wav", 3));
            gameSounds.put("darth_vader",loadClipPool("/resources/sounds/darth_vader.wav", 1));
            gameSounds.put("money_sound",loadClipPool("/resources/sounds/money_sound.wav", 3));
            gameSounds.put("place_sound",loadClipPool("/resources/sounds/place_sound.wav", 2));


            System.out.println("all game resouces are loaded!");
        } catch (Exception e ) {
            System.out.println("Error loading game resouces: "+ e.getMessage());
        }
    }

    // loads in a sound poolSize times into the hashmap
    //poolsize is an indicator how many times the same sound can be played "simultaneously"

     /**
     * Loads multiple instances of a sound effect to create a playback pool.
     *
     * <p>This allows the same sound to be played multiple times simultaneously
     * by providing multiple Clip instances that can be started independently.
     *
     * @param filePath Path to the sound file within the resources directory
     * @param poolSize Number of Clip instances to create for overlapping playback
     * @return An array of Clip objects ready for playback
     * @throws Exception If the sound file cannot be loaded or audio system initialization fails
     */
    private static Clip[] loadClipPool(String filePath, int poolSize) throws Exception {
        Clip[] pool = new Clip[poolSize];
        
        // NEU: Wir suchen die Audio-Datei direkt im Classpath der .jar Datei
        URL soundURL = Resource.class.getResource(filePath);
        
        // if sound is not found 
        if (soundURL == null) {
            System.out.println("Error: Sound file not found: " + filePath);
            return pool; 
        }

        for (int i = 0; i < poolSize; i++) {
            AudioInputStream stream = AudioSystem.getAudioInputStream(soundURL);        
            Clip clip = AudioSystem.getClip();  
            clip.open(stream);  
            pool[i] = clip; // puts clip into the the pool
        }
        return pool;
    }

    // Getter for any game resouces via key 
    /**
     * Retrieves a game resource by its unique identifier.
     *
     * @param resourceID The identifier key for the requested resource
     * @return The BufferedImage associated with the resourceID, or null if not found
     * @see #gameResources
     */
    public static BufferedImage getResource(String resouceID){      
        return gameResources.get(resouceID);
    }
    
    // plays sounds even overlapping possible
    /**
     * Plays a sound effect identified by its key.
     *
     * <p>This method uses the sound pool to play the requested sound effect,
     * allowing for multiple simultaneous playbacks if the pool contains
     * multiple instances of the requested sound.
     *
     * <p>If no available Clip is found in the pool, the sound won't play.
     *
     * @param key The identifier for the sound effect to play
     * @see #gameSounds
     * @see #loadClipPool(String, int)
     */
    public static void playSound(String key) {
        Clip[] pool = gameSounds.get(key); 
        
        if (pool != null) {
            for (Clip clip : pool) {
                //selects Clip that is not playing rn 
                if (clip != null && !clip.isRunning()) {
                    clip.setFramePosition(0);
                    clip.start();
                    break;
                }
            }
        } else {
            System.out.println("sound-key not found: " + key);
        }
    }

    //return status of loaded resources 
    @Override
    public String toString() {
        return "Resource {ImagesLoaded: " + gameResources.size() + 
             ", SoundsLoaded: " + gameSounds.size() + "}";
    }
}