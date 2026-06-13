package projectiles;

public class TextProjectile extends Projectile {
    
    private String text;
    private int lifeTime = 45; // how long the txt floats 45 frames

    public TextProjectile(int startX, int startY, String text) {
        super(startX, startY, null, 0, null, null, null); 
        
        this.text = text;
    }


    @Override
    public void update() {
        this.y -= 1; //flys straight up
        this.lifeTime--;
        
        if (this.lifeTime <= 0) {
            this.active = false; 
        }
    }

    public String getText() {
        return text;
    }

    @Override
    protected void onHit() {
    }
}