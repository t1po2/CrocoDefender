package game.main_menu;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



/**
 * Map selection interface for Croco Defender game.
 * <p>
 * Provides a graphical interface allowing players to choose between available game maps
 * before starting a new game. Extends {@link javax.swing.JFrame} to create a modal window
 * containing map selection controls.
 * </p>
 *
 * <p>
 * The class uses a {@link javax.swing.JComboBox} to present available map options
 * and provides confirmation feedback via {@link javax.swing.JOptionPane} when a
 * selection is made. Once a map is selected, the choice is communicated to the
 * {@link MainMenu} class via {@link MainMenu#setMap(String)}.
 * </p>
 *
 * @author Nguyen Viet Hung
 * @see MainMenu
 * @see MainMenu#setMap(String)
 */

public class MapSelector extends JFrame {

    private String map;

     /**
     * Constructs a new MapSelector window.
     * <p>
     * Initializes the map selection interface with the following components:
     * <ul>
     *   <li>Title label ("Select a Map")</li>
     *   <li>Dropdown combo box containing available maps</li>
     *   <li>Confirmation button to select the chosen map</li>
     * </ul>
     * </p>
     * <p>
     * The window will automatically center on screen and defaults to 600x400 pixels.
     * </p>
     */
    public MapSelector() {

        
        setTitle("Map Selector Croco Defender");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        JPanel mapSelector = new JPanel();
        add(mapSelector); // Add panel to 'this' MapSelector

        JLabel label1 = new JLabel("Select a Map");
        mapSelector.add(label1);

        String[] maps = { "swamp_map", "mt_croco"};    // String for the buttons

        final JComboBox<String> mapSelected = new JComboBox<String>(maps);       //JComboBox is a retractable pane Layout
        mapSelector.add(mapSelected);

        JButton selectButton = new JButton("Select map");

        // The corrected click event
        selectButton.addActionListener(e -> {
            map = (String) mapSelected.getSelectedItem();
            JOptionPane.showMessageDialog(mapSelector,"Map selected: "+map,"SUCESS",JOptionPane.WARNING_MESSAGE);    //parent window is mapSelector panel 
            MainMenu.setMap(map);
            this.dispose();
           
        });

        mapSelector.add(selectButton);
        setVisible(true); 
    }


     /**
     * Returns the currently selected map.
     * <p>
     * This getter provides access to the map that was most recently selected
     * via the user interface. Returns {@code null} if no selection has been made.
     * </p>
     *
     * @return the name of the selected map, or {@code null} if none selected
     */
    
    // getter for game to know which map 
    public String selectedMap(){
        return this.map;
    }


     @Override
     public String toString() {
        return "MapSelector [map=" + map + "]";
     }
    
}