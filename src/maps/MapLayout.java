package maps;

import java.awt.Point;
import java.util.ArrayList;


/**
 * Abstract base class defining the structure for all map layouts in the tower defense game.
 *
 * <p>This class provides the foundation for map implementations by defining:
 * <ul>
 *   <li>Waypoint paths that enemies follow</li>
 *   <li>A description of the map</li>
 *   <li>Core functionality for map navigation</li>
 * </ul>
 *
 * <p>Specific map implementations should extend this class and define their
 * unique waypoint paths by initializing the waypoints ArrayList in their constructor.
 *
 * @see java.awt.Point
 */

public abstract class MapLayout {
/**
     * Collection of waypoints that define the path enemies will follow.
     * Each point represents a coordinate on the game map.
     */
    protected ArrayList<Point> waypoints;

    /**
     * Text description of the map providing information about its design,
     * difficulty, or special features.
     */
    private String mapDesc;

    /**
     * Constructs a new map layout with the specified description.
     *
     * <p>This constructor initializes the waypoints collection and stores
     * the provided map description. Concrete subclasses should define
     * their specific waypoint paths by adding Points to the waypoints ArrayList.
     *
     * @param mapDesc Text description of the map
     */

    public MapLayout(String mapDesc){
        this.waypoints = new ArrayList<>();
        this.mapDesc = mapDesc;
        
        // -- Paste path waypoints here --
    }

    //Getters
    /**
     * Prints the map description to the console.
     *
     * <p>This is primarily for debugging and development purposes to
     * display information about the current map.
     */
    public void getMapDesc(){
        System.out.println(mapDesc);

    }

    /**
     * Returns the collection of waypoints that define the enemy path.
     *
     * @return An ArrayList of Points representing the enemy path waypoints
     */
    public ArrayList<Point> getWaypoints(){
        return this.waypoints;
    }

    // Set new map Desciption
    /**
     * Updates the map's description.
     *
     * @param mapDesc The new text description for this map
     */
    public void setMapDesc(String mapDesc){
        this.mapDesc = mapDesc;
    }

}