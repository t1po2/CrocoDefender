package game;

import javax.swing.SwingUtilities;

public class Launcher {

    private static String mapName;   

    public static void main(String[] args){

        mapName = "swamp_map";  // insert map name here

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
