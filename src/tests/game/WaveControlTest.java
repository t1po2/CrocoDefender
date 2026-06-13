package tests.game;

import game.WaveControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WaveControlTest {

    private WaveControl waveCtrl;

    @BeforeEach
    public void setUp() {
        // Erstellt vor jedem Test eine frische WaveControl (startet bei Welle 1)
        waveCtrl = new WaveControl();
    }

    @Test
    public void testInitialWaveSetup() {
        assertEquals(1, waveCtrl.curentWave(), "Das Spiel sollte bei Welle 1 starten.");
        assertEquals(20, waveCtrl.getCrocosToSpawn(), "Welle 1 sollte exakt 20 Krokodile haben.");
    }

    @Test
    public void testPullNextCrocoType() {
        // Bei Welle 1 sollten alle 20 Krokodile "basic_croco" sein
        String firstCroco = waveCtrl.pullNextCrocoType();
        assertEquals("basic_croco", firstCroco, "Das erste Krokodil muss ein basic_croco sein.");
    }

    @Test
    public void testIncrementWave() {
        // Wir springen zu Welle 2
        waveCtrl.incrementWave();
        
        assertEquals(2, waveCtrl.curentWave(), "Die Welle wurde nicht korrekt hochgezählt.");
        // Da Welle 2 in deinem Code aus der Hälfte des alten Patterns (10) + dem alten Pattern (20) besteht
        // sollte die neue Größe 30 sein. (10 + 20)
        assertEquals(30, waveCtrl.getCrocosToSpawn(), "Die Krokodil-Anzahl in Welle 2 stimmt nicht mit der Logik überein.");
    }
}