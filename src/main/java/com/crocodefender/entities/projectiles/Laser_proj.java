package main.java.com.crocodefender.entities.projectiles;

import java.util.ArrayList;

import main.java.com.crocodefender.entities.enemies.Croco;
import main.java.com.crocodefender.util.Resource;

public class Laser_proj extends Projectile {

    public Laser_proj(double startX, double startY, Croco target, int damage, String projectileKey,ArrayList<Croco> allCrocos,ArrayList<Projectile> allProjectiles) {
        
        super(startX, startY, target, damage, projectileKey,allCrocos,allProjectiles);
        Resource.playSound("laser_sound");
        this.speed = 30.0; 
    }

    @Override
    protected void onHit() {
        //default behaivior 
        target.reduceHealth(damage); 
        this.active = false; 
    }
}