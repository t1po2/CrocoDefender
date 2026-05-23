package towers;

import java.awt.Point;
import java.util.ArrayList;
import Crocodiles.Croco;
import projectiles.Projectile;
import projectiles.Default_proj;
import projectiles.Splitter_proj;



public abstract class Tower implements Upgrades {

    protected int range;
    protected int damage;
    protected int fireRate; // milliseconds between shots
    protected int cost;
    protected String towerType;
    protected String projectileKey;
    protected double towerValue;

    protected long lastShotTime = 0;

    // induviduell uprade cost

    protected int upgrade1Cost;
    protected int upgrade2Cost;
    protected int upgrade3Cost;
    protected int upgrade4Cost;


    //Uograde desc for Buttons
    protected String upgrade1Desc;
    protected String upgrade2Desc;
    protected String upgrade3Desc;
    protected String upgrade4Desc;
    // upgrade lock 
    protected int lock1=0;
    protected int lock2=0;
    protected int lock3=0;
    protected int lock4=0;



    public Tower(int range,int damage, int rpm, int cost,String projectileKey){
        this.range = range;
        this.damage = damage;
        this.cost = cost;
        this.towerValue = cost;       //rounds the number sell price is at 70%
        this.projectileKey = projectileKey;

        //unfortunetly there is only current time in milliseconds 
        //rpm converter

        if (rpm > 0){
            this.fireRate = 60000 / rpm;
        } else {
            System.out.println("Error: rpm of Tower Class is less or equal to 0");
        }
    }


    // -- Tower shooting logic --
    public void updateShooting(long currentTime, Point pos, ArrayList<Croco> crocos, ArrayList<Projectile> projectiles){

        if (currentTime - lastShotTime >= this.fireRate){
            
            for (Croco target : crocos){

                double dist = pos.distance(target.getX(),target.getY());        // calcs distance between tower pos and target X/Y

                if (dist <= this.range){

                    switch (this.projectileKey) {
                        case "default_proj":
                            projectiles.add(new Default_proj(pos.x, pos.y, target, this.damage, this.projectileKey, crocos, projectiles));
                            this.lastShotTime = currentTime;
                            break;
                        case "splitter_proj":
                            projectiles.add(new Splitter_proj(pos.x, pos.y, target, this.damage, this.projectileKey, crocos, projectiles));
                            this.lastShotTime = currentTime;
                            break;
                    }
                    break;
                }
            }
        }
    }













    @Override
    public final int upgrade1(){
        applyUpgrade1();
        this.addValue((int)Math.round(upgrade1Cost*0.7));
        lock1++;
        return upgrade1Cost;
    }
    @Override
    public final int upgrade2(){
        applyUpgrade2();
        this.addValue((int)Math.round(upgrade2Cost*0.7));
        lock2++;
        return upgrade2Cost;
    }
    @Override
    public final int upgrade3(){
        applyUpgrade3();
        this.addValue((int)Math.round(upgrade3Cost*0.7));
        lock3++;
        return upgrade3Cost;
    }
    @Override
    public final int upgrade4(){
        applyUpgrade4();
        this.addValue((int)Math.round(upgrade4Cost*0.7));
        lock4++;
        return upgrade4Cost;
    }


    @Override
    public abstract void applyUpgrade1();
    @Override
    public abstract void applyUpgrade2();
    @Override
    public abstract void applyUpgrade3();
    @Override
    public abstract void applyUpgrade4();





    //getters

    public int getRange() {
        return range;
    }

    public int getDamage() {
        return damage;
    }

    public int getFireRate() {
        return fireRate;
    }

    public int getCost() {
        return cost;
    }

    public String getTowerType() {
        return towerType;
    }

    public int getTowerValue(){
        return (int) towerValue;
    }

    public int getUpgrade1Cost() {
        return upgrade1Cost;
    }

    public int getUpgrade2Cost() {
        return upgrade2Cost;
    }

    public int getUpgrade3Cost() {
        return upgrade3Cost;
    }

    public int getUpgrade4Cost() {
        return upgrade4Cost;
    }

    public String getUpgrade1Desc() {
        return upgrade1Desc;
    }

    public String getUpgrade2Desc() {
        return upgrade2Desc;
    }

    public String getUpgrade3Desc() {
        return upgrade3Desc;
    }

    public String getUpgrade4Desc() {
        return upgrade4Desc;
    }

    public String getProjectileKey() {
        return projectileKey;
    }


    public boolean locked1(){
        if(lock1>=4){
            return true;
        } else {
            return false;
        }
    }
    public boolean locked2(){
        if(lock2>=4){
            return true;
        } else {
            return false;
        }
    }
    public boolean locked3(){
        if(lock3>=4){
            return true;
        } else {
            return false;
        }
    }
    public boolean locked4(){
        if(lock4>=4){
            return true;
        } else {
            return false;
        }
    }
    public int getLock1() {
        return lock1;
    }

    public int getLock2() {
        return lock2;
    }

    public int getLock3() {
        return lock3;
    }

    public int getLock4() {
        return lock4;
    }

    //setters
    public void addValue(int value){
        this.towerValue = this.towerValue + value;
    }
    public void setUpgrade1Cost(double factor) {
        this.upgrade1Cost = (int)Math.round(upgrade1Cost *((factor/10)+1));
    }
    public void setUpgrade2Cost(double factor) {
        this.upgrade2Cost = (int)Math.round(upgrade2Cost *((factor/10)+1));
    }
    public void setUpgrade3Cost(double factor) {
        this.upgrade3Cost = (int)Math.round(upgrade3Cost *((factor/10)+1));
    }
    public void setUpgrade4Cost(double factor) {
        this.upgrade4Cost = (int)Math.round(upgrade4Cost *((factor/10)+1));
    }

    @Override
    public String toString() {
        return "Tower [range=" + range + ", damage=" + damage + ", fireRate=" + fireRate + ", cost=" + cost
                + ", towerType=" + towerType + ", projectileKey=" + projectileKey + ", towerValue=" + towerValue
                + ", upgrade1Cost=" + upgrade1Cost + ", upgrade2Cost=" + upgrade2Cost + ", upgrade3Cost=" + upgrade3Cost
                + ", upgrade4Cost=" + upgrade4Cost + ", upgrade1Desc=" + upgrade1Desc + ", upgrade2Desc=" + upgrade2Desc
                + ", upgrade3Desc=" + upgrade3Desc + ", upgrade4Desc=" + upgrade4Desc + "]";
    }

    




    

    
    
}
