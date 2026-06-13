package game;
import javax.swing.*;



/**
 * Main game window container for the Croco Defender tower defense game.
 *
 * <p>This class creates and manages the application window that contains:
 * <ul>
 *   <li>The primary game display (GamePanel)</li>
 *   <li>The upgrade interface (UpgradePanel)</li>
 * </ul>
 *
 * <p>The window uses a layered pane system to properly position UI elements
 * and ensure correct rendering order. The game panel occupies the main layer,
 * while the upgrade panel floats above it in the palette layer.
 *
 * @see GamePanel
 * @see UpgradePanel
 * @see GameMechanic
 */
public class Window {
    /** The main application window frame */
    private JFrame frame;

    /** The primary game visualization panel */
    private GamePanel gamePanel;



    /**
     * Constructs the main game window with all UI components.
     *
     * <p>This method:
     * <ul>
     *   <li>Creates the main application frame</li>
     *   <li>Sets window properties (title, size, close operation)</li>
     *   <li>Creates and configures a JLayeredPane for UI organization</li>
     *   <li>Initializes and positions the GamePanel in the default layer</li>
     *   <li>Initializes and positions the UpgradePanel in the palette layer</li>
     *   <li>Establishes connections between UI components</li>
     *   <li>Makes the window visible and centers it on screen</li>
     * </ul>
     *
     * @param mechanic The GameMechanic instance that controls game logic
     * @see GamePanel#GamePanel(GameMechanic)
     * @see UpgradePanel#setGameMechanic(GameMechanic)
     */
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