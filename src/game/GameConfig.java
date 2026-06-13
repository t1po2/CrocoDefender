package game;
/**
 * Central configuration class for Croco Defender game settings.
 * <p>
 * Contains all global game parameters for balancing and gameplay mechanics.
 * This class provides static access to configuration values through getter methods,
 * allowing consistent game behavior across different components while enabling
 * easy adjustments to game balance.
 * </p>
 *
 * <h3>Map Addition Guide:</h3>
 * <ol>
 *   <li>Add map image file to Resources.java</li>
 *   <li>Create new MapKey in Game panel</li>
 *   <li>Add map name to MapSelector.java</li>
 * </ol>
 *
 * <h3>Configuration Categories:</h3>
 * <ul>
 *   <li><b>Tower Upgrades:</b> Pricing and selling mechanics</li>
 *   <li><b>Wave Management:</b> Timing between rounds and enemy spawns</li>
 *   <li><b>Projectile Effects:</b> Movement speed and area-of-effect properties</li>
 * </ul>
 *
 * @author Nguyen Viet Hung
 * @see Resources
 * @see game.MainMenu
 * @see game.main_menu.MapSelector
 */

public class GameConfig {


    // -- Tower Upgrades -- 
    private static int sellFactor=7;        //tower sell factor in %
    private static int upgradeFactor=3;     // price in crease after an upgrade in %
    private static int slowEffect=1000;     //1000 = 1 sec


    // -- Wave and Round Delay --
    private static int firstRoundStartDelay = 200;         //how many frames wait brfore first Round starts
    private static int nextRoundDelay = 240;        // pause inbetween waves in frames
    private static int spawnDelay = 10;         //spawn delay between each croco in frames

    // -- Projectile AOE Radius --
    private static int projectileSpeed= 10;
    private static int splashRadius = 25; //in px
    private static int splashDamage = 1;    





    






































    public static int getSellFactor(){
        return sellFactor;
    }

    public static int getUpgradeFactor(){
        return upgradeFactor;
    }

    public static int getFirstRoundStartDelay(){
        return firstRoundStartDelay/-1;
    }

    public static int getNextRoundDelay(){
        return nextRoundDelay/-1;
    }
    public static int getSplashRadius(){
        return splashRadius;
    }
    public static int getSplashDamage(){
        return splashDamage;
    }
    public static int getSpawnDelay(){
        return spawnDelay;
    }
    public static int getSlowEffectTime(){
        return slowEffect;
    }
    public static int getProjectileSpeed(){
        return projectileSpeed;
    }
}
