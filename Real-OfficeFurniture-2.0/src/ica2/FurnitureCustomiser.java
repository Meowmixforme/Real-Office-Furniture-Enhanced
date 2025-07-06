package ica2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;

public class FurnitureCustomiser {
    
    // Color options for furniture
    private static final HashMap<String, Color> colorOptions = new HashMap<>();
    static {
        colorOptions.put("Natural", new Color(210, 180, 140));
        colorOptions.put("Mahogany", new Color(103, 54, 45));
        colorOptions.put("Cherry", new Color(132, 70, 60));
        colorOptions.put("Maple", new Color(235, 217, 150));
        colorOptions.put("Ebony", new Color(53, 47, 45));
    }
    
    // Material options with price multipliers
    private static final HashMap<String, Double> materialOptions = new HashMap<>();
    static {
        materialOptions.put("Standard", 1.0);
        materialOptions.put("Premium", 1.3);
        materialOptions.put("Luxury", 1.8);
        materialOptions.put("Eco-friendly", 1.5);
    }
    
    public static JPanel createCustomizationPanel(final FurnitureItem item) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Customize"));
        
        // Color selection
        JPanel colorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        colorPanel.add(new JLabel("Color:"));
        JComboBox<String> colorCombo = new JComboBox<>(colorOptions.keySet().toArray(new String[0]));
        colorPanel.add(colorCombo);
        
        // Material selection
        JPanel materialPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        materialPanel.add(new JLabel("Material Quality:"));
        JComboBox<String> materialCombo = new JComboBox<>(materialOptions.keySet().toArray(new String[0]));
        materialPanel.add(materialCombo);
        
        // Additional options based on furniture type
        JPanel additionalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        if (item instanceof Chair) {
            additionalPanel.add(new JLabel("Adjustable Height:"));
            JCheckBox adjustableCheck = new JCheckBox();
            additionalPanel.add(adjustableCheck);
            
            additionalPanel.add(new JLabel("Wheels:"));
            JCheckBox wheelsCheck = new JCheckBox();
            additionalPanel.add(wheelsCheck);
        } 
        else if (item instanceof Desk) {
            additionalPanel.add(new JLabel("Cable Management:"));
            JCheckBox cableCheck = new JCheckBox();
            additionalPanel.add(cableCheck);
            
            additionalPanel.add(new JLabel("Adjustable Height:"));
            JCheckBox adjustableCheck = new JCheckBox();
            additionalPanel.add(adjustableCheck);
        }
        else if (item instanceof Table) {
            additionalPanel.add(new JLabel("Glass Top:"));
            JCheckBox glassCheck = new JCheckBox();
            additionalPanel.add(glassCheck);
        }
        
        // Apply button
        JButton applyBtn = new JButton("Apply Changes");
        applyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedColor = (String) colorCombo.getSelectedItem();
                String selectedMaterial = (String) materialCombo.getSelectedItem();
                
                // In a real implementation, you would update the furniture item properties here
                // For example:
                // item.setColor(selectedColor);
                // item.setMaterial(selectedMaterial);
                
                JOptionPane.showMessageDialog(panel, 
                        "Customization applied!\nColor: " + selectedColor + 
                        "\nMaterial: " + selectedMaterial,
                        "Customization", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Add everything to the main panel
        panel.add(colorPanel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(materialPanel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(additionalPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(applyBtn);
        
        return panel;
    }
}