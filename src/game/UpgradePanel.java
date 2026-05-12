package game; // Oder in deinem UI-Package

import game.GameMechanic.TowerData;

import javax.swing.*;
import java.awt.*;

public class UpgradePanel extends JPanel {

    private JLabel infoLabel;
    private JButton upgradeBtn1;
    private JButton upgradeBtn2;
    private JButton upgradeBtn3;
    private JButton upgradeBtn4;
    private JButton sellBtn;
    private TowerData currentTower;
    private GameMechanic gameMechanic;


    //for balance changes:

    private int factor = 5;     // setts factor of which upgrade prices increases after upgraded realFactor = ((factor/10)+1)


    public UpgradePanel() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        this.setBackground(Color.DARK_GRAY);
        this.setPreferredSize(new Dimension(800,80)); 

        infoLabel = new JLabel();
        infoLabel.setForeground(Color.WHITE);

        upgradeBtn1 = new JButton("Upgrade 1");
        upgradeBtn2 = new JButton("Upgrade 2");
        upgradeBtn3 = new JButton("Upgrade 3");
        upgradeBtn4 = new JButton("Upgrade 4");
        
        sellBtn = new JButton("Verkaufen");

        // pgrade logic
        upgradeBtn1.addActionListener(e -> {

            if (currentTower != null && gameMechanic != null) {
                int currentPlayerGold = gameMechanic.getPlayerGold();
                int cost = currentTower.specs.getUpgrade1Cost();

            if (currentPlayerGold >= cost) {
                int spent = currentTower.specs.upgrade1(); 
                gameMechanic.spendGold(spent); 
                currentTower.specs.setUpgrade1Cost(factor);      // increase upgrade price by factor
                updateUIForTower(currentTower);
                System.out.println("Upgrade erfolgreich!");
            } else {
                System.out.println("Nicht genug Gold!");
                }
            }
        });

        upgradeBtn2.addActionListener(e -> {
            if (currentTower != null) {
                System.out.println("Upgrade gedrückt für: " + currentTower.resID);
                //currentTower.specs.upgrade2();
                updateUIForTower(currentTower); // Text nach Upgrade aktualisieren
            }
        });

        upgradeBtn3.addActionListener(e -> {
            if (currentTower != null) {
                System.out.println("Upgrade gedrückt für: " + currentTower.resID);
               // currentTower.specs.upgrade3();
                updateUIForTower(currentTower); // Text nach Upgrade aktualisieren
            }
        });

        upgradeBtn4.addActionListener(e -> {
            if (currentTower != null) {
                System.out.println("Upgrade gedrückt für: " + currentTower.resID);
               // currentTower.specs.upgrade4();
                updateUIForTower(currentTower); // Text nach Upgrade aktualisieren
            }
        });

        sellBtn.addActionListener(e ->{
            if (currentTower!= null && gameMechanic != null) {
                gameMechanic.addGold(currentTower.specs.getTowerValue());
                gameMechanic.sellTower(currentTower);
                hidePanel();

            }
        });
        
        this.add(infoLabel);
        this.add(sellBtn);
        this.add(upgradeBtn1);
        this.add(upgradeBtn2);
        this.add(upgradeBtn3);
        this.add(upgradeBtn4);
       
        
        this.setVisible(false);     //upgrade pannel is invis at the start
    }

   //reference in game panle tower is clicked current tower is linked this method so UpgradeUI always knows which tower is clicked
    public void updateUIForTower(TowerData tower) {
        this.currentTower = tower;
        // Texte anpassen
        infoLabel.setText("Sell Price: "+tower.specs.getTowerValue());
        upgradeBtn1.setText("Upgrade 1 (" + tower.specs.getUpgrade1Cost() + "Gold)");
        upgradeBtn2.setText("Upgrade 2 (" + tower.specs.getUpgrade2Cost() + "Gold)");
        upgradeBtn3.setText("Upgrade 3 (" + tower.specs.getUpgrade3Cost() + "Gold)");
        upgradeBtn4.setText("Upgrade 4 (" + tower.specs.getUpgrade4Cost() + "Gold)");
        
        this.setVisible(true); //set panel to visible
    }

    public void hidePanel() {
        this.currentTower = null;
        this.setVisible(false); //hoide panel after clicking in empty space
    }
    //setters
    public void setGameMechanic(GameMechanic game){
        this.gameMechanic = game;
    }
}   