package game.main_menu;

import java.awt.*;
import java.util.ArrayList;

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
        loadAndDisplayHighscores();
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

    //Helper method to display highscore 

    private void loadAndDisplayHighscores() {
        // HighscoreManager liest automatisch die JSON-Datei und sortiert sie
        game.HighScoreManager hm = new game.HighScoreManager();
        ArrayList<game.HighScoreEntry> scores = hm.getScores();

        // creatse HTML String little nice header for the scoreboard
        StringBuilder sb = new StringBuilder();
        sb.append("<html><center>");
        sb.append("<br><b>--- Top 5 Croco Hunters ---</b><br>");

        // top5 scores cause of window size is limited 
        int limit = Math.min(scores.size(), 5); 

        if (scores.isEmpty()) {
            sb.append("<i>No Heros were born yet</i><br>");
        } else {
            for (int i = 0; i < limit; i++) {
                game.HighScoreEntry entry = scores.get(i);
                sb.append(i + 1).append(". Player: ").append(entry.getName())
                  .append(" - Reached Wave ").append(entry.getWave()).append("<br>");
            }
        }
        sb.append("</center></html>");
        scoreLabel.setText(sb.toString());
    }

}
