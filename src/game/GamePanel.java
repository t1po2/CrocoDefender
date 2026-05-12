package game;

import javax.swing.*;

import Crocodiles.Croco;
import towers.Projectile;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private BufferedImage map;
    private GameMechanic mechanic;

    //toggle mouseListener
    private boolean toggleMouseListener = false;        //toggle mouseListener

   

    // Labels for Player Stats
    private JLabel playerHpL;
    private JLabel playerGoldL;
    private JLabel playerWaveL;



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
        statsPanel.setPreferredSize(new Dimension(200,100));
        statsPanel.setOpaque(false);
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));

        //creeate labels 
        // need to import stats from GameMechanics
        playerHpL = new JLabel("Health: 0");
        playerHpL.setFont(new Font("Arial", Font.BOLD, 20));
        playerHpL.setForeground(Color.RED); // red font color for hp

        playerGoldL = new JLabel("Gold: 0");
        playerGoldL.setFont(new Font("Arial", Font.BOLD, 20));
        playerGoldL.setForeground(Color.YELLOW); // yellow font color for gold

        playerWaveL = new JLabel("Wave: 1");
        playerWaveL.setFont(new Font("Arial", Font.BOLD,20));
        playerWaveL.setForeground(Color.WHITE); // White for current Wave

        //add labels to panel 
        statsPanel.add(playerHpL);
        statsPanel.add(playerGoldL);    
        statsPanel.add(playerWaveL);

        //locate panel to the north
        this.add(statsPanel, BorderLayout.WEST);
    

        // --- Side panel for tower selection ---

        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.setOpaque(false);


        JPanel towerMenu = new JPanel();
        towerMenu.setPreferredSize(new Dimension(300, 600));
        towerMenu.setLayout(new GridLayout(6, 1)); //6 rows 1 colum 
        towerMenu.setOpaque(false);

        JButton toggleSidePanel = new JButton("<");
        toggleSidePanel.setFocusPainted(false);
        toggleSidePanel.setPreferredSize(new Dimension(30,600));

        toggleSidePanel.addActionListener(e -> { 
            boolean isVisible = towerMenu.isVisible();
            towerMenu.setVisible(!isVisible);

            if (!isVisible) { 
                toggleSidePanel.setText("<");
            } else {
                toggleSidePanel.setText(">");
            }
            this.revalidate();        
        });


        // Define your tower IDs 
        String[] towerIDs = {
                "basic_tower",
                "sniper_tower",
                "duck_tower",
                "archer_tower",
                "ice_tower",
                "tesla_tower"
        };

        // need to create seperate string for label name

        String[] towerName = {
                "Basic Tower 1800",
                "Sniper Tower 3400",
                "Super Duck 5200",
                "archer_tower",
                "ice_tower",
                "tesla_tower"
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

        // uncoment this to activate button for toggle Side panel
        //sidePanel.add(toggleSidePanel,BorderLayout.WEST);
        sidePanel.add(towerMenu, BorderLayout.CENTER);
        this.add(sidePanel, BorderLayout.EAST);

        // Mouseclick to place tower
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //just return the click
                mechanic.placeTower(e.getPoint());
                mechanic.render();
                //toggle mouseListener via variable
                if (toggleMouseListener == true){
                    System.out.println("waypoints.add(new Point("+e.getX()+","+e.getY()+"));");
                }
            }
        });
    }

    
    // helper function to update stats
    public void updatePlayerStats(int health, int gold,int wave) {
        if (playerHpL != null && playerGoldL != null && playerWaveL != null) {
            playerHpL.setText("Health: " + health);
            playerGoldL.setText("Gold: " + gold);
            playerWaveL.setText("Wave: "+ wave);
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
            BufferedImage towerImg = Resource.getResource(tower.resID);    // asks for the Tower image

            if (towerImg != null) {
                // Subtract 32 from X and Y to center the 64x64 image on the click
                int drawX = tower.pos.x - 32;
                int drawY = tower.pos.y - 32;

                g.drawImage(towerImg, drawX, drawY, 64, 64, null);
            } else {
                System.out.println("Can't place Tower");
            }
        }




        
        // import all croco type Images

        if (mechanic.getCrocos() != null) {
            BufferedImage basicCrocoImg= Resource.getResource("basic_croco");
            BufferedImage speedyCrocoImg = Resource.getResource("speedy_croco");
            BufferedImage midCrocoImg = Resource.getResource("mid_croco");
            BufferedImage fatCrocoImg = Resource.getResource("fat_croco");


            
            // --- DRAW THE CROCODILES ---

            for (Croco croco : mechanic.getCrocos()) {

                // -- Insert the types of Crocos so they get rendered with their own game texture --

                if (croco.getCrocoType().equals("basic_croco")){
                    g.drawImage(basicCrocoImg,(int)croco.getX()-32,(int)croco.getY()-32, 64,64,null);
                } 
                
                else if (croco.getCrocoType().equals("speedy_croco")){
                    g.drawImage(speedyCrocoImg,(int)croco.getX()-32,(int)croco.getY()-32, 64,64,null);
                } 
                
                else if (croco.getCrocoType().equals("mid_croco")){
                    g.drawImage(midCrocoImg,(int)croco.getX()-32,(int)croco.getY()-32, 64,64,null);
                } 

                else if (croco.getCrocoType().equals("fat_croco")){
                    g.drawImage(fatCrocoImg,(int)croco.getX()-32,(int)croco.getY()-32, 64,64,null);
                } 
            }
        }


        // --- DRAW THE PROJECTILES ---
        g.setColor(Color.BLACK); // Make them black so they are easy to see
        if (mechanic.getProjectiles() != null) {
            // We use a safe copy to prevent ConcurrentModificationExceptions while drawing
            ArrayList<Projectile> safeProjectiles = new ArrayList<>(mechanic.getProjectiles());
            for (Projectile p : safeProjectiles) {
                // Draw a small 6x6 rectangle at the projectile's X and Y
                g.fillRect((int)p.getX() - 3, (int)p.getY() - 3, 6, 6);
            }
        }

        if (mechanic.returnGameOver()){
            g.setColor((new Color(0,0,0,150)));
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial",Font.BOLD,50));
            g.drawString("Game Over!",250,300);
        }


    }
}