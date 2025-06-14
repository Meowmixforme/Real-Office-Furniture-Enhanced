package ica2;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

/**
 *
 * @author v8255920
 */
public class GUI extends JPanel implements MouseListener {

    private static final HashMap<String, Integer> woodTypeMap = new HashMap<>();

    static {
        woodTypeMap.put("Oak", 0);
        woodTypeMap.put("Walnut", 1);
    }

    private static final HashMap<String, Integer> baseTypeMap = new HashMap<>();

    static {
        baseTypeMap.put("Wooden", 0);
        baseTypeMap.put("Chrome", 1);
    }

    private ArrayList<FurnitureItem> FurnitureList = new ArrayList<FurnitureItem>();
    private DraggableFurnitureLabel[] items;

    private JPanel buttonsPNL, itemsPNL;
    private JButton addChairBTN, addTableBTN, addDeskBTN, totalPriceBTN, summaryBTN, clearAllBTN, saveBTN, loadBTN;

    private int noElements = 0;
    
    // Add diagram panel reference
    private FurnitureDiagramPanel diagramPanel;
    
    // Command manager for undo/redo
    private CommandManager cmdManager = new CommandManager();

    /**
     * Class constructor
     *
     */
    public GUI() {

        /**
         * Initialise Panels
         */
        itemsPNL = new JPanel();
        buttonsPNL = new JPanel();

        itemsPNL.setPreferredSize(new Dimension(400, 400));
        itemsPNL.setBackground(Color.ORANGE);

        /**
         * Construct containerPNL
         */
        this.setLayout(new BorderLayout());
        this.setBackground(Color.magenta);
        this.setSize(740, 660);
        this.setVisible(true);
        this.add(buttonsPNL, BorderLayout.WEST);
        this.add(itemsPNL, BorderLayout.CENTER);

        /**
         * Construct itemsPNL
         */
        itemsPNL.setVisible(true);
        // itemsPNL.setBackground(Color.WHITE);
        itemsPNL.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridLayout itemLayout = new GridLayout(3, 3);
        itemsPNL.setLayout(itemLayout);

        /**
         * Construct buttonsPNL
         */
        buttonsPNL.setVisible(true);
        buttonsPNL.setFont(new Font("Segoe UI", Font.BOLD, 14));
        buttonsPNL.setBackground(Color.orange);
        buttonsPNL.setPreferredSize(new Dimension(150, 660));
        GridLayout buttonLayout = new GridLayout(8, 1);
        buttonLayout.setVgap(10);
        buttonsPNL.setOpaque(true);
        buttonsPNL.setLayout(buttonLayout);

        JButton[] buttons = new JButton[8];
        buttons[0] = addChairBTN = createStyledButton("ADD CHAIR");
        buttons[1] = addTableBTN = createStyledButton("ADD TABLE");
        buttons[2] = addDeskBTN = createStyledButton("ADD DESK");
        buttons[3] = totalPriceBTN = createStyledButton("TOTAL PRICE");
        buttons[4] = summaryBTN = createStyledButton("SUMMARY");
        buttons[5] = clearAllBTN = createStyledButton("CLEAR ALL");
        buttons[6] = saveBTN = createStyledButton("SAVE");
        buttons[7] = loadBTN = createStyledButton("LOAD");

        for (JButton b : buttons) {
            b.setSize(150, 50);
            b.setVisible(true);
            buttonsPNL.add(b);
        }

        // Initialize with draggable furniture labels
        items = new DraggableFurnitureLabel[9];
        for (int i = 0; i < items.length; i++) {
            items[i] = new DraggableFurnitureLabel(null);
            itemsPNL.add(items[i]);
            items[i].addMouseListener(this);
        }

        /**
         * Displays a frame that gets the details needed for a chair object.
         * <p>
         * A panel is created, displayed on a JOption pane and then the details
         * are stored in a new chair object, this object is then added to the
         * FurnitureList.
         */
        addChairBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = 0;

                String[] woodOptions = {
                    "Oak", "Walnut"
                };

                SpinnerNumberModel quantityModel = new SpinnerNumberModel(1, 1, 100, 1);

                JComboBox woodCMBO = new JComboBox(woodOptions);
                JSpinner quantitySPN = new JSpinner(quantityModel);

                JRadioButton yesBTN = new JRadioButton("Yes");
                JRadioButton noBTN = new JRadioButton("No");
                noBTN.setSelected(true); // Default selection

                ButtonGroup group = new ButtonGroup();
                group.add(yesBTN);
                group.add(noBTN);

                JPanel addChairPNL = new JPanel();
                addChairPNL.setLayout(new BoxLayout(addChairPNL, BoxLayout.Y_AXIS));

                // Note: Product ID field removed - auto-generated instead
                
                addChairPNL.add(new JLabel("Wood Type"));
                addChairPNL.add(woodCMBO);
                addChairPNL.add(Box.createVerticalStrut(15));

                addChairPNL.add(new JLabel("Armrests?"));
                addChairPNL.add(yesBTN);
                addChairPNL.add(noBTN);
                addChairPNL.add(Box.createVerticalStrut(15));

                addChairPNL.add(new JLabel("Quantity"));
                addChairPNL.add(quantitySPN);
                addChairPNL.add(Box.createVerticalStrut(15));

                result = JOptionPane.showConfirmDialog(null, addChairPNL, "Enter Chair Details", JOptionPane.DEFAULT_OPTION);

                if (result == -1 || result == 2) {
                    return; // User canceled
                }

                // Result is yes
                if (result == 0) {
                    boolean armrests = yesBTN.isSelected();

                    String woodTypeStr = (String) woodCMBO.getSelectedItem();
                    int woodType = woodTypeMap.get(woodTypeStr);
                    int quantity = (Integer) quantitySPN.getValue();

                    // Generate automatic product ID
                    String productId = ProductIDGenerator.generateID("Chair");
                    Chair newChair = new Chair(armrests, productId, woodType, quantity);
                    
                    // Show confirmation
                    JOptionPane.showMessageDialog(null, 
                        "Created Chair with ID: " + productId, 
                        "Item Created", 
                        JOptionPane.INFORMATION_MESSAGE);
                        
                    // Use command pattern for undo/redo support
                    CommandManager.AddFurnitureCommand cmd = new CommandManager.AddFurnitureCommand(FurnitureList, newChair);
                    executeCommand(cmd);
                    
                    addToList(newChair);
                }
            }
        });

        /**
         * Displays frame that gets the details needed for a table object.
         * <p>
         * A panel is created, displayed on a JOption pane and then the details
         * are stored in a new table object, this object is then added to the
         * FurnitureList.
         */
        addTableBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = 0;

                String[] woodOptions = {
                    "Oak", "Walnut"
                };
                String[] baseOptions = {
                    "Wooden", "Chrome"
                };

                SpinnerNumberModel sizeModel = new SpinnerNumberModel(50, 50, 500, 1);
                SpinnerNumberModel quantityModel = new SpinnerNumberModel(1, 1, 100, 1);

                JComboBox woodCMBO = new JComboBox(woodOptions);
                JComboBox baseCMBO = new JComboBox(baseOptions);
                JSpinner sizeSPN = new JSpinner(sizeModel);
                JSpinner quantitySPN = new JSpinner(quantityModel);

                JPanel addTablePNL = new JPanel();
                addTablePNL.setLayout(new BoxLayout(addTablePNL, BoxLayout.Y_AXIS));

                // Note: Product ID field removed - auto-generated instead
                
                addTablePNL.add(new JLabel("Wood Type"));
                addTablePNL.add(woodCMBO);
                addTablePNL.add(Box.createVerticalStrut(15));

                addTablePNL.add(new JLabel("Size (diameter)"));
                addTablePNL.add(sizeSPN);
                addTablePNL.add(Box.createVerticalStrut(15));

                addTablePNL.add(new JLabel("Base Type"));
                addTablePNL.add(baseCMBO);
                addTablePNL.add(Box.createVerticalStrut(15));

                addTablePNL.add(new JLabel("Quantity"));
                addTablePNL.add(quantitySPN);
                addTablePNL.add(Box.createVerticalStrut(15));

                result = JOptionPane.showConfirmDialog(null, addTablePNL, "Enter Table Details", JOptionPane.DEFAULT_OPTION);

                if (result == -1 || result == 2) {
                    return; // User canceled
                }

                // Result is yes
                if (result == 0) {
                    String woodTypeStr = (String) woodCMBO.getSelectedItem();
                    int woodType = woodTypeMap.get(woodTypeStr);
                    String baseTypeStr = (String) baseCMBO.getSelectedItem();
                    int baseType = baseTypeMap.get(baseTypeStr);
                    int quantity = (Integer) quantitySPN.getValue();
                    int size = (Integer) sizeSPN.getValue();

                    // Generate automatic product ID
                    String productId = ProductIDGenerator.generateID("Table");
                    Table newTable = new Table(baseType, size, productId, woodType, quantity);
                    
                    // Show confirmation
                    JOptionPane.showMessageDialog(null, 
                        "Created Table with ID: " + productId, 
                        "Item Created", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Use command pattern for undo/redo support
                    CommandManager.AddFurnitureCommand cmd = new CommandManager.AddFurnitureCommand(FurnitureList, newTable);
                    executeCommand(cmd);
                    
                    addToList(newTable);
                }
            }
        });

        /**
         * Displays frame that gets the details needed for a desk object.
         * <p>
         * A panel is created, displayed on a JOption pane and then the details
         * are stored in a new desk object, this object is then added to the
         * FurnitureList.
         */
        addDeskBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int result = 0;

                String[] drawsOptions = {
                    "1", "2", "3", "4"
                };
                String[] woodOptions = {
                    "Oak", "Walnut"
                };

                SpinnerNumberModel widthModel = new SpinnerNumberModel(80, 60, 500, 10);
                SpinnerNumberModel depthModel = new SpinnerNumberModel(80, 60, 500, 10);
                SpinnerNumberModel quantityModel = new SpinnerNumberModel(1, 1, 100, 1);

                JComboBox woodCMBO = new JComboBox(woodOptions);
                JComboBox drawsCMBO = new JComboBox(drawsOptions);
                JSpinner widthSPN = new JSpinner(widthModel);
                JSpinner depthSPN = new JSpinner(depthModel);
                JSpinner quantitySPN = new JSpinner(quantityModel);

                JPanel addDeskPNL = new JPanel();
                addDeskPNL.setLayout(new BoxLayout(addDeskPNL, BoxLayout.Y_AXIS));

                // Note: Product ID field removed - auto-generated instead

                addDeskPNL.add(new JLabel("Wood Type"));
                addDeskPNL.add(woodCMBO);
                addDeskPNL.add(Box.createVerticalStrut(15));

                addDeskPNL.add(new JLabel("Drawers"));
                addDeskPNL.add(drawsCMBO);
                addDeskPNL.add(Box.createVerticalStrut(15));

                addDeskPNL.add(new JLabel("Width"));
                addDeskPNL.add(widthSPN);
                addDeskPNL.add(Box.createVerticalStrut(15));

                addDeskPNL.add(new JLabel("Depth"));
                addDeskPNL.add(depthSPN);
                addDeskPNL.add(Box.createVerticalStrut(15));

                addDeskPNL.add(new JLabel("Quantity"));
                addDeskPNL.add(quantitySPN);
                addDeskPNL.add(Box.createVerticalStrut(15));

                result = JOptionPane.showConfirmDialog(null, addDeskPNL, "Enter Desk Details", JOptionPane.DEFAULT_OPTION);

                if (result == -1 || result == 2) {
                    return; // User canceled
                }

                // Result is yes
                if (result == 0) {
                    String woodTypeStr = (String) woodCMBO.getSelectedItem();
                    int woodType = woodTypeMap.get(woodTypeStr);
                    int drawers = Integer.parseInt((String) drawsCMBO.getSelectedItem());
                    int quantity = (Integer) quantitySPN.getValue();
                    int width = (Integer) widthSPN.getValue();
                    int depth = (Integer) depthSPN.getValue();

                    // Generate automatic product ID
                    String productId = ProductIDGenerator.generateID("Desk");
                    Desk newDesk = new Desk(productId, woodType, quantity, drawers, width, depth);
                    
                    // Show confirmation
                    JOptionPane.showMessageDialog(null, 
                        "Created Desk with ID: " + productId, 
                        "Item Created", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Use command pattern for undo/redo support
                    CommandManager.AddFurnitureCommand cmd = new CommandManager.AddFurnitureCommand(FurnitureList, newDesk);
                    executeCommand(cmd);
                    
                    addToList(newDesk);
                }
            }
        });

        /**
         * Displays a frame that shows the return value of the method sumPrice
         */
        totalPriceBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Total order price: £" + String.format("%1$,.2f", sumPrice()));
            }
        });

        /**
         * Sorts the FurnitureList by item price and displays the items in the
         * console
         */
        summaryBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog summaryDialog = new JDialog((java.awt.Frame) null, "Order Summary", true);
                summaryDialog.setPreferredSize(new Dimension(400, 400));
                
                JPanel content = new JPanel();
                content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
                content.setBackground(new Color(240, 240, 240));
                content.setVisible(true);
                
                JScrollPane sumScrollPane = new JScrollPane(content);
                summaryDialog.add(sumScrollPane, BorderLayout.CENTER);

                sumScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                sumScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                sumScrollPane.setPreferredSize(new Dimension(400, 400));

                double sum = 0;
                
                StringBuilder summaryMessage = new StringBuilder();
                
                summaryMessage.append("ORDER SUMMARY\n\n");
                
                // Sort by ID as specified in the compareTo method
                FurnitureList.sort((FurnitureItem a, FurnitureItem b) -> a.getIdNum().compareTo(b.getIdNum()));
                
                for(FurnitureItem fi : FurnitureList) {
                    double itemPrice = fi.calcPrice();
                    sum += itemPrice;

                    summaryMessage.append("Item Type: ");
                    summaryMessage.append(fi.getClass().getSimpleName());
                    summaryMessage.append("\n");
                    summaryMessage.append("ID: ");
                    summaryMessage.append(fi.getIdNum());
                    summaryMessage.append("\n");
                    summaryMessage.append("Wood: ");
                    summaryMessage.append(fi.getTypeOfWood());
                    summaryMessage.append("\n");
                    summaryMessage.append("Quantity: ");
                    summaryMessage.append(fi.getQuantity());
                    summaryMessage.append("\n");
                    summaryMessage.append(String.format("Item Price: £%.2f", itemPrice));
                    summaryMessage.append("\n\n");
                }
                
                summaryMessage.append(String.format("TOTAL PRICE: £%.2f", sum));

                JTextArea sumTextArea = new JTextArea(summaryMessage.toString());
                sumTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
                sumTextArea.setEditable(false);
                sumTextArea.setColumns(40);
                sumTextArea.setBackground(null);
                sumTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                content.add(sumTextArea);

                summaryDialog.revalidate();
                summaryDialog.pack();
                summaryDialog.setLocationRelativeTo(null);
                summaryDialog.setVisible(true);
            }
        });
        
        /**
         * After confirmation by the user, all items are cleared from the array
         * and GUI
         */
        clearAllBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (FurnitureList.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You cannot clear an empty list.");
                } else {
                    int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear the list?");

                    if (result == 0) {
                        // Create a copy of the list for undo
                        ArrayList<FurnitureItem> oldList = new ArrayList<>(FurnitureList);
                        
                        // Use command pattern for undo/redo
                        CommandManager.Command clearCommand = new CommandManager.Command() {
                            @Override
                            public void execute() {
                                FurnitureList.clear();
                                updateViews();
                            }
                            
                            @Override
                            public void undo() {
                                FurnitureList.addAll(oldList);
                                updateViews();
                            }
                        };
                        
                        executeCommand(clearCommand);
                    }
                }
            }
        });

        saveBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (FurnitureList.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "There are no items to save.");
                    return;
                }
                
                String fileName = JOptionPane.showInputDialog("Enter a file name.");
                if (fileName == null || fileName.trim().isEmpty()) {
                    return;
                }
                
                fileName = fileName + ".dat";

                try {
                    //Saving of object in a file
                    FileOutputStream file = new FileOutputStream(fileName);
                    ObjectOutputStream out = new ObjectOutputStream(file);

                    //Method for serialization of object
                    out.writeObject(FurnitureList);

                    out.close();
                    file.close();

                    JOptionPane.showMessageDialog(null, "Order saved successfully to " + fileName);
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(null, "Error saving file: " + ioe.getMessage(), 
                            "Save Error", JOptionPane.ERROR_MESSAGE);
                    ioe.printStackTrace();
                }
            }
        });

        /**
         * Loading of objects from a file
         */
        loadBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog("Enter a filename to load");
                if (fileName == null || fileName.trim().isEmpty()) {
                    return;
                }
                
                fileName = fileName + ".dat";

                try {
                    FileInputStream file = new FileInputStream(fileName);
                    ObjectInputStream out = new ObjectInputStream(file);
                    
                    // Save the current list for undo
                    ArrayList<FurnitureItem> oldList = new ArrayList<>(FurnitureList);
                    
                    // Load the new list
                    @SuppressWarnings("unchecked")
                    ArrayList<FurnitureItem> loadedList = (ArrayList<FurnitureItem>) out.readObject();
                    
                    // Use command pattern for load operation
                    CommandManager.Command loadCommand = new CommandManager.Command() {
                        @Override
                        public void execute() {
                            FurnitureList.clear();
                            FurnitureList.addAll(loadedList);
                            updateViews();
                        }
                        
                        @Override
                        public void undo() {
                            FurnitureList.clear();
                            FurnitureList.addAll(oldList);
                            updateViews();
                        }
                    };
                    
                    executeCommand(loadCommand);
                    
                    out.close();
                    file.close();
                    
                    // Update the ProductIDGenerator with the highest ID seen
                    updateProductIDGeneratorFromLoadedData(loadedList);
                    
                    JOptionPane.showMessageDialog(null, "Loaded " + FurnitureList.size() + " furniture items.");
                    
                } catch (FileNotFoundException fnfe) {
                    JOptionPane.showMessageDialog(null, "File not found: " + fileName, 
                            "Load Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(null, "Error loading file: " + ioe.getMessage(), 
                            "Load Error", JOptionPane.ERROR_MESSAGE);
                    ioe.printStackTrace();
                } catch (ClassNotFoundException c) {
                    JOptionPane.showMessageDialog(null, "Error loading data: Class not found", 
                            "Load Error", JOptionPane.ERROR_MESSAGE);
                    c.printStackTrace();
                }
            }
        });
    }
    
    /**
     * Update the product ID generator based on loaded data to ensure
     * new IDs don't conflict with loaded ones
     * @param loadedItems List of loaded furniture items
     */
    private void updateProductIDGeneratorFromLoadedData(ArrayList<FurnitureItem> loadedItems) {
        // Extract the highest ID for each type and update the generator
        int highestChairId = 1000;
        int highestTableId = 2000;
        int highestDeskId = 3000;
        
        for (FurnitureItem item : loadedItems) {
            String id = item.getIdNum();
            try {
                if (item instanceof Chair && id.startsWith("C")) {
                    int num = Integer.parseInt(id.substring(1));
                    highestChairId = Math.max(highestChairId, num);
                } else if (item instanceof Table && id.startsWith("T")) {
                    int num = Integer.parseInt(id.substring(1));
                    highestTableId = Math.max(highestTableId, num);
                } else if (item instanceof Desk && id.startsWith("D")) {
                    int num = Integer.parseInt(id.substring(1));
                    highestDeskId = Math.max(highestDeskId, num);
                }
            } catch (NumberFormatException e) {
                // Skip invalid IDs
            }
        }
        
        // Update the generator with highest IDs + 1
        ProductIDGenerator.setCounter("Chair", highestChairId + 1);
        ProductIDGenerator.setCounter("Table", highestTableId + 1);
        ProductIDGenerator.setCounter("Desk", highestDeskId + 1);
    }

    /**
     * Calculate the sum of all furniture prices
     * @return Total price as double
     */
    private double sumPrice() {
        double total = 0;
        for (FurnitureItem item : FurnitureList) {
            total += item.calcPrice();
        }
        return total;
    }
    
    /**
     * Add a furniture item to the list and update views
     * @param item The furniture item to add
     */
    private void addToList(FurnitureItem item) {
        if (noElements < items.length) {
            // Replace the label with a draggable label containing the furniture item
            items[noElements] = new DraggableFurnitureLabel(item);
            itemsPNL.remove(noElements);
            itemsPNL.add(items[noElements], noElements);
            items[noElements].addMouseListener(this);
            noElements++;
        }
        
        // Update the display
        updateViews();
    }
    
    /**
     * Set the diagram panel reference
     * @param diagramPanel The diagram panel to connect to this GUI
     */
    public void setDiagramPanel(FurnitureDiagramPanel diagramPanel) {
        this.diagramPanel = diagramPanel;
        // Update diagram with current items
        if (diagramPanel != null && !FurnitureList.isEmpty()) {
            diagramPanel.setFurnitureItems(FurnitureList);
        }
    }
    
    /**
     * Get the current furniture list
     * @return The list of furniture items
     */
    public ArrayList<FurnitureItem> getFurnitureList() {
        return FurnitureList;
    }
    
    /**
     * Get the command manager for undo/redo operations
     * @return The command manager
     */
    public CommandManager getCommandManager() {
        return cmdManager;
    }
    
    /**
     * Add command execution to the history
     * @param command The command to execute
     */
    public void executeCommand(CommandManager.Command command) {
        if (cmdManager != null) {
            cmdManager.executeCommand(command);
        } else {
            // Fallback if command manager is null
            command.execute();
        }
    }
    
    /**
     * Update all views (icons and diagram)
     * This method is public to allow external components to trigger updates
     */
    public void updateViews() {
        // Clear itemsPNL and re-add items
        itemsPNL.removeAll();
        
        // Re-add icons from the list
        noElements = 0;
        for (int i = 0; i < items.length; i++) {
            FurnitureItem item = (i < FurnitureList.size()) ? FurnitureList.get(i) : null;
            
            // Create draggable label for the item
            items[i] = new DraggableFurnitureLabel(item);
            items[i].addMouseListener(this);
            itemsPNL.add(items[i]);
            
            if (item != null) {
                noElements++;
            }
        }
        
        // Update diagram panel if available
        if (diagramPanel != null) {
            diagramPanel.setFurnitureItems(FurnitureList);
        }
        
        // Refresh the display
        itemsPNL.revalidate();
        itemsPNL.repaint();
    }
    
    /**
     * Create a styled button with hover effects
     * @param text Button text
     * @return Styled JButton
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(new Color(33, 33, 33));
        button.setBackground(new Color(255, 255, 153));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0), 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)));
        button.setFocusPainted(false);
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 240, 102));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 255, 153));
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        return button;
    }

    // MouseListener implementations - can be expanded for furniture interaction
    @Override
    public void mouseClicked(MouseEvent e) {
        // Handle mouse clicks on furniture items
        for (int i = 0; i < items.length; i++) {
            if (e.getSource() == items[i]) {
                FurnitureItem item = null;
                if (items[i] instanceof DraggableFurnitureLabel) {
                    item = ((DraggableFurnitureLabel)items[i]).getFurnitureItem();
                }
                
                if (item != null) {
                    // Only show details if the label has an item
                    showFurnitureDetails(i);
                    break;
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Not used but required by interface
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Not used but required by interface
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Add hover effect for furniture items
        for (DraggableFurnitureLabel item : items) {
            if (e.getSource() == item && item.getFurnitureItem() != null) {
                item.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                item.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Remove hover effect
        for (DraggableFurnitureLabel item : items) {
            if (e.getSource() == item) {
                item.setBorder(null);
                item.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
    
    /**
     * Show details for a specific furniture item
     * @param index Index of the furniture item in the display
     */
    private void showFurnitureDetails(int index) {
        if (index >= FurnitureList.size()) {
            return;
        }
        
        FurnitureItem item = FurnitureList.get(index);
        
        // Build details message based on furniture type
        StringBuilder details = new StringBuilder();
        details.append("FURNITURE DETAILS\n\n");
        details.append("Type: ").append(item.getClass().getSimpleName()).append("\n");
        details.append("ID: ").append(item.getIdNum()).append("\n");
        details.append("Wood Type: ").append(item.getTypeOfWood()).append("\n");
        details.append("Quantity: ").append(item.getQuantity()).append("\n");
        
        if (item instanceof Chair) {
            Chair chair = (Chair) item;
            details.append("Armrests: ").append(chair.getArmrests() ? "Yes" : "No").append("\n");
            details.append("Units: ").append(chair.getNumOfUnits()).append("\n");
        } else if (item instanceof Table) {
            Table table = (Table) item;
            details.append("Diameter: ").append(table.getDiameter()).append("\n");
            details.append("Base Type: ").append(table.getBaseChoice()).append("\n");
        } else if (item instanceof Desk) {
            Desk desk = (Desk) item;
            details.append("Width: ").append(desk.getWidth()).append("\n");
            details.append("Depth: ").append(desk.getDepth()).append("\n");
            details.append("Height: ").append(desk.getHeight()).append("\n");
            details.append("Drawers: ").append(desk.getNumOfDrawers()).append("\n");
        }
        
        details.append("\nItem Price: £").append(String.format("%.2f", item.calcPrice()));
        
        // Show details dialog
        JOptionPane.showMessageDialog(
                this, 
                details.toString(),
                item.getClass().getSimpleName() + " Details",
                JOptionPane.INFORMATION_MESSAGE,
                item.getImage());
        
        // Add options to remove or edit the item
        String[] options = {"Edit", "Remove", "Cancel"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "What would you like to do with this item?",
                "Item Actions",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]
        );
        
        if (choice == 1) {
            // Remove the item
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to remove this item?",
                    "Confirm Removal",
                    JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                // Use command pattern for undo/redo
                CommandManager.RemoveFurnitureCommand cmd = 
                        new CommandManager.RemoveFurnitureCommand(FurnitureList, item, index);
                executeCommand(cmd);
            }
        } else if (choice == 0) {
            // Edit functionality would go here
            JOptionPane.showMessageDialog(
                    this,
                    "Edit functionality would be implemented here.\n" +
                    "You would be able to modify properties of the furniture item.",
                    "Edit Item",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}