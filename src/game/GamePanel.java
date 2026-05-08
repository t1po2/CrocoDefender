package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private BufferedImage map;
    private GameMechanic mechanic;

    public GamePanel(GameMechanic mechanics) {
        this.mechanic = mechanics;

        // GamePanel Setup
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(800, 600));

        // load in the map via mapID
        map = Resource.getResource("test_map");

        // Side panel for tower selection
        JPanel towerMenu = new JPanel();
        towerMenu.setPreferredSize(new Dimension(100, 600));
        towerMenu.setLayout(new GridLayout(6, 1));
        towerMenu.setOpaque(false);

        // Add 6 buttons
        for (int i = 1; i <= 6; i++) {
            JButton btn = new JButton("Tower " + i);
            btn.setFocusPainted(false);

            final int towerIndex = i;
            btn.addActionListener(e -> {
                mechanic.setSelectedTower("basic_tower");
            });

            towerMenu.add(btn);
        }
        this.add(towerMenu, BorderLayout.EAST);

        // Mouseclick to place tower
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mechanic.attemptPlacement(e.getPoint());
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background map first
        g.drawImage(map, 0, 0, getWidth(), getHeight(), this);

        // Draw every tower from the logic list
        for (GameMechanic.TowerData tower : mechanic.getPlacedTowers()) {
            BufferedImage towerImg = Resource.getResource(tower.resID);

            if (towerImg != null) {
                // Subtract 32 from X and Y to center the 64x64 image on the click
                int drawX = tower.pos.x - 32;
                int drawY = tower.pos.y - 32;

                g.drawImage(towerImg, drawX, drawY, 64, 64, null);
            } else {
                System.out.println("Can't place Tower");
            }
        }
    }
}