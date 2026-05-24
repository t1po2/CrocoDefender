package projectiles;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import Crocodiles.Croco;
import game.GameConfig;
import game.Resource;

public abstract class Projectile {
    
    protected double x, y;
    protected double speed; 
    protected Croco target;
    protected int damage;
    protected boolean active = true;
    protected BufferedImage texture;
    protected ArrayList<Croco> allCrocos;
    ArrayList<Projectile> allProjectiles;

    // all projectile need This 
    public Projectile(double startX, double startY, Croco target, int damage, String projectileKey,ArrayList<Croco> allCrocos,ArrayList<Projectile> allProjectiles) {
        this.x = startX;
        this.y = startY;
        this.target = target;
        this.damage = damage;
        this.texture = Resource.getResource(projectileKey);
        this.speed = GameConfig.getProjectileSpeed(); 
        this.allCrocos = allCrocos; 
        this.allProjectiles = allProjectiles; // addding this lsit so we can simulate splitetrs etc bcs GamnePanel draws allProjectile list
    }

    // Projectile fly logic
    public void update() {
        if (target == null || target.hasReachedEnd() || target.getHealth() <= 0) {
            active = false;
            return;
        }

        double dx = target.getX() - x;          
        double dy = target.getY() - y;          
        double distance = Math.sqrt(dx * dx + dy * dy);   

        if (distance < speed) {
            // hit the target
            onHit(); 
        } else {
            x += (dx / distance) * speed;           
            y += (dy / distance) * speed;           
        }
    }

    // onHit() is insert hit effect here
    protected abstract void onHit();

    // calcs angle of adjacent and hypothenuse to rotate projectile
    public double getAngle() {
        if (target != null) {
            double dy = target.getY() - this.y;     
            double dx = target.getX() - this.x;     
            return Math.atan2(dy, dx);   
        } 
        return 0;                   
    }

    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public boolean isActive() { return active; }
    public BufferedImage getTexture() { return texture; }
    public boolean isShrapnel(){ 
        return false; 
    }   //default for all non aoe projectiles
}