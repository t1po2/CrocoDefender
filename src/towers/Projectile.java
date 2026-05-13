package towers;

import java.awt.image.BufferedImage;

import Crocodiles.Croco;
import game.Resource;

public class Projectile {
    private double x, y;
    private double speed = 7.0; // How fast the projectile flies
    private Croco target;
    private int damage;
    private boolean active = true;

    // projectile Pic 
    private BufferedImage texture;

    public Projectile(double startX, double startY, Croco target, int damage,String projectileKey) {
        this.x = startX;
        this.y = startY;
        this.target = target;
        this.damage = damage;
        this.texture = Resource.getResource(projectileKey);
    }

    public void update() {
        // If the crocodile died or reached the end, delete this projectile
        if (target == null || target.hasReachedEnd() || target.getHealth() <= 0) {
            active = false;
            return;
        }

        // Calculate distance to the target
        double dx = target.getX() - x;          //distance to target  at X axis
        double dy = target.getY() - y;          // distance to tarhget y axis 
        double distance = Math.sqrt(dx * dx + dy * dy);   //distance to target  

        if (distance < speed) {
            // BOOM! Hit the target!
            target.reduceHealth(damage); 
            active = false;
        } else {
            // Move closer to the target
            x += (dx / distance) * speed;           //  dx/distance return value between -1 and 1 it tells projectile where to move
            y += (dy / distance) * speed;           // now the retuened value times the speed so how many pixels per frame to move 
        }
    }
    public double getAngle(){

        if (target != null){
            double dy = target.getY() - this.y;     //dy, dx is the distance between projectile and target 
            double dx = target.getX() - this.x;     

            return Math.atan2(dy,dx);   //arctan 2 function return us the angele of the target arctan2 is smarter and know in which sector the target is 
        } else return 0;                   //angle between adjacent and hypothenuse
       
    }


    // Getters for drawing
    public double getX() { return x; }
    public double getY() { return y; }
    public boolean isActive() { return active; }

    public BufferedImage getTexture(){
        return texture;
    }


}