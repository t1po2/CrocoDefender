package tests.projectiles;

import projectiles.Projectile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectileTest {

    // Dummy projectile for the test 
    class DummyProjectile extends Projectile {
        public DummyProjectile() {
            // Start at  X=50, Y=50, no Target (null), 25 Dmg
            super(50, 50, null, 25, "dummy_key", null, null);
        }

        @Override
        protected void onHit() {
            // irrelevant for the test 
        }
    }

    @Test
    public void testInitialStats() {
        DummyProjectile proj = new DummyProjectile();
        
        assertEquals(50, proj.getX(), "Start X is false.");
        assertEquals(50, proj.getY(), "Start Y is false.");
        assertTrue(proj.isActive(), "A new projectile must be active at the start.");
    }

    @Test
    public void testUpdateWithNullTarget() {
        DummyProjectile proj = new DummyProjectile();
        
        // Act: We call update() even though there is no opponent (Target = null).
        proj.update();

        // Assert: The logic in your Projectile.java should immediately set the projectile to 'false'!
        assertFalse(proj.isActive(), "A projectile without a target (null) must not remain active, otherwise game errors will occur!");
    }
}