package ica2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainDriver {

    public static void main(String[] args) {
        try {
            // Set System Look and Feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Create the main frame
            JFrame main = new JFrame("Real Office Furniture");
            main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create components
            FurnitureDiagramPanel diagramPanel = new FurnitureDiagramPanel();
            GUI guiPNL = new GUI();
            
            // Connect GUI with diagram panel
            guiPNL.setDiagramPanel(diagramPanel);
            
            // Add toolbar with undo/redo/export buttons
            JToolBar toolbar = new JToolBar();
            toolbar.setFloatable(false);
            
            // Get command manager from GUI
            CommandManager cmdManager = guiPNL.getCommandManager();
            
            // Create buttons
            JButton undoBtn = new JButton("Undo");
            undoBtn.setToolTipText("Undo last action");
            try {
                ImageIcon undoIcon = new ImageIcon(MainDriver.class.getResource("/images/undo.png"));
                if (undoIcon.getIconWidth() > 0) {
                    undoBtn.setIcon(undoIcon);
                }
            } catch (Exception e) {
                // If image not found, continue without icon
                System.out.println("Undo icon not found: " + e.getMessage());
            }
            
            JButton redoBtn = new JButton("Redo");
            redoBtn.setToolTipText("Redo last undone action");
            try {
                ImageIcon redoIcon = new ImageIcon(MainDriver.class.getResource("/images/redo.png"));
                if (redoIcon.getIconWidth() > 0) {
                    redoBtn.setIcon(redoIcon);
                }
            } catch (Exception e) {
                // If image not found, continue without icon
                System.out.println("Redo icon not found: " + e.getMessage());
            }
            
            JButton exportPDFBtn = new JButton("Export PDF");
            exportPDFBtn.setToolTipText("Export furniture list and layout to PDF");
            try {
                ImageIcon pdfIcon = new ImageIcon(MainDriver.class.getResource("/images/pdf.png"));
                if (pdfIcon.getIconWidth() > 0) {
                    exportPDFBtn.setIcon(pdfIcon);
                }
            } catch (Exception e) {
                // If image not found, continue without icon
                System.out.println("PDF icon not found: " + e.getMessage());
            }
            
            // Initially disable the buttons if they can't be used
            undoBtn.setEnabled(cmdManager.canUndo());
            redoBtn.setEnabled(cmdManager.canRedo());
            
            // Add button listeners
            undoBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (cmdManager.canUndo()) {
                        System.out.println("Performing undo operation");
                        cmdManager.undo();
                        guiPNL.updateViews();
                        
                        // Update button states
                        undoBtn.setEnabled(cmdManager.canUndo());
                        redoBtn.setEnabled(cmdManager.canRedo());
                    }
                }
            });
            
            redoBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (cmdManager.canRedo()) {
                        System.out.println("Performing redo operation");
                        cmdManager.redo();
                        guiPNL.updateViews();
                        
                        // Update button states
                        undoBtn.setEnabled(cmdManager.canUndo());
                        redoBtn.setEnabled(cmdManager.canRedo());
                    }
                }
            });
            
            exportPDFBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Call the PDF export function
                    System.out.println("Exporting PDF...");
                    PDFExporter.exportToPDF(guiPNL.getFurnitureList(), diagramPanel);
                }
            });
            
            // Add buttons to toolbar
            toolbar.add(undoBtn);
            toolbar.add(redoBtn);
            toolbar.add(Box.createHorizontalGlue()); // Push export button to right
            toolbar.add(exportPDFBtn);
            
            // Create layout
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.add(toolbar, BorderLayout.NORTH);
            
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, guiPNL, diagramPanel);
            splitPane.setResizeWeight(0.6); // 60% left, 40% right
            contentPanel.add(splitPane, BorderLayout.CENTER);
            
            main.setContentPane(contentPanel);
            main.setSize(1000, 660);
            main.setLocationRelativeTo(null); // Center on screen
            main.setVisible(true);
            
            // Add listener to update undo/redo button states when actions occur
            cmdManager.addCommandListener(new CommandManager.CommandListener() {
                @Override
                public void commandExecuted() {
                    SwingUtilities.invokeLater(() -> {
                        undoBtn.setEnabled(cmdManager.canUndo());
                        redoBtn.setEnabled(cmdManager.canRedo());
                    });
                }
            });
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error starting application: " + e.getMessage(), 
                    "Startup Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}