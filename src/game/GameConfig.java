package game;

/**
 * @author Nguyen Viet Hung 
 * 
 * <b> Global game configs </b>
 * For easier balance changes <br>
 * 
 *  =How to add a Map=
 * add png to Resources.java <br>
 * add new MapKey to Game panel <br>
 * add new Map to MapSelector.java <br>
 * 
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
