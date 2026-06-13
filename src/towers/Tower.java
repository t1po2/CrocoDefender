package towers;

import java.awt.Point;
import java.util.ArrayList;
import Crocodiles.Croco;
import projectiles.Projectile;
import projectiles.Default_proj;
import projectiles.Laser_proj;
import projectiles.Splitter_proj;

/**
 * Abstract base class for all tower types in the tower defense game.
 *
 * <p>This class provides the foundation for tower behavior including:
 * <ul>
 *   <li>Target acquisition and attack logic</li>
 *   <li>Upgrade system for enhancing tower capabilities</li>
 *   <li>Projectile creation for different attack types</li>
 *   <li>Value tracking for buying, upgrading, and selling towers</li>
 * </ul>
 *
 * <p>Each tower has four distinct upgrade paths that enhance different aspects
 * of the tower's functionality. Concrete subclasses define the specific effects
 * of these upgrades through the abstract applyUpgrade methods.
 *
 * @author Nguyen Viet Hung
 * @see Upgrades
 * @see Croco
 * @see Projectile
 */

public abstract class Tower implements Upgrades {

    /** The attack range of the tower in pixels */
    protected int range;

    /** The base damage dealt by the tower's projectiles */
    protected int damage;

    /** The delay between shots in milliseconds */
    protected int fireRate;

    /** The initial cost to build this tower */
    protected int cost;

    /** The type identifier for this tower */
    protected String towerType;

    /** The resource key for the projectile this tower fires */
    protected String projectileKey;

    /** The current value of this tower for selling purposes */
    protected double towerValue;

    /** Timestamp of the last shot in milliseconds */
    protected long lastShotTime = 0;

    /** Costs for each upgrade path */
    protected int upgrade1Cost, upgrade2Cost, upgrade3Cost, upgrade4Cost;

    /** Descriptions for each upgrade path displayed in the UI */
    protected String upgrade1Desc, upgrade2Desc, upgrade3Desc, upgrade4Desc;

    /** Counter for number of upgrades purchased on each path */
    protected int lock1=0, lock2=0, lock3=0, lock4=0;

    /**
     * Constructs a new Tower with the specified properties.
     *
     * @param range The attack range of the tower
     * @param damage The base damage of the tower's projectiles
     * @param rpm The rate of fire in rounds per minute
     * @param cost The initial build cost of the tower
     * @param projectileKey The resource identifier for the projectile this tower fires
     */

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
     /**
     * Updates the tower's shooting logic and creates projectiles if ready to fire.
     *
     * <p>This method:
     * <ul>
     *   <li>Checks if enough time has passed since the last shot</li>
     *   <li>Finds the closest valid target within range</li>
     *   <li>Creates the appropriate projectile type based on the tower's configuration</li>
     * </ul>
     *
     * @param currentTime The current game time in milliseconds
     * @param pos The position of the tower on the game map
     * @param crocos The list of all active crocodiles to target
     * @param projectiles The list of all active projectiles to add new projectiles to
     */
    public void updateShooting(long currentTime, Point pos, ArrayList<Croco> crocos, ArrayList<Projectile> projectiles){
        if (currentTime - lastShotTime >= this.fireRate){
        
        Croco closestTarget = null;
        double shortestDist = this.range; // sets minimum dist to max range 

        // Finds a croco 
        for (Croco target : crocos){
            double dist = pos.distance(target.getX(), target.getY()); // calcs distance between tower pos and target X/Y

            // if croco is in range and has shorter range than shortestDist 
            if (dist <= shortestDist){
                shortestDist = dist;      // updates closest distance 
                closestTarget = target;   //marks closes target
            }
        }

        // 2. Schieße auf den gefundenen Gegner (falls einer in Reichweite war)
        if (closestTarget != null) {

                    switch (this.projectileKey) {
                        case "default_proj":
                            projectiles.add(new Default_proj(pos.x, pos.y, closestTarget, this.damage, this.projectileKey, crocos, projectiles));
                            break;
                        case "splitter_proj":
                            projectiles.add(new Splitter_proj(pos.x, pos.y, closestTarget, this.damage, this.projectileKey, crocos, projectiles));
                            break;
                        case "laser_proj":
                            projectiles.add(new Laser_proj(pos.x, pos.y, closestTarget, this.damage, this.projectileKey, crocos, projectiles));
                            break;
                    }
                    this.lastShotTime = currentTime;
                }
            }
        }
 

    /**
     * Applies upgrade 1 and updates tower value.
     *
     * @return The cost of this upgrade
     */
    @Override
    public final int upgrade1() {
        applyUpgrade1();
        this.addValue((int)Math.round(upgrade1Cost*0.7));
        lock1++;
        return upgrade1Cost;
    }
    
   /**
     * Applies upgrade 2 and updates tower value.
     *
     * @return The cost of this upgrade
     */
    @Override
    public final int upgrade2() {
        applyUpgrade2();
        this.addValue((int)Math.round(upgrade2Cost*0.7));
        lock2++;
        return upgrade2Cost;
    }
    
    /**
     * Applies upgrade 3 and updates tower value.
     *
     * @return The cost of this upgrade
     */
    @Override
    public final int upgrade3() {
        applyUpgrade3();
        this.addValue((int)Math.round(upgrade3Cost*0.7));
        lock3++;
        return upgrade3Cost;
    }

