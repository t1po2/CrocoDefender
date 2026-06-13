package tests.game;

import game.HighScoreManager;
import game.HighScoreEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HighScoreManagerTest {

    private HighScoreManager manager;

    @BeforeEach
    public void setUp() {
        manager = new HighScoreManager();
    }

    @Test
    public void testAddAndSortScores() {
        int initialSize = manager.getScores().size();

        // add 2 Player 1 with a lot of waves and one with less waves 
        manager.addScore("NoobPlayer", 5);
        manager.addScore("ProGamer", 50);

        // Check if list is getting bigger
        assertEquals(initialSize + 2, manager.getScores().size(), "no scores added!");

       // Check if Sort Highscore works 
        HighScoreEntry topScore = manager.getScores().get(0);
        assertTrue(topScore.getWave() >= 50, "The sorting (compareTo) isn't working correctly! The highest score must be at the top.");
    }
}