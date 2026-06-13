package Crocodiles;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import game.GameConfig;
import game.Resource;

/**
 * Abstract base class representing crocodile enemies in the game.
 * <p>
 * This class handles movement along a predefined path, health management,
 * and status effects like slowing. Crocodiles follow waypoints until they
 * reach the end of the path or are defeated.
 * </p>
 *
 * <p>
 * The class provides basic functionality for:
 * <ul>
 *   <li>Smooth movement along waypoints</li>
 *   <li>Health reduction when hit by towers</li>
 *   <li>Movement speed alteration effects</li>
 *   <li>Reward calculation when defeated</li>
 * </ul>
 * </p>
 *
 * <p>
 * Subclasses should implement specific crocodile types with unique textures
 * and potentially special behaviors.
 * </p>
 */

public abstract class Croco  {


    //variables for movement
    private double x,y;     //doubles so mevement is super smooth
    protected double speed;
    protected int targetWaypoint = 0;   // Index of  next Waypoint in the waypoint List 
    protected boolean reachedEnd = false;
    protected int killReward;
    protected BufferedImage texture;
    
    // Slow or stun mechanics
    protected boolean isSlowed = false;
    protected double originalSpeed = -1;
    protected long effectTime = 0;
    
    // some Game stats
    protected String textureKey;
    protected int crocoHp;


    /**
     * Constructs a new Crocodile with specified path,speed,texture,health points and reward on kill.
     * 
     * @param path A list of waypoints the crocodile will follow 
     * @param speed Movement speed of the Crocodile
     * @param textureKey A String used as key to retrieve the textures from resource manager 
     * @param crocoHp Healthpoints of Crocodile
     * @param killReward Upon defeat of Crocodile, a reward is added to tthe player's gold 
     */

    public Croco(ArrayList<Point> path,int speed, String textureKey,int crocoHp,int killRward){

        this.speed= speed;
        
        this.textureKey = textureKey;
        this.crocoHp = crocoHp;
        this.killReward = killRward;
        this.texture = Resource.getResource(textureKey);


        //set sapwn point of tcrcos
        if (path !=null && !path.isEmpty()){
            this.x = path.get(0).x;
            this.y = path.get(0).y;
        }
    }




    /**
     * Method to move crocodile through given waypoints.
     * <p>
     * Checks for duration of an apllied effect and moves the crocodile along the waypoints.
     * </p> 
     */
    public void move(ArrayList<Point> path) {

        // this applies only when the Tower is slowing the crocos
        if (isSlowed && System.currentTimeMillis() >= effectTime) {
        this.speed = originalSpeed; // resets speed 
        this.isSlowed = false;      // resets effect status
    }
        // 1. Check if we already finished the path
        if (targetWaypoint >= path.size()) {
            reachedEnd = true;
            return;
        }

        // 2. Find out where we are trying to go
        Point target = path.get(targetWaypoint);
        
        // 3. Calculate distance to that target
        double dx = target.x - x;
        double dy = target.y - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // 4. Move!
        if (distance <= speed) {
            // We are so close that taking a normal step would overshoot it. 
            // So we snap exactly to the point and aim for the next one!
            x = target.x;
            y = target.y;
            targetWaypoint++; 
        } else {
            // Take a normal step towards the target based on our speed
            x += (dx / distance) * speed;
            y += (dy / distance) * speed;
        }
    }

    // Getters for Drawing (GamePanel needs these to read the private x and y)
    public double getX() { 
        return x; 
    }
    
    public double getY() { 
        return y; 
    }



    /**
     * Returns the type identifier of the crocodile.
     * @return The texture key which also serves as type identifier.
     */
    //Getters
    public String getCrocoType(){
        return textureKey;
    }


    /**
     * Returns the texture of the  crocodile
     * @return Returns a buffered image of the crocodile.
     */
    public BufferedImage getTexture(){
        return this.texture;
    }


    /**
     * This method checks if the Croco has readched the end of the map 
     * @return  true or false statement wheather it reached last waypoint (map end).
     */
    public boolean hasReachedEnd(){
        return reachedEnd;
    }

    /**
     * This method returns the damage inflicted to the player, if it reaches the end.
     *  <i> Note: There are two methods which return the same variable, thus created only to differientiate easier (qol) </i>
     * @return  The received damage upon reaching map end.
     */
    public int getDmg(){
        return this.crocoHp;
    }

    /**
     * This method return current croco health points.
     * @return  Current crocodile's health point
     */
    public int getHealth(){
        return this.crocoHp;
    }



    /**
     * This method substitues health points from current crocodile.
     */
    public void reduceHealth(int damage){
        this.crocoHp = this.crocoHp - damage;
    }


    /**
     * This method returns the gold upon killing the crocodile.
     * @return  Returns Kill Reward of Crocodile. 
     */
    public int killRward(){
        return this.killReward;
    }

    /**
     * This method checks current crocodile wheather it is slowed already or not. 
     * Apllies the slow effect only on untoched crocodiles.
     * @param value the speed of the croco should have while slowed.  
     */
    //slow effect 
    public void reduceSpeed(double value) {
    //onlz tagets non effected crocos
    if (!isSlowed) {
        this.originalSpeed = this.speed; 
    }
    
    this.speed = value;
    this.isSlowed = true;
    // slow time effect
    this.effectTime = System.currentTimeMillis() + GameConfig.getSlowEffectTime(); 
    }


    // Croco type, hp and location
    @Override
    public String toString() {
        return "Croco {Type: " + getCrocoType() + 
              ", HP: " + getHealth() + 
              ", Pos: (" + getX() + "/" + getY() + ")}";
}
}
