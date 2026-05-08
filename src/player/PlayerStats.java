package player;

public class PlayerStats {

    private int gold;
    private final int playerHP = 150;
    


    public PlayerStats(){
        this.gold = 2500;
    }



    //Getters 
    public int getGold(){
        return gold;
    }

    public int getPlayerHp(){
        return playerHP;
    }
}
