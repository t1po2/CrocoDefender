package towers;
import java.awt.Point;
import java.util.ArrayList;

import Crocodiles.Croco;
import game.Resource;
import player.PlayerStats;
import projectiles.Projectile;
import projectiles.TextProjectile;

public class CocaFarm extends Tower {

    private int goldDrop=30;
    private int dropCount=0;
    private int maxDrop=8;

    public CocaFarm(){
        super(0,0,60,3200,null);

        this.upgrade1Cost = 1600;
        this.upgrade2Cost = 2000;
        this.upgrade3Cost = 3800;
        this.upgrade4Cost = 0;

        this.upgrade1Desc = "Increase Coca production 15%";
        this.upgrade2Desc = "Increase max coca drop";
        this.upgrade3Desc = "Cut the coca with Dior Savage +40% profit";
        this.upgrade4Desc = "Nothing, absolutely nothing";
    }   

    @Override
    public void updateShooting(long currentTime, Point pos, ArrayList<Croco> crocos, ArrayList<Projectile> projectiles){

        if (this.dropCount <= this.maxDrop && currentTime - lastShotTime >= this.fireRate){
            PlayerStats.addGold(goldDrop);
            this.dropCount++;
            this.lastShotTime = currentTime;
            projectiles.add(new TextProjectile(pos.x, pos.y - 30, "+"+goldDrop));
            Resource.playSound("money_sound");
            
        } 
    }

    @Override
    public void applyUpgrade1() {
        this.fireRate = (int)Math.round(fireRate/1.15);  //20 % fireRate Buff    
        lock1++;   
    }

    @Override
    public void applyUpgrade2() {
        this.maxDrop+=2;
    }

    @Override
    public void applyUpgrade3() {
       this.goldDrop = (int)Math.round(this.goldDrop*1.4);
       if (lock3==3){
        this.upgrade3Desc = "Premium Rainbow Power Coca next upgrade will bring u fortune ;)";
       }
       if (lock3==4){
        this.goldDrop+=150;
       }
    }

    @Override
    public void applyUpgrade4() {
        if (lock4==4){
            this.upgrade4Desc = "Real power is taking care of yourself";
        }
    }
    
}
