package game;

import javax.swing.SwingUtilities;

public class Launcher {


    public static void main(String[] args){

        Resource.loadGameResources();
        //initiate GameLoop inside a thread
        GameMechanic gameMechanic = new GameMechanic();
        GameLoop gameLoop = new GameLoop(gameMechanic);     
        Thread thread = new Thread(gameLoop);
        thread.start();

            // Show the window - this will contain the GamePanel
        SwingUtilities.invokeLater(() -> {
            new Window(gameMechanic);
        });
        
    }
    
}
