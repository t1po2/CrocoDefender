package game.main_menu;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class MapSelector extends JFrame {

    private String map;

    public MapSelector() {

        
        setTitle("Map Selector Croco Defender");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        JPanel mapSelector = new JPanel();
        add(mapSelector); // Add panel to 'this' MapSelector

        JLabel label1 = new JLabel("Select a Map");
        mapSelector.add(label1);

        String[] maps = { "swamp_map", "dessert_map"};    // String for the buttons

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

    // getter for game to know which map 
    public String selectedMap(){
        return this.map;
    }
}