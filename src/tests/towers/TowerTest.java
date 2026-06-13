package tests.towers;

import towers.Tower;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TowerTest {

    // 1. Wir bauen uns einen Dummy-Turm, um die abstrakte Klasse zu testen
    class DummyTower extends Tower {
        public DummyTower() {
            // 100 Range, 10 Dmg, 60 RPM (1 Schuss pro Sekunde), 1000 Gold Cost, "test_proj"
            super(100, 10, 60, 1000, "test_proj"); 
        }
        
        // Die abstrakten Methoden müssen wir ausfüllen, lassen sie für den Test aber leer
        @Override public void applyUpgrade1() {}
        @Override public void applyUpgrade2() {}
        @Override public void applyUpgrade3() {}
        @Override public void applyUpgrade4() {}
    }

    @Test
    public void testTowerMathAndStats() {
        DummyTower tower = new DummyTower();

        // 1. RPM Check: 60 RPM = 60.000 / 60 = 1000 Millisekunden (fireRate)
        assertEquals(1000, tower.getFireRate(), "Die RPM zu Millisekunden-Rechnung ist falsch!");

        // 2. Initialer Wert (Sollte dem Kaufpreis entsprechen)
        assertEquals(1000, tower.getTowerValue(), "Der Startwert des Turms muss dem Kaufpreis entsprechen.");
    }

    @Test
    public void testUpgradeLogic() {
        DummyTower tower = new DummyTower();
        
        // Simuliere einen Upgrade-Kauf
        tower.upgrade1();
        
        // Prüfen, ob das Upgrade-Level (lock) gestiegen ist
        assertEquals(1, tower.getLock1(), "Das Upgrade-Level (lock1) wurde nicht erhöht!");
    }
}