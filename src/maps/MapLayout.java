package maps;

import java.awt.Point;
import java.util.ArrayList;

public abstract class MapLayout {


    protected ArrayList<Point> waypoints;
    private String mapDesc;

    public MapLayout(String mapDesc){
        this.waypoints = new ArrayList<>();
        this.mapDesc = mapDesc;
        
        // -- Paste path waypoints here --
    }

    //Getters
    public void getMapDesc(){
        System.out.println(mapDesc);

    }

    public ArrayList<Point> getWaypoints(){
        return this.waypoints;
    }

    // Set new map Desciption
    public void setMapDesc(String mapDesc){
        this.mapDesc = mapDesc;
    }

}