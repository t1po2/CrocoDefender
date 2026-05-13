package game;

public class BalanceChangeControl {



    private static int sellFactor=10;        //tower sell factor in %
    private static int upgradeFactor=10;     // price in crease after an upgrade in %

    private static int firstRoundStartDelay = -600;         //how many frames wait brfore first Round starts

    public static int getSellFactor(){
        return sellFactor;
    }

    public static int getUpgradeFactor(){
        return upgradeFactor;
    }

    public static int getFirstRoundStartDelay(){
        return firstRoundStartDelay;
    }

}
