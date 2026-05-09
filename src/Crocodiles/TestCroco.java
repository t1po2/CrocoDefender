package Crocodiles;

import java.awt.Point;
import java.util.ArrayList;

public class TestCroco extends Croco {

    // reference of map path from game mechanics
    public TestCroco(ArrayList<Point> path) {
        
        // Croco desc, the map, and speed 
        super(path);

        this.dmg =1;
        this.speed = 2;
    }

}