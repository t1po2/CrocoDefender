package game;

// this class file loads all nessecery game resources before launching saving memory and increases

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Resource {

    //String type is the resourceID
    private static final Map<String, BufferedImage> gameResources = new HashMap<>();
    
    // Clip[] for overlapping sounds
    private static final Map<String, Clip[]> gameSounds = new HashMap<>();

    public static void loadGameResources(){

        // laoding all nessecary game resources 
        try {
            //map 
            gameResources.put("swamp_map", ImageIO.read(new File("src/resources/maps/swamp_map.png")));
            gameResources.put("mt_croco", ImageIO.read(new File("src/resources/maps/mt_croco.png")));
            
            //Croco textures
            gameResources.put("basic_croco", ImageIO.read(new File("src/resources/crocodiles/basic_croco.png")));
            gameResources.put("speedy_croco",ImageIO.read(new File("src/resources/crocodiles/speedy_croco.png")));
            gameResources.put("mid_croco", ImageIO.read(new File("src/resources/crocodiles/mid_croco.png")));
            gameResources.put("fat_croco", ImageIO.read(new File("src/resources/crocodiles/fat_croco.png")));
            gameResources.put("arnab",ImageIO.read(new File("src/resources/crocodiles/arnab.png")));

            //Towers
            gameResources.put("basic_tower", ImageIO.read(new File("src/resources/towers/mortar.png")));
            gameResources.put("sniper_tower",ImageIO.read(new File("src/resources/towers/sniper.png")));
            gameResources.put("duck_tower", ImageIO.read(new File("src/resources/towers/duck.png")));
            gameResources.put("tank_tower", ImageIO.read(new File("src/resources/towers/tank.png")));
            gameResources.put("strong_duck",ImageIO.read(new File("src/resources/towers/strong_duck.png")));

            //projectiles 
            gameResources.put("splitter_proj",ImageIO.read(new File("src/resources/projectiles/default_proj.png")));    // change that
            gameResources.put("default_proj",ImageIO.read(new File("src/resources/projectiles/default_proj.png")));
            gameResources.put("laser_proj",ImageIO.read(new File("src/resources/projectiles/laser_proj.png")));


            // -- Sounds -- 
            gameSounds.put("kill_sound", loadClipPool("src/resources/sounds/kill_sound.wav", 6));
            gameSounds.put("take_damage",loadClipPool("src/resources/sounds/take_damage.wav", 10));
            gameSounds.put("laser_sound",loadClipPool("src/resources/sounds/laser_sound.wav", 3));


            System.out.println("all game resouces are loaded!");
        } catch (Exception e ) {
            System.out.println("Error loading game resouces: "+ e.getMessage());
        }
    }

    // loads in a sound poolSize times into the hashmap
    //poolsize is an indicator how many times the same sound can be played "simultaneously"
    private static Clip[] loadClipPool(String filePath, int poolSize) throws Exception {
        Clip[] pool = new Clip[poolSize];
        for (int i = 0; i < poolSize; i++) {
            File file = new File(filePath); 
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);        
            Clip clip = AudioSystem.getClip();  
            clip.open(stream);  
            pool[i] = clip; // puts clip into the the pool
        }
        return pool;
    }

    // Getter for any game resouces via key 
    public static BufferedImage getResource(String resouceID){      
        return gameResources.get(resouceID);
    }
    
    // plays sounds even overlapping possible
    public static void playSound(String key) {
        Clip[] pool = gameSounds.get(key); 
        
        if (pool != null) {
            for (Clip clip : pool) {
                //selects Clip that is not playing rn 
                if (!clip.isRunning()) {
                    clip.setFramePosition(0);
                    clip.start();
                    break;
                }
            }
        } else {
            System.out.println("Sound-Key nicht gefunden: " + key);
        }
    }
}