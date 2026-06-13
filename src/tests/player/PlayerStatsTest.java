package tests.player; 

import player.PlayerStats; 

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerStatsTest {

    private PlayerStats stats;

    @BeforeEach
    public void setUp() {
       //resets player stats 
        PlayerStats.resetForTesting(); 
        stats = new PlayerStats();
    }

    @Test
    public void testInitialValues() {
        assertEquals(2400, stats.getGold(), "start gold should be 2400");
        assertEquals(150, stats.getPlayerHp(), "start hp shpuld be 150.");
    }

    @Test
    public void testAddGold() {
        PlayerStats.addGold(600);
        assertEquals(3000, stats.getGold(), "Gold was not added correctly!");
    }

    @Test
    public void testRemoveGold() {
        PlayerStats.removeGold(400);
        assertEquals(2000, stats.getGold(), "Gold was not subtracted correctly!");
    }

    @Test
    public void testTakeDamage() {
        stats.takeDamage(30);
        assertEquals(120, stats.getPlayerHp(), "damage was not subtracted from hp");
    }
}