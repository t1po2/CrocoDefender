package main.java.com.crocodefender;

import javax.swing.SwingUtilities;

import main.java.com.crocodefender.core.GameLoop;
import main.java.com.crocodefender.core.GameMechanic;
import main.java.com.crocodefender.core.Window;
import main.java.com.crocodefender.ui.MainMenu;
import main.java.com.crocodefender.util.Resource;


/**
 * Starting point of Game <br>
 * before executing a thread loads in all necessary game files  
 * Core Game Instances are initialized and run in a thread 
 * Game UI is displayed inside a window class 
 * @author Nguyen Viet Hung 
 */



public class Launcher {
 

    public static void main(String[] args){

        
        SwingUtilities.invokeLater(() -> {              // Launches Main Menu from there u can start game
           new MainMenu();
        });
        
    }

    /**
     * Initializes game resources and starts the main game loop in a new thread.
     * It also launches the main game window.
     *
     * @param mapName the name of the map to be loaded
     */

    public static void startGame(String mapName){
        mapName = MainMenu.getMap();  // insert map name here

        Resource.loadGameResources();
        //initiate GameLoop inside a thread
        GameMechanic gameMechanic = new GameMechanic(mapName);
        GameLoop gameLoop = new GameLoop(gameMechanic);     
        Thread thread = new Thread(gameLoop);
        thread.start();

            // Show the window - this will contain the GamePanel
        SwingUtilities.invokeLater(() -> {
            new Window(gameMechanic);
        });
        
    }

    @Override
    public String toString() {
        return "Launcher []";
    }
    
}
