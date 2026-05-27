package game;

import javax.swing.*;
import Crocodiles.Croco;
import game.GameMechanic.TowerData;
import projectiles.Projectile;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private BufferedImage map;
    private GameMechanic mechanic;

    // toggle mouseListener
    private boolean toggleMouseListener = true; // toggle mouseListener

    // Labels for Player Stats
    private JLabel playerHpL;
    private JLabel playerGoldL;
    private JLabel playerWaveL;
    


    // For UpgradeUI
    private UpgradePanel upgradePanel;
    private TowerData rangeOfCurrentTower = null;

    public GamePanel(GameMechanic mechanics) {
        this.mechanic = mechanics;

        // Link this panel back to the mechanic so the mechanic can control it
        mechanics.setGamePanel(this);

        // GamePanel Setup
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(800, 600));

        // load in the map via mapID
        map = Resource.getResource(mechanics.getMapName());

        // --- Labels for Player Stats ---
        JPanel statsPanel = new JPanel();
        statsPanel.setPreferredSize(new Dimension(200, 100));
        statsPanel.setOpaque(false);
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));

        // creeate labels
        // need to import stats from GameMechanics
        playerHpL = new JLabel("Health: 0");
        playerHpL.setFont(new Font("Arial", Font.BOLD, 20));
        playerHpL.setForeground(Color.RED); // red font color for hp

        playerGoldL = new JLabel("Gold: 0");
        playerGoldL.setFont(new Font("Arial", Font.BOLD, 20));
        playerGoldL.setForeground(Color.YELLOW); // yellow font color for gold

        playerWaveL = new JLabel("Wave: 1");
        playerWaveL.setFont(new Font("Arial", Font.BOLD, 20));
        playerWaveL.setForeground(Color.WHITE); // White for current Wave

        // add labels to panel
        statsPanel.add(playerHpL);
        statsPanel.add(playerGoldL);
        statsPanel.add(playerWaveL);

        // new Panel on the bottom for the surrender button 

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BorderLayout());

        JButton exitBtn = new JButton("Surrender");
        exitBtn.setFocusPainted(false);
        exitBtn.setBackground(new Color(200, 50, 50));
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        exitBtn.setPreferredSize(new Dimension(50,20));

        exitBtn.addActionListener(e -> {
            mechanic.saveGameHighscore();
            java.awt.Window window = SwingUtilities.getWindowAncestor(this);  //had to put java.awt infront cause i have a class with the same name  type missmatch 
            if (window != null){
                window.dispose();
            }
            new game.main_menu.MainMenu();
        });

        //put bottompanel at the bottom 
        bottomPanel.add(exitBtn,BorderLayout.SOUTH);
        JPanel leftContainer = new JPanel(new BorderLayout());
        leftContainer.setPreferredSize(new Dimension(135,600));
        leftContainer.setOpaque(false);
        
        leftContainer.add(statsPanel, BorderLayout.NORTH); // Player info top 
        leftContainer.add(bottomPanel, BorderLayout.CENTER); // exit button center bottom but left cause whole container is left 

        this.add(leftContainer, BorderLayout.WEST);

        // --- Side panel for tower selection ---

        JPanel towerMenu = new JPanel();
        towerMenu.setPreferredSize(new Dimension(300, 600));
        towerMenu.setLayout(new GridLayout(6, 1)); // 6 rows 1 colum
        towerMenu.setOpaque(false);

        // Define your tower IDs
        String[] towerIDs = {
                "basic_tower",
                "sniper_tower",
                "tank_tower",
                "duck_tower",
                "strong_duck",
                "coming soon..."
        };

        // need to create seperate string for label name

        String[] towerName = {
                "Basic Tower 1800 Gold",
                "Sniper Tower 3400 Gold",
                "Tank Tower 2400 Gold",
                "Super Duck 5200 Gold",
                "Strong Duck 2600 Gold",
                "coming soon..."
        };

        for (int i = 0; i < towerIDs.length; i++) {
            // We store the specific ID for THIS button in a local variable
            String currentID = towerIDs[i];

            JButton btn = new JButton(towerName[i]);
            btn.setFocusPainted(false);

            // 2. The listener now uses the specific ID for this button
            btn.addActionListener(e -> {
                mechanic.setSelectedTower(currentID);
                System.out.println("Selected Tower ID: " + currentID);
            });

            towerMenu.add(btn);
        }
        this.add(towerMenu, BorderLayout.EAST);

        // Mouseclick to place tower OR select a tower
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point clickPoint = e.getPoint();
                boolean clickedOnTower = false;

                // checks if tower is clicked
                for (GameMechanic.TowerData tower : mechanic.getPlacedTowers()) {
                    if (clickPoint.distance(tower.pos) < 32) { // 32 Pixel Hitbox
                        // if tower is in 32 radius is clicked
                        upgradePanel.updateUIForTower(tower);
                        rangeOfCurrentTower = tower; // sets the tower of which the range will show
                        clickedOnTower = true;
                        break;
                    }
                }
                // if nothing is clicked build selected tower
                if (!clickedOnTower) {
                    upgradePanel.hidePanel(); // hides panel
                    mechanic.placeTower(clickPoint);
                    mechanic.setSelectedTower(null);
                    rangeOfCurrentTower = null;
                }

                mechanic.render(); // repainbt UI

                // toggle mouseListener via variable
                if (toggleMouseListener == true) {
                    System.out.println("waypoints.add(new Point(" + e.getX() + "," + e.getY() + "));");
                }
            }
        });
    }

    // helper function to update stats
    public void updatePlayerStats(int health, int gold, int wave) {
        if (playerHpL != null && playerGoldL != null && playerWaveL != null) {
            playerHpL.setText("Health: " + health);
            playerGoldL.setText("Gold: " + gold);
            playerWaveL.setText("Wave: " + wave);
        }
    }

    // --- Function to render all the game components ---

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background map first
        g.drawImage(map, 0, 0, 800, getHeight(), this);

        // Draw every tower from the logic list
        for (GameMechanic.TowerData tower : mechanic.getPlacedTowers()) {
            if (tower.texture != null) {
                // Subtract 32 from X and Y to center the 64x64 image on the click
                int drawX = tower.pos.x - 32;
                int drawY = tower.pos.y - 32;

                g.drawImage(tower.texture, drawX, drawY, 64, 64, null);
            } else {
                System.out.println("Can't place Tower | Texture not found");
            }
        }

        if (mechanic.getCrocos() != null) {
            // --- DRAW THE CROCODILES ---
            for (Croco croco : mechanic.getCrocos()) {
                if (croco.getTexture() != null) {
                    g.drawImage(croco.getTexture(), (int) croco.getX() - 32, (int) croco.getY() - 32, 64, 64, null);
                }

            }

            // --- DRAW THE PROJECTILES ---
            if (mechanic.getProjectiles() != null) {
                // We use a safe copy to prevent ConcurrentModificationExceptions while drawing
                ArrayList<Projectile> safeProjectiles = new ArrayList<>(mechanic.getProjectiles());

                for (Projectile p : safeProjectiles) {

                    if (p.isShrapnel()) {
                        g.setColor(Color.BLACK);
                        g.fillRect((int) p.getX() - 3, (int) p.getY() - 3, 6, 6); // 6x6 square for shrapnel -> need to subtract 3 for the center
                        continue;
                    }
                    if (p.getTexture() != null) {
                        Graphics2D g2d = (Graphics2D) g.create();

                        int cx = (int) p.getX(); // g2d draws the projectile defaultly at 0, 0 this woulD be top left corner tahts why we set the palce to draw at the center of our projectile
                        int cy = (int) p.getY(); 
                        g2d.translate(cx, cy);
                        g2d.rotate(p.getAngle()); // rotates picture the angle
                        g2d.drawImage(p.getTexture(), -8, -8, 16, 16, null);
                        g2d.dispose();

                    } else {
                        System.out.println("Error coud not find projectle texture");
                    }
                }
            }

            // Game Over Screen Overlay
            if (mechanic.returnGameOver()) {
                g.setColor((new Color(0, 0, 0, 150)));
                g.fillRect(0, 0, getWidth(), getHeight());

                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 50));
                g.drawString("Game Over!", 250, 300);
            }

            // draw range of clicked tower
            if (rangeOfCurrentTower != null) {
                Graphics2D g2d = (Graphics2D) g;

                // get range of current Tower
                int range = rangeOfCurrentTower.specs.getRange(); // TowerData.Tower.method inside tower data there is a Tower variable(specs) via specs we can access methods of that towertype
                                                                 
                int centerX = rangeOfCurrentTower.pos.x;
                                                                 
                int centerY = rangeOfCurrentTower.pos.y;

                g2d.setColor(new Color(255, 255, 255, 40));
                g2d.fillOval(centerX - range, centerY - range, range * 2, range * 2);
                g2d.setColor(new Color(255, 255, 255, 150));
                g2d.drawOval(centerX - range, centerY - range, range * 2, range * 2);
            }
        }
    }

    // setter for UpgradeUI

    public void setUpgradePanel(game.UpgradePanel upgradePanel) {
        this.upgradePanel = upgradePanel;
    }

    public void clearRangeHighlight() { // helper method, deletes range of Tower when Tower is getting selled
        this.rangeOfCurrentTower = null;
    }
}