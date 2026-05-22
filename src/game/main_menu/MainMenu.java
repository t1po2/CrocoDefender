package game.main_menu;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * @author Nguyen Viet Hung 
 * 
 * Main menu of the game.
 * Create options to start a game, choose a map or quit the game
 */





public class MainMenu extends JFrame {

    private static String mapName;
    private static JLabel scoreLabel;
    
    public MainMenu() {

        //set up window properties
        setTitle("Main Menu (Croco Defender)");
        setSize(new Dimension(400,350));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //centers weindow to screen 
        setResizable(false);



        // Create panel to hold Buttons
        JPanel mainMenu = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10,10, 10, 10); //padding between btns
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;


        JButton startButton = new JButton("Start Game");
        JButton exitButton = new JButton("Exit Game");
        JButton mapSelectButton = new JButton("Map Selector");


        startButton.addActionListener(e ->{
            if (mapName != null){
                this.dispose();
                Launcher.startGame(mapName);
               
            } else { 
                new MapSelector(); // if no map is selected then open map selctor
            }
        });
        gbc.gridy = 1;
        mainMenu.add(startButton, gbc);

        mapSelectButton.addActionListener(e->{
            new MapSelector();
        });
        gbc.gridy = 2;
        mainMenu.add(mapSelectButton,gbc);


        exitButton.addActionListener(e ->{
            System.exit(0);
        });
        gbc.gridy = 3 ;
        mainMenu.add(exitButton,gbc);

        scoreLabel = new JLabel("Score",SwingConstants.CENTER);

        gbc.gridy = 4;
        mainMenu.add(scoreLabel,gbc);
        add(mainMenu);
        setVisible(true);


    }

    // Setters for select map 

    public static void setMap(String name){
        mapName = name;
    }

    //getters for the launcher

    public static String getMap(){
        return mapName;
    }

}
