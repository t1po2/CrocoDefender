package player;

import game.Resource;

public class PlayerStats {

    private static int gold = 2400;
    private static int playerHP = 150;
    


    public PlayerStats(){;
    }



    //Getters 
    public int getGold(){
        return gold;
    }

    public int getPlayerHp(){
        return playerHP;
    }

    //Setters
    public static void addGold(int value){
        gold = gold + value;
    }
    public void takeDamage(int dmg){
        Resource.playSound("take_damage");
        playerHP = playerHP - dmg;
    }

    public static  void removeGold(int value){
        gold = gold - value;
    }
}
