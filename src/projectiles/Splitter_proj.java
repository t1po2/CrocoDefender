package projectiles;

import java.util.ArrayList;
import java.util.Random;

import Crocodiles.Croco;
import game.GameConfig;

public class Splitter_proj extends Projectile {
    private int splashDamage = GameConfig.getSplashDamage();

    public Splitter_proj(double startX, double startY, Croco target, int damage, String projectileKey,ArrayList<Croco> allCrocos, ArrayList<Projectile> allProjectiles) {
        super(startX, startY, target, damage, projectileKey, allCrocos, allProjectiles);
        
        
        this.speed = 5.0; // spliter projectiles are slower



    }
    @Override
    protected void onHit() {

        if (target != null) {
            target.reduceHealth(damage);
        }
        if (allProjectiles != null) {
            Random rand = new Random();
            for (int i = 0; i < 20 + rand.nextInt(20); i++) {
                allProjectiles.add(new Shrapnel(this.x, this.y));
            }
        }

        for (Croco c : allCrocos) {

            if (c == target) {      //skipps main target
                continue;
            }

            double dx = c.getX() - this.x;
            double dy = c.getY() - this.y;

            if (Math.sqrt(dx * dx + dy * dy) <= GameConfig.getSplashRadius()) {
                c.reduceHealth(splashDamage);  //damages nearby targets 
            }
        }
        this.active = false;
    }
}