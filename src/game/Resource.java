package game;


// this class file loads all nessecery game resources before launching saving memory and increases

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Resource {

    //String type is the resourceID
    private static final Map<String, BufferedImage> gameResources = new HashMap<>();

    public static void loadGameResources(){

        // laoding all nessecary game resources 
        try {
            //map 
            gameResources.put("swamp_map", ImageIO.read(new File("src\\resources\\maps\\swamp_map.png")));
            
            //Croco textures
            gameResources.put("basic_croco", ImageIO.read(new File("src\\resources\\crocodiles\\basic_croco.png")));
            gameResources.put("speedy_croco",ImageIO.read(new File("src\\resources\\crocodiles\\speedy_croco.png")));
            gameResources.put("mid_croco", ImageIO.read(new File("src\\resources\\crocodiles\\mid_croco.png")));
            gameResources.put("fat_croco", ImageIO.read(new File("src\\resources\\crocodiles\\fat_croco.png")));
            


            //Towers
            gameResources.put("basic_tower", ImageIO.read(new File("src\\resources\\towers\\mortar.png")));
            gameResources.put("sniper_tower",ImageIO.read(new File("src\\resources\\towers\\sniper.png")));

            System.out.println("all game resouces are loaded!");
        } catch (IOException e ) {
            System.out.println("Error loading game resouces: "+ e.getMessage());
        }
    }


    // Getter for any game resouces via key 
    public static BufferedImage getResource(String resouceID){      
        return gameResources.get(resouceID);
    }

    
}
