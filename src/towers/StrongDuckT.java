package towers;

import java.awt.Point;
import java.util.ArrayList;
import Crocodiles.Croco;
import projectiles.Projectile;


public class StrongDuckT extends Tower{

    private double slowEffectValue = 1; // set the speed of corocso in range 


    public StrongDuckT(){
        super(100, 0, 24, 2600,"default_proj");
        this.upgrade1Cost = 1200;
        this.upgrade2Cost = 2875;
        this.upgrade3Cost = 2200;
        this.upgrade4Cost = 5000;

        this.upgrade1Desc = "-PERFORMATIVE MODE- +5% range boost";
        this.upgrade2Desc = "-STEROIDS INJECTION- +1 damage (2!)";
        this.upgrade3Desc = "-PHONK ON- +25% fire rate";
        this.upgrade4Desc = "10000KG BENCH PRESS ULTIMATE AURA FARM (!)";
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
        this.range = (int)Math.round(range*1.05);
    }

    @Override
    public void applyUpgrade2() {  
        this.damage=this.damage + 1;
        if (lock2==2){
            lock2=4;
        }
    }

    @Override
    public void applyUpgrade3() {
        this.fireRate = (int)Math.round(fireRate/1.25); 
    }

    @Override
    public void applyUpgrade4() {
        this.slowEffectValue=0.5;
        lock4=4;
    }
    
}
