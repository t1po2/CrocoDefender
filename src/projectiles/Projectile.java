package projectiles;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import Crocodiles.Croco;
import game.GameConfig;
import game.Resource;


/**
 * Abstract base class for all projectile types in the tower defense game.
 *
 * <p>This class defines the common behavior and properties for all projectiles including:
 * <ul>
 *   <li>Position tracking</li>
 *   <li>Movement logic</li>
 *   <li>Target acquisition</li>
 *   <li>Damage handling</li>
 *   <li>Collision detection</li>
 * </ul>
 *
 * <p>Subclasses must implement the {@link #onHit()} method to define specific
 * behavior when the projectile hits its target.
 *
 * @see Croco
 * @see Resource
 */

public abstract class Projectile {
    
    /** X coordinate of the projectile on the game map */
    protected double x, y;

    /** Speed at which the projectile travels toward its target */
    protected double speed;

    /** The crocodile enemy that this projectile is targeting */
    protected Croco target;

    /** Amount of damage this projectile will inflict on its target */
    protected int damage;

    /** Flag indicating whether this projectile is still active and should be updated */
    protected boolean active = true;

    /** Visual representation of this projectile */
    protected BufferedImage texture;

    /** Reference to the collection of all active crocodiles in the game */
    protected ArrayList<Croco> allCrocos;

    /** Reference to the collection of all active projectiles in the game */
    ArrayList<Projectile> allProjectiles;

    /**
     * Constructs a new Projectile with the specified properties.
     *
     * @param startX The initial X coordinate of the projectile
     * @param startY The initial Y coordinate of the projectile
     * @param target The target crocodile this projectile is tracking
     * @param damage The amount of damage this projectile will deal
     * @param projectileKey The resource key for the projectile's texture
     * @param allCrocos Reference to the list of all active crocodiles in the game
     * @param allProjectiles Reference to the list of all active projectiles in the game
     *
     * @see Resource#getResource(String)
     * @see GameConfig#getProjectileSpeed()
     */
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
    /**
     * Updates the projectile's position and checks for target collision.
     *
     * <p>This method handles:
     * <ul>
     *   <li>Projectile deactivation if target is invalid</li>
     *   <li>Projectile movement toward the target</li>
     *   <li>Collision detection with the target</li>
     * </ul>
     */
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
    /**
     * Called when this projectile hits its target.
     *
     * <p>Subclasses must implement this method to define the specific behavior
     * when a projectile reaches its target, such as applying damage or creating
     * area of effect explosions.
     *
     * @see #onHit()
     */
    protected abstract void onHit();

    // calcs angle of adjacent and hypothenuse to rotate projectile
    /**
     * Calculates the angle of this projectile based on its position relative to the target.
     *
     * <p>This angle can be used to properly rotate the projectile's texture to face
     * its target, creating more realistic projectile visuals.
     *
     * @return The angle in radians that the projectile should be rotated, or 0 if no target
     */
    public double getAngle() {
        if (target != null) {
            double dy = target.getY() - this.y;     
            double dx = target.getX() - this.x;     
            return Math.atan2(dy, dx);   
        } 
        return 0;                   
    }

    // Getters
    /**
     * Returns the current X coordinate of this projectile.
     *
     * @return The X coordinate
     */
    public double getX() { return x; }

    /**
     * Returns the current Y coordinate of this projectile.
     *
     * @return The Y coordinate
     */
    public double getY() { return y; }

    /**
     * Checks if this projectile is still active and should be updated and rendered.
     *
     * @return true if the projectile is active, false otherwise
     */
    public boolean isActive() { return active; }

    /**
     * Returns the texture used to render this projectile.
     *
     * @return The BufferedImage texture for this projectile
     */
    public BufferedImage getTexture() { return texture; }

    /**
     * Determines whether this projectile is a shrapnel (small piece from an explosion).
     *
     * <p>Default implementation returns false. Subclasses that represent shrapnel
     * should override this method to return true.
     *
     * @return true if this projectile is a shrapnel piece, false otherwise
     */
    public boolean isShrapnel() {
        return false;
    }
}