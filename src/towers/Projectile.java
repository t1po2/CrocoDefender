package towers;

import Crocodiles.Croco;

public class Projectile {
    private double x, y;
    private double speed = 7.0; // How fast the projectile flies
    private Croco target;
    private int damage;
    private boolean active = true;

    public Projectile(double startX, double startY, Croco target, int damage) {
        this.x = startX;
        this.y = startY;
        this.target = target;
        this.damage = damage;
    }

    public void update() {
        // If the crocodile died or reached the end, delete this projectile
        if (target == null || target.hasReachedEnd() || target.getHealth() <= 0) {
            active = false;
            return;
        }

        // Calculate distance to the target
        double dx = target.getX() - x;
        double dy = target.getY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < speed) {
            // BOOM! Hit the target!
            target.reduceHealth(damage); 
            active = false;
        } else {
            // Move closer to the target
            x += (dx / distance) * speed;
            y += (dy / distance) * speed;
        }
    }

    // Getters for drawing
    public double getX() { return x; }
    public double getY() { return y; }
    public boolean isActive() { return active; }


}