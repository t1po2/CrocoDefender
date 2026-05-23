package Crocodiles;

import java.awt.Point;
import java.util.ArrayList;

public class BasicCroco extends Croco {

    // reference of map path from game mechanics
    public BasicCroco(ArrayList<Point> path) {
        
        // Croco desc, the map, and speed 

        //impoortant crocoType is the Key for calling that resource
        
    // path,speed,crocoType,crocoHp,killReward
        super(path,3,"basic_croco",1,40);

    }

}