package game;

public class GameConfig {


    // -- Tower Upgrades -- 
    private static int sellFactor=7;        //tower sell factor in %
    private static int upgradeFactor=3;     // price in crease after an upgrade in %


    // -- Wave and Round Delay --
    private static int firstRoundStartDelay = 200;         //how many frames wait brfore first Round starts
    private static int nextRoundDelay = 240;        // pause inbetween waves

    // -- Projectile AOE Radius --
    private static int splashRadius = 50;
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
}
