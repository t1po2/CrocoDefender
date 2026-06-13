package game;

/**
 * Core game loop implementation for Croco Defender.
 * <p>
 * This class implements a fixed timestep game loop that updates game logic
 * at a consistent rate (60 updates per second by default) while rendering
 * as fast as possible. The loop maintains separate counters for updates
 * (UPS) and frames (FPS) to monitor performance.
 * </p>
 *
 * <p>
 * The game loop uses an accumulator pattern to maintain consistent update
 * timing even when the framerate fluctuates. This ensures smooth gameplay
 * and prevents physics and game logic from running too fast or too slow.
 * </p>
 *
 * @see GameMechanic
 * @see Runnable
 * @author Nguyen Viet Hung
 */

public class GameLoop implements Runnable{

    /** Reference to the game mechanics controller */
    private GameMechanic game;

    /** Flag indicating whether the game loop is currently running */
    private boolean running; // am i running?
    /**
     * Target update frequency in seconds.
     * <p>
     * Default value is 1/60 seconds (60 updates per second).
     * </p>
     */
    private double updateRate = 1.d/60d;
     /** Time (in milliseconds) when the next performance stats will be printed */
    private long nextStatTime;
    /** Current frames per second counter */
    private int fps;
    /** Current updates per second counter */
    private int ups;
    

    /**
     * Constructs a new GameLoop instance for the specified game.
     *
     * @param game the GameMechanic instance to be driven by this loop
     */
    public GameLoop(GameMechanic game){
        this.game=game;
    }
    
    /**
     * Main game loop execution method.
     * <p>
     * Implements a fixed timestep game loop that:
     * <ul>
     *   <li>Maintains consistent update timing using an accumulator</li>
     *   <li>Calls update() at the fixed rate (60Hz by default)</li>
     *   <li>Calls render() as fast as possible</li>
     *   <li>Tracks and periodically reports performance statistics</li>
     * </ul>
     * </p>
     */
    @Override
    public void run(){
        running = true;
        double accumulator = 0;
        long currentTime, lastUpdate = System.currentTimeMillis();
        nextStatTime = System.currentTimeMillis() + 1000;

        while (running) {
            currentTime = System.currentTimeMillis();
            double lastRenderTimeInSec = (currentTime-lastUpdate)/1000d;
            accumulator += lastRenderTimeInSec;
            lastUpdate = currentTime;

            while(accumulator > updateRate){
                update();
                accumulator -= updateRate;
            }
            render();


            // toggle for testing
            if (toggleStats == true){
                printStats();
            }
        }
    }

     /** Flag to enable/disable performance statistics printing */
    private boolean toggleStats = false;

     /**
     * Prints current performance statistics to the console.
     * <p>
     * Outputs the current frames per second (FPS) and updates per second (UPS)
     * once per second when enabled via {@link #toggleStats}.
     * </p>
     */
    public void printStats(){
        if(System.currentTimeMillis() > nextStatTime){
            System.out.println(String.format("FPS: %d UPS: %d",fps,ups));
            fps = 0;
            ups = 0;
            nextStatTime = System.currentTimeMillis() + 1000;
        }
    }
    /**
     * Updates the game state by calling the game's update method.
     * <p>
     * Also increments the updates-per-second counter.
     * </p>
     */
    private void update(){
        game.update();
        ups++;
    }
      /**
     * Renders the current game frame by calling the game's render method.
     * <p>
     * Also increments the frames-per-second counter.
     * </p>
     */
    private void render(){
        game.render();
        fps++;
    }

    @Override
    public String toString() {
        return "GameLoop {" +
                "running=" + running + 
                ", FPS=" + fps + 
                ", UPS=" + ups + 
                ", targetUpdateRate=" + (1.0 / updateRate) + "Hz" +
                "}";
    }
    

}
