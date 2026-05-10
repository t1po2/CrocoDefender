package game;
import javax.swing.*;

public class Window {
    private JFrame frame;
    private GamePanel gamePanel;

    public Window(GameMechanic mechanic){
        frame = new JFrame("Croco Defender");
        frame.setSize(1115,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create game panel with the mechanic
        gamePanel = new GamePanel(mechanic);
        frame.add(gamePanel);
        
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}