package projectiles;

import java.util.Random;

public class Shrapnel extends Projectile {
    private double vx,vy;   //velocity of shrapnells
    private int lifetime; //lifetime of shrapnels in frames

    public Shrapnel(double startX,double startY){
        super(startX,startY,null,0,"",null,null);  // set every parameter we dont need to null

        Random rand = new Random();
        //random direction and velocity of shrapnel
        this.vx = (rand.nextDouble() - 0.5)*10;
        this.vy = (rand.nextDouble() - 0.5)*10;

        this.lifetime = 10 + rand.nextInt(30);  // lifetime of shrapnel between 10 and 40 frames
    }


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

    @Override
    protected void onHit() {
    //empty its just visual effect
    }
    
}
