package player;

public class PlayerStats {

    private int gold;
    private int playerHP = 150;
    


    public PlayerStats(){
        this.gold = 100000;
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
        this.gold = this.gold + value;
    }
    public void takeDamage(int dmg){
        this.playerHP = this.playerHP - dmg;
    }

    public void removeGold(int value){
        this.gold = this.gold -value;
    }
}
