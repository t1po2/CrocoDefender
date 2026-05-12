package game;
import javax.swing.*;

public class Window {
    private JFrame frame;
    private GamePanel gamePanel;

    public Window(GameMechanic mechanic){
        frame = new JFrame("Croco Defender");
        // Wir setzen die INNERE Größe des Fensters (ohne die Windows-Titelleiste)
        frame.getContentPane().setPreferredSize(new java.awt.Dimension(1115, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // --- DER EBENEN-MANAGER ---
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new java.awt.Dimension(1115, 600));

        // 1. Ebene: Das Spielfeld (Ganz unten)
        gamePanel = new GamePanel(mechanic);
        gamePanel.setBounds(0, 0, 1115, 600); // Füllt das ganze Fenster
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER); // Ebene 0

        // 2. Ebene: Das Upgrade Panel (Schwebt darüber)
        UpgradePanel upgradePanel = new UpgradePanel();
        upgradePanel.setGameMechanic(mechanic);
        // Positionierung: (x, y, breite, höhe). 
        // y = 500 bedeutet, es schwebt 100 Pixel über dem unteren Rand
        upgradePanel.setBounds(100, 500, 900, 80); 
        layeredPane.add(upgradePanel, JLayeredPane.PALETTE_LAYER); // Ebene 1 (Vordergrund)

        // Verknüpfen
        gamePanel.setUpgradePanel(upgradePanel); 
        
        // Das fertige Ebenen-Paket ins Fenster packen
        frame.add(layeredPane);
        frame.pack(); // Passt das Fenster exakt an die PreferredSize an
        
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}