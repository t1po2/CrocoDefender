package game;

import java.awt.Point;
import java.util.ArrayList;



public class GameMechanic {


    private ArrayList<TowerData> towers;
    private String selectedTower = null;
    private GamePanel gamePanel;


    //own class to store references to resouce manager
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



    // Connect the UI to the brain
    public void setGamePanel(GamePanel panel) {
        this.gamePanel = panel;
    }

    public void setSelectedTower(String id){
        this.selectedTower = id;
    }





    public void attemptPlacement(Point p) {
        if (selectedTower == null) return; // Drop out if nothing is selected

        // Don't place a tower too close to another tower
        for (TowerData existing : towers) {
            double distance = p.distance(existing.pos);
            if (distance < 50) {   // cant place tower within 50 pixels of their radius 
                System.out.println("Cannot place here! Too close to another tower.");
                return;     // Deny placement
            }
        }

        // If it passes the rules, add it
        towers.add(new TowerData(p, selectedTower));
        System.out.println("Placed " + selectedTower + " at " + p.x + "," + p.y);
    }






    public ArrayList<TowerData> getPlacedTowers() {
        return towers;
    }




    
    public void update() {
        // Game Loop Logic: Move enemies, shoot bullets, etc

    }
    public void render(){
        // The Boss tells the UI to refresh
        if (gamePanel != null) {
            gamePanel.repaint();

        }
    }

}