package tests.projectiles;

import projectiles.Projectile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectileTest {

    // Dummy Projektil für den Test
    class DummyProjectile extends Projectile {
        public DummyProjectile() {
            // Start bei X=50, Y=50, KEIN Target (null), 25 Dmg
            super(50, 50, null, 25, "dummy_key", null, null);
        }

        @Override
        protected void onHit() {
            // Für den Test irrelevant
        }
    }

    @Test
    public void testInitialStats() {
        DummyProjectile proj = new DummyProjectile();
        
        assertEquals(50, proj.getX(), "Start X ist falsch.");
        assertEquals(50, proj.getY(), "Start Y ist falsch.");
        assertTrue(proj.isActive(), "Ein neues Projektil muss am Anfang aktiv sein.");
    }

    @Test
    public void testUpdateWithNullTarget() {
        DummyProjectile proj = new DummyProjectile();
        
        // Act: Wir rufen update() auf, obwohl es keinen Gegner gibt (Target = null)
        proj.update();

        // Assert: Die Logik in deiner Projectile.java sollte das Projektil sofort auf 'false' setzen!
        assertFalse(proj.isActive(), "Projektil ohne Ziel (null) darf nicht aktiv bleiben, sonst gibt es Fehler im Spiel!");
    }
}