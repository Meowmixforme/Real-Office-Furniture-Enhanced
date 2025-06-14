package ica2;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ModernUI {
    
    // Main color scheme
    public static final Color PRIMARY_COLOR = new Color(51, 102, 153);     // Blue
    public static final Color SECONDARY_COLOR = new Color(240, 240, 240);  // Light Gray
    public static final Color ACCENT_COLOR = new Color(255, 153, 51);      // Orange
    public static final Color TEXT_COLOR = new Color(33, 33, 33);          // Dark Gray
    public static final Color LIGHT_TEXT_COLOR = new Color(250, 250, 250); // White
    
    // Font settings
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    
    public static void applyModernStyle() {
        try {
            // Use system look and feel as base
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Customize components
            UIManager.put("Button.background", SECONDARY_COLOR);
            UIManager.put("Button.foreground", PRIMARY_COLOR);
            UIManager.put("Button.font", REGULAR_FONT);
            UIManager.put("Button.border", BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                    BorderFactory.createEmptyBorder(6, 15, 6, 15)));
            
            UIManager.put("Panel.background", SECONDARY_COLOR);
            UIManager.put("Label.font", REGULAR_FONT);
            UIManager.put("Label.foreground", TEXT_COLOR);
            
            UIManager.put("ComboBox.font", REGULAR_FONT);
            UIManager.put("TextField.font", REGULAR_FONT);
            UIManager.put("Spinner.font", REGULAR_FONT);
            
            UIManager.put("OptionPane.messageFont", REGULAR_FONT);
            UIManager.put("OptionPane.buttonFont", REGULAR_FONT);
            
        } catch (Exception e) {
            System.err.println("Failed to set UI look and feel: " + e.getMessage());
        }
    }
    
    public static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(REGULAR_FONT);
        button.setForeground(TEXT_COLOR);
        button.setBackground(SECONDARY_COLOR);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)));
        button.setFocusPainted(false);
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
                button.setForeground(LIGHT_TEXT_COLOR);
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(SECONDARY_COLOR);
                button.setForeground(TEXT_COLOR);
            }
        });
        
        return button;
    }
    
    public static JPanel createStyledPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(SECONDARY_COLOR);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        return panel;
    }
}