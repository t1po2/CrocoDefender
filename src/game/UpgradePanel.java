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

    private int factor = GameConfig.getUpgradeFactor(); // setts factor of which upgrade prices increases after upgraded realFactor = ((factor/10)+1)


    public UpgradePanel() {
        // 1 row , 6 collums , 1 for a label + 5 for buttons ,10 px horizontal padding 0 vertikal 
        this.setLayout(new GridLayout(1, 6, 10, 0));    
        // padding: top 15, left 20 , bottom 15, rright 20
        this.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
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
                    }
                } else {
                    System.out.println("Nicht genug Gold!");
            }
        });

        upgradeBtn2.addActionListener(e -> {
             if (currentTower != null && gameMechanic != null) {
                int currentPlayerGold = gameMechanic.getPlayerGold();
                 int cost = currentTower.specs.getUpgrade2Cost();

            if (currentPlayerGold >= cost) {
                int spent = currentTower.specs.upgrade2(); 
                gameMechanic.spendGold(spent); 
                currentTower.specs.setUpgrade2Cost(factor);     
                updateUIForTower(currentTower);
                System.out.println("Upgrade erfolgreich!");
            } else {
                System.out.println("Nicht genug Gold!");
                }
            }
        });

        upgradeBtn3.addActionListener(e -> {
             if (currentTower != null && gameMechanic != null) {
                int currentPlayerGold = gameMechanic.getPlayerGold();
                 int cost = currentTower.specs.getUpgrade3Cost();

            if (currentPlayerGold >= cost) {
                int spent = currentTower.specs.upgrade3(); 
                gameMechanic.spendGold(spent); 
                currentTower.specs.setUpgrade3Cost(factor);     
                updateUIForTower(currentTower);
                System.out.println("Upgrade erfolgreich!");
            } else {
                System.out.println("Nicht genug Gold!");
                }
            }
        });

        upgradeBtn4.addActionListener(e -> {
              if (currentTower != null && gameMechanic != null) {
                int currentPlayerGold = gameMechanic.getPlayerGold();
                 int cost = currentTower.specs.getUpgrade4Cost();

            if (currentPlayerGold >= cost) {
                int spent = currentTower.specs.upgrade4(); 
                gameMechanic.spendGold(spent); 
                currentTower.specs.setUpgrade4Cost(factor);      
                updateUIForTower(currentTower);
                System.out.println("Upgrade erfolgreich!");
            } else {
                System.out.println("Nicht genug Gold!");
                }
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
        infoLabel.setText("Sell Price: " + tower.specs.getTowerValue());

        sellBtn.setToolTipText("Turm verkaufen und Gold zurückerhalten.");

        // --- UPGRADE 1 CHECK ---
        if (tower.specs.locked1()) {
            upgradeBtn1.setText("Max. level");
            upgradeBtn1.setToolTipText("Upgraded reached max level!");
            upgradeBtn1.setEnabled(false); //disable button if upgrade is locked
        } else {
            upgradeBtn1.setText("Upgrade 1 ("+tower.specs.getLock1()+")");
            String tooltipInfo = "<html>"
                           + "<b>" + tower.specs.getUpgrade1Desc() + "</b><br>"
                           + "Kosten: <b>" + tower.specs.getUpgrade1Cost() + " Gold</b>"
                           + "</html>";
        upgradeBtn1.setToolTipText(tooltipInfo);
        upgradeBtn1.setEnabled(true);  //set button text and enable if upgrade is not locked
        }
        // --- UPGRADE 2 CHECK ---
        if (tower.specs.locked2()) {
            upgradeBtn2.setText("Max. level");
            upgradeBtn2.setToolTipText("Upgraded reached max level!");
            upgradeBtn2.setEnabled(false); //disable button if upgrade is locked
        } else {
            upgradeBtn2.setText("Upgrade 2 ("+tower.specs.getLock2()+")");
            String tooltipInfo = "<html>"
                           + "<b>" + tower.specs.getUpgrade2Desc() + "</b><br>"
                           + "Kosten: <b>" + tower.specs.getUpgrade2Cost() + " Gold</b>"
                           + "</html>";
        upgradeBtn2.setToolTipText(tooltipInfo);
        upgradeBtn2.setEnabled(true);  //set button text and enable if upgrade is not locked
        }
        // --- UPGRADE 3 CHECK ---
        if (tower.specs.locked3()) {
            upgradeBtn3.setText("Max. level");
            upgradeBtn3.setToolTipText("Upgraded reached max level!");
            upgradeBtn3.setEnabled(false); //disable button if upgrade is locked
        } else {
            upgradeBtn3.setText("Upgrade 3 ("+tower.specs.getLock3()+")");
            String tooltipInfo = "<html>"
                           + "<b>" + tower.specs.getUpgrade3Desc() + "</b><br>"                 //Jframe HTML for good looking text hehehe 
                           + "Kosten: <b>" + tower.specs.getUpgrade3Cost() + " Gold</b>"        //<html> start and end 
                           + "</html>";                                                         // <b> = thick text <br> = \n
        upgradeBtn3.setToolTipText(tooltipInfo);
        upgradeBtn3.setEnabled(true);  //set button text and enable if upgrade is not locked
        }
        // --- UPGRADE 4 CHECK ---
        if (tower.specs.locked4()) {
            upgradeBtn4.setText("Max. level");
            upgradeBtn4.setToolTipText("Upgraded reached max level!");
            upgradeBtn4.setEnabled(false); //disable button if upgrade is locked
        } else {
            upgradeBtn4.setText("Upgrade 4 ("+tower.specs.getLock4()+")");
            String tooltipInfo = "<html>"
                           + "<b>" + tower.specs.getUpgrade4Desc() + "</b><br>"
                           + "Kosten: <b>" + tower.specs.getUpgrade4Cost() + " Gold</b>"
                           + "</html>";
        upgradeBtn4.setToolTipText(tooltipInfo);
        upgradeBtn4.setEnabled(true);  //set button text and enable if upgrade is not locked
        }
        this.setVisible(true); 
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