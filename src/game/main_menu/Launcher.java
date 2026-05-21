package game.main_menu;

import javax.swing.SwingUtilities;
import game.GameLoop;
import game.GameMechanic;
import game.Resource;
import game.Window;


/**
 * @author Nguyen Viet Hung 
 * 
 * Starting point of Game, before executing a thread loads in all necessery game files  
 * Core Game Instances are initilised and run in a thread 
 * Game UI is displayed inside a window class 
 * 
 */



public class Launcher {
 

    public static void main(String[] args){

        SwingUtilities.invokeLater(() -> {              // Launches Main Menu from there u can start game
           new MainMenu();
        });

    }

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
    
}
