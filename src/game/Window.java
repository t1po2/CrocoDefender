package game;
import javax.swing.*;

public class Window {
    private JFrame frame;
    private GamePanel gamePanel;

    public Window(GameMechanic mechanic){
        JFrame frame = new JFrame("Croco Defender");
        frame.setSize(900,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create game panel with the mechanic
        gamePanel = new GamePanel(mechanic);
        frame.add(gamePanel);
        
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}