package projectiles;

import java.util.ArrayList;

import Crocodiles.Croco;

public class Default_proj extends Projectile {

    public Default_proj(double startX, double startY, Croco target, int damage, String projectileKey,ArrayList<Croco> allCrocos,ArrayList<Projectile> allProjectiles) {
        super(startX, startY, target, damage, projectileKey,allCrocos,allProjectiles);
        this.speed = 7.0; 
    }

    @Override
    protected void onHit() {
        //default behaivior 
        target.reduceHealth(damage); 
        this.active = false; 
    }
}