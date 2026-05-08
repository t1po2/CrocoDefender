package game;

import java.awt.Point;
import java.util.ArrayList;

import towers.Tower;


public class GameMechanic {


    private Window gameWindow;
    private ArrayList<TowerData> towers;
    private String selectedTower = null;

    public static class TowerData {
        public Point pos;
        public String resID;

        public TowerData(Point pos, String resID){
            this.pos = pos;
            this.resID = resID;
        }

    }


    public GameMechanic(){

        this.towers = new ArrayList<>();
    }

    public void setSelectedTower(String id){
        this.selectedTower = id;
    }
    public void attemptPlacement(Point p) {
        if (selectedTower != null) {
            towers.add(new TowerData(p, selectedTower));
            System.out.println("Placed " + selectedTower + " at " + p);
        }
    }
    public ArrayList<TowerData> getPlacedTowers() {
        return towers;
    }




    
    public void update() {

    }
    public void render(){

    }

}