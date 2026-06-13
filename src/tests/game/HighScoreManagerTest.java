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

        // Wir fügen zwei Scores hinzu (einen kleinen, danach einen großen)
        manager.addScore("NoobPlayer", 5);
        manager.addScore("ProGamer", 50);

        // Prüfen, ob die Liste größer geworden ist
        assertEquals(initialSize + 2, manager.getScores().size(), "Scores wurden nicht hinzugefügt!");

        // WICHTIG: Da die Liste automatisch sortiert wird, MUSS der ProGamer (Welle 50) 
        // jetzt ganz oben auf Platz 1 (Index 0) stehen, obwohl er als zweites hinzugefügt wurde!
        HighScoreEntry topScore = manager.getScores().get(0);
        assertTrue(topScore.getWave() >= 50, "Die Sortierung (compareTo) funktioniert nicht richtig! Der höchste Score muss oben stehen.");
    }
}