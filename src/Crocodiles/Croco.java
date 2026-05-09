package Crocodiles;

import java.awt.Point;
import java.util.ArrayList;

import game.Resource;


public abstract class Croco  {


    //variables for movement
    private double x,y;     //doubles so mevement is super smooth
    protected double speed;
    protected int targetWaypoint = 0;   // Index of  next Waypoint in the waypoint List 
    protected boolean reachedEnd = false;



    // some Game stats
    protected int dmg;
    protected String crocoType;
    protected int crocoHp = 1;

    public Croco(ArrayList<Point> path){

        //set sapwn point of tcrcos
        if (path !=null && !path.isEmpty()){
            this.x = path.get(0).x;
            this.y = path.get(0).y;
        }
    }




    // THE BRAIN: How the crocodile moves along the map's waypoints
    public void move(ArrayList<Point> path) {
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
        return this.crocoType;
    }

    public boolean hasReachedEnd(){
        return reachedEnd;
    }

    public int getDmg(){
        return this.dmg;
    }

    public int getHealth(){
        return this.crocoHp;
    }

    public void reduceHealth(int damage){
        this.crocoHp = this.crocoHp - damage;
    }
    
}