    /**
     * Applies upgrade 4 and updates tower value.
     *
     * @return The cost of this upgrade
     */
    @Override
    public final int upgrade4() {
        applyUpgrade4();
        this.addValue((int)Math.round(upgrade4Cost*0.7));
        lock4++;
        return upgrade4Cost;
    }

    /**
     * Applies the effects of upgrade 1 to this tower.
     *
     * <p>Concrete tower classes must implement this method to define
     * the specific effects of their first upgrade path.
     */
    @Override
    public abstract void applyUpgrade1();

    /**
     * Applies the effects of upgrade 2 to this tower.
     *
     * <p>Concrete tower classes must implement this method to define
     * the specific effects of their second upgrade path.
     */
    @Override
    public abstract void applyUpgrade2();

    /**
     * Applies the effects of upgrade 3 to this tower.
     *
     * <p>Concrete tower classes must implement this method to define
     * the specific effects of their third upgrade path.
     */
    @Override
    public abstract void applyUpgrade3();

    /**
     * Applies the effects of upgrade 4 to this tower.
     *
     * <p>Concrete tower classes must implement this method to define
     * the specific effects of their fourth upgrade path.
     */
    @Override
    public abstract void applyUpgrade4();



    
    // Getter methods
    public int getRange() { return range; }
    public int getDamage() { return damage; }
    public int getFireRate() { return fireRate; }
    public int getCost() { return cost; }
    public String getTowerType() { return towerType; }
    public int getTowerValue() { return (int) towerValue; }
    public int getUpgrade1Cost() { return upgrade1Cost; }
    public int getUpgrade2Cost() { return upgrade2Cost; }
    public int getUpgrade3Cost() { return upgrade3Cost; }
    public int getUpgrade4Cost() { return upgrade4Cost; }
    public String getUpgrade1Desc() { return upgrade1Desc; }
    public String getUpgrade2Desc() { return upgrade2Desc; }
    public String getUpgrade3Desc() { return upgrade3Desc; }
    public String getUpgrade4Desc() { return upgrade4Desc; }
    public String getProjectileKey() { return projectileKey; }

    /**
     * Checks if upgrade path 1 has reached maximum level.
     *
     * @return true if upgrade path 1 is at max level (4 upgrades), false otherwise
     */
    public boolean locked1() {
        return lock1 >= 4;
    }

    /**
     * Checks if upgrade path 2 has reached maximum level.
     *
     * @return true if upgrade path 2 is at max level (4 upgrades), false otherwise
     */
    public boolean locked2() {
        return lock2 >= 4;
    }

    /**
     * Checks if upgrade path 3 has reached maximum level.
     *
     * @return true if upgrade path 3 is at max level (4 upgrades), false otherwise
     */
    public boolean locked3() {
        return lock3 >= 4;
    }

    /**
     * Checks if upgrade path 4 has reached maximum level.
     *
     * @return true if upgrade path 4 is at max level (4 upgrades), false otherwise
     */
    public boolean locked4() {
        return lock4 >= 4;
    }

    public int getLock1() { return lock1; }
    public int getLock2() { return lock2; }
    public int getLock3() { return lock3; }
    public int getLock4() { return lock4; }

    /**
     * Adds value to this tower (used when upgrading).
     *
     * @param value The amount to add to the tower's value
     */
    public void addValue(int value) {
        this.towerValue = this.towerValue + value;
    }

    /**
     * Updates the cost of upgrade 1 based on the current cost and pricing factor.
     *
     * @param factor The pricing factor for determining the new cost
     */
    public void setUpgrade1Cost(double factor) {
        this.upgrade1Cost = (int)Math.round(upgrade1Cost * ((factor/10)+1));
    }

    /**
     * Updates the cost of upgrade 2 based on the current cost and pricing factor.
     *
     * @param factor The pricing factor for determining the new cost
     */
    public void setUpgrade2Cost(double factor) {
        this.upgrade2Cost = (int)Math.round(upgrade2Cost * ((factor/10)+1));
    }

    /**
     * Updates the cost of upgrade 3 based on the current cost and pricing factor.
     *
     * @param factor The pricing factor for determining the new cost
     */
    public void setUpgrade3Cost(double factor) {
        this.upgrade3Cost = (int)Math.round(upgrade3Cost * ((factor/10)+1));
    }

    /**
     * Updates the cost of upgrade 4 based on the current cost and pricing factor.
     *
     * @param factor The pricing factor for determining the new cost
     */
    public void setUpgrade4Cost(double factor) {
        this.upgrade4Cost = (int)Math.round(upgrade4Cost * ((factor/10)+1));
    }

    /**
     * Returns a string representation of this tower for debugging purposes.
     *
     * @return A string containing key statistics and properties of this tower
     */
    @Override
    public String toString() {
        return "Tower [range=" + range + ", damage=" + damage + ", fireRate=" + fireRate + ", cost=" + cost
                + ", towerType=" + towerType + ", projectileKey=" + projectileKey + ", towerValue=" + towerValue
                + ", upgrade1Cost=" + upgrade1Cost + ", upgrade2Cost=" + upgrade2Cost + ", upgrade3Cost=" + upgrade3Cost
                + ", upgrade4Cost=" + upgrade4Cost + ", upgrade1Desc=" + upgrade1Desc + ", upgrade2Desc=" + upgrade2Desc
                + ", upgrade3Desc=" + upgrade3Desc + ", upgrade4Desc=" + upgrade4Desc + "]";
    }
}


    




    

    
    

