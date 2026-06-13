package tests.towers;

import towers.Tower;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TowerTest {

    // 1. Create Dummy Tower for the test 
    class DummyTower extends Tower {
        public DummyTower() {
            // 100 Range, 10 Dmg, 60 RPM (1 Schuss pro Sekunde), 1000 Gold Cost, "test_proj"
            super(100, 10, 60, 1000, "test_proj"); 
        }
        
        // irrelevant for the test
        @Override public void applyUpgrade1() {}
        @Override public void applyUpgrade2() {}
        @Override public void applyUpgrade3() {}
        @Override public void applyUpgrade4() {}
    }

    @Test
    public void testTowerMathAndStats() {
        DummyTower tower = new DummyTower();

        // 1. RPM Check: 60 RPM = 60.000 / 60 = 1000 millisecs (fireRate)
        assertEquals(1000, tower.getFireRate(), "wrong rpm calculation");

        // 2. initial value (should equal to default buy price ))
        assertEquals(1000, tower.getTowerValue(), "The starting value of the tower must match to the purchase price.");
    }

    @Test
    public void testUpgradeLogic() {
        DummyTower tower = new DummyTower();
        
        // simulates Tower upgrade 
        tower.upgrade1();
        
        // check if upgraded is executed 
        assertEquals(1, tower.getLock1(), "The upgrade level (lock1) was not increased!");
    }
}