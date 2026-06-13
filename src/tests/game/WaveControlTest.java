package tests.game;

import game.WaveControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WaveControlTest {

    private WaveControl waveCtrl;

    @BeforeEach
    public void setUp() {
        //creates before every test a WaveControll with starting wave 1 
        waveCtrl = new WaveControl();
    }

    @Test
    public void testInitialWaveSetup() {
        assertEquals(1, waveCtrl.curentWave(), "Game should start at wave 1");
        assertEquals(20, waveCtrl.getCrocosToSpawn(), "wave 1 should have had 20 crocs!");
    }

    @Test
    public void testPullNextCrocoType() {
        // wave 1 should consists of 20 crocs 
        String firstCroco = waveCtrl.pullNextCrocoType();
        assertEquals("basic_croco", firstCroco, "First Croco should have been a basic croco.");
    }

}