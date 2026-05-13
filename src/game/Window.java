package game;
import javax.swing.*;

public class Window {
    private JFrame frame;
    private GamePanel gamePanel;

    public Window(GameMechanic mechanic){
        frame = new JFrame("Croco Defender");
        // Wir setzen die INNERE Größe des Fensters (ohne die Windows-Titelleiste)
        frame.getContentPane().setPreferredSize(new java.awt.Dimension(1101, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // --- Layer Manager ---
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new java.awt.Dimension(1101, 600));

        // first Layer game panel
        gamePanel = new GamePanel(mechanic);
        gamePanel.setBounds(0, 0, 1101, 600); // gamepanel 800 + the width of the tower menu
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER); // actual game

        // 2nd layer _ upgradepanel
        UpgradePanel upgradePanel = new UpgradePanel();
        upgradePanel.setGameMechanic(mechanic);
        // (x, y, width, height). 
        upgradePanel.setBounds(50, 500, 700, 80); 
        layeredPane.add(upgradePanel, JLayeredPane.PALETTE_LAYER); //Layer 1 above game panel

        // link both panels
        gamePanel.setUpgradePanel(upgradePanel); 
        
        // add layers to frame
        frame.add(layeredPane);
        frame.pack(); 
        
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}