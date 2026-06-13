package tests.player; 

import player.PlayerStats; 

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerStatsTest {

    private PlayerStats stats;

    @BeforeEach
    public void setUp() {
        // Hier rufen wir deine neue reset-Methode auf
        PlayerStats.resetForTesting(); 
        stats = new PlayerStats();
    }

    @Test
    public void testInitialValues() {
        assertEquals(2400, stats.getGold(), "Start-Gold sollte 2400 sein.");
        assertEquals(150, stats.getPlayerHp(), "Start-HP sollte 150 sein.");
    }

    @Test
    public void testAddGold() {
        PlayerStats.addGold(600);
        assertEquals(3000, stats.getGold(), "Gold wurde nicht korrekt addiert!");
    }

    @Test
    public void testRemoveGold() {
        PlayerStats.removeGold(400);
        assertEquals(2000, stats.getGold(), "Gold wurde nicht korrekt abgezogen!");
    }

    @Test
    public void testTakeDamage() {
        stats.takeDamage(30);
        assertEquals(120, stats.getPlayerHp(), "Schaden wurde nicht von den HP abgezogen!");
    }
}