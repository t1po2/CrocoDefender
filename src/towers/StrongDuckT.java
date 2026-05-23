package towers;

import java.awt.Point;
import java.util.ArrayList;
import Crocodiles.Croco;
import projectiles.Projectile;


public class StrongDuckT extends Tower{

    private int slowEffectValue = 1; // substracts the speed of corocso in range 


    public StrongDuckT(){
        super(100, 0, 240, 2600,"default_proj");
        this.upgrade1Cost = 800;
        this.upgrade2Cost = 1500;
        this.upgrade3Cost = 3000;
        this.upgrade4Cost = 5000;

        this.upgrade1Desc = "+2 dmg";
        this.upgrade2Desc = "+20% fireRate";
        this.upgrade3Desc = "+10% range";
        this.upgrade4Desc = "comming soon...";
    }

    // t his duck dioesnt shoot but apllies effects to crocos within its range
    @Override
    public void updateShooting(long currentTime, Point pos, ArrayList<Croco> crocos, ArrayList<Projectile> projectiles){
        if (currentTime - lastShotTime >= this.fireRate){
            boolean hitSomething = false;

            for (Croco target : crocos){

                double dist = pos.distance(target.getX(),target.getY());        // calcs distance between tower pos and target X/Y

                if (dist <= this.range){
                    target.reduceSpeed(slowEffectValue);
                    this.lastShotTime = currentTime;
                }
            }
            if (hitSomething) {
                this.lastShotTime = currentTime;
            }
        }
    }

    @Override
    public void applyUpgrade1() {
    }

    @Override
    public void applyUpgrade2() {  
    }

    @Override
    public void applyUpgrade3() {
    }

    @Override
    public void applyUpgrade4() {
    }
    
}
