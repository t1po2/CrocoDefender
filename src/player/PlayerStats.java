package player;

public class PlayerStats {

    private int gold;
    private int playerHP = 150;
    


    public PlayerStats(){
        gold = 101000;
    }



    //Getters 
    public int getGold(){
        return gold;
    }

    public int getPlayerHp(){
        return playerHP;
    }

    //Setters
    public void addGold(int value){
        gold = gold + value;
    }
    public void takeDamage(int dmg){
        playerHP = playerHP - dmg;
    }

    public void removeGold(int value){
        this.gold = this.gold -value;
    }
}
