package Crocodiles;


import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.Resource;

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


    // path,speed,textureKey(NEEDS TO BE EXACTLY LIKE TEXTRE NAME),crcoHp,killReward
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




    // THE BRAIN: How the crocodile moves along the map's waypoints
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


    //Getters
    public String getCrocoType(){
        return textureKey;
    }
    public BufferedImage getTexture(){
        return this.texture;
    }

    public boolean hasReachedEnd(){
        return reachedEnd;
    }

    public int getDmg(){
        return this.crocoHp;
    }

    public int getHealth(){
        return this.crocoHp;
    }

    public void reduceHealth(int damage){
        this.crocoHp = this.crocoHp - damage;
    }

    public int killRward(){
        return this.killReward;
    }

    //slow effect 
    public void reduceSpeed(int value) {
    //onlz tagets non effected crocos
    if (!isSlowed) {
        this.originalSpeed = this.speed; 
    }
    
    this.speed = this.speed -value;
    this.isSlowed = true;
    
    // Aktuelle Zeit + 3000 Millisekunden (3 Sekunden) in die Zukunft rechnen
    this.effectTime = System.currentTimeMillis() + 3000; 
}
}
