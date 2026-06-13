package projectiles;

import java.awt.image.BufferedImage;
import java.util.Random;


/**
 * Represents a visual shrapnel effect for explosions and area effects in the tower defense game.
 *
 * <p>This class extends Projectile to create small, short-lived particles that:
 * <ul>
 *   <li>Move randomly across the screen</li>
 *   <li>Have a limited lifespan</li>
 *   <li>Create visual effects for explosions and impacts</li>
 * </ul>
 *
 * <p>Unlike regular projectiles, shrapnel doesn't target enemies or deal damage.
 * It's purely a visual effect designed to enhance the game's feedback for explosions
 * and other impact events.
 *
 * @see Projectile
 */

public class Shrapnel extends Projectile {
   /** Horizontal velocity component of the shrapnel */
    private double vx;

    /** Vertical velocity component of the shrapnel */
    private double vy;

    /** Number of frames remaining before this shrapnel disappears */
    private int lifetime;

    /**
     * Constructs a new Shrapnel particle with random movement parameters.
     *
     * @param startX The initial X coordinate
     * @param startY The initial Y coordinate
     */
    public Shrapnel(double startX,double startY){
        super(startX,startY,null,0,"",null,null);  // set every parameter we dont need to null

        Random rand = new Random();
        //random direction and velocity of shrapnel
        this.vx = (rand.nextDouble() - 0.5)*10;
        this.vy = (rand.nextDouble() - 0.5)*10;

        this.lifetime = 10 + rand.nextInt(30);  // lifetime of shrapnel between 10 and 40 frames
    }

    /**
     * Updates the shrapnel's position and checks if it should be removed.
     *
     * <p>This method:
     * <ul>
     *   <li>Moves the shrapnel according to its velocity components</li>
     *   <li>Decrements the remaining lifetime</li>
     *   <li>Deactivates the shrapnel when its lifetime expires</li>
     * </ul>
     */
    @Override
    public void update(){

        //shrapnell random movement logic (Im so proud)
        this.x += vx;
        this.y +=vy;

        this.lifetime--;

        if(lifetime <=0){       //GET THE FUCK OUT HEHEHEHEHHE 
            this.active = false;
        }
    }

    /**
     * Empty implementation as shrapnel doesn't have hit functionality.
     *
     * <p>This method overrides the abstract method from Projectile but
     * does nothing because shrapnel has no effect on targets.
     */
    @Override
    protected void onHit() {
    //empty its just visual effect
    }

    /**
     * Returns null to indicate that GamePanel should handle shrapnel drawing manually.
     *
     * @return null to force custom drawing in GamePanel
     */
    @Override
    public BufferedImage getTexture() {
        return null;   // force GamePanel to draw it manually
    }


    /**
     * Identifies this object as shrapnel, overriding the parent class method.
     *
     * @return true, indicating this is a shrapnel particle
     */
    @Override
    public boolean isShrapnel(){ 
        return true; 
    } 
}
