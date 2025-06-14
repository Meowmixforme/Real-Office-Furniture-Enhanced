/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ica2;

import java.io.FileInputStream;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author v8255
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
    private JLabel[] items;

    private JPanel buttonsPNL, itemsPNL;
    private JButton addChairBTN, addTableBTN, addDeskBTN, totalPriceBTN, summaryBTN, clearAllBTN, saveBTN, loadBTN;
    private JLabel label1, label2, label3, label4, label5, label6, label7, label8, label9;

    private int noElements = 0;

    /**
     * Class constructor
     *
     */
    GUI() {

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
        buttonsPNL.setFont(new Font("Monospaced", Font.BOLD, 18));
        buttonsPNL.setBackground(Color.orange);
        buttonsPNL.setPreferredSize(new Dimension(150, 660));
        GridLayout buttonLayout = new GridLayout(8, 1);
        buttonLayout.setVgap(10);
        buttonsPNL.setOpaque(true);
        buttonsPNL.setLayout(buttonLayout);

        JButton[] buttons = new JButton[8];
        buttons[0] = addChairBTN = new JButton("ADD CHAIR");
        buttons[1] = addTableBTN = new JButton("ADD TABLE");
        buttons[2] = addDeskBTN = new JButton("ADD DESK");
        buttons[3] = totalPriceBTN = new JButton("TOTAL PRICE");
        buttons[4] = summaryBTN = new JButton("SUMMARY");
        buttons[5] = clearAllBTN = new JButton("CLEAR ALL");
        buttons[6] = saveBTN = new JButton("SAVE");
        buttons[7] = loadBTN = new JButton("LOAD");

        for (JButton b : buttons) {

            b.setSize(150, 50);
            b.setVisible(true);
            b.setBackground(Color.yellow);
            buttonsPNL.add(b);
        }

        items = new JLabel[9];
        items[0] = label1 = new JLabel();
        items[1] = label2 = new JLabel();
        items[2] = label3 = new JLabel();
        items[3] = label4 = new JLabel();
        items[4] = label5 = new JLabel();
        items[5] = label6 = new JLabel();
        items[6] = label7 = new JLabel();
        items[7] = label8 = new JLabel();
        items[8] = label9 = new JLabel();

        for (JLabel l : items) {

            itemsPNL.add(l);
            l.addMouseListener(this);
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

                String[] woodOptions
                        = {
                            "Oak", "Walnut"
                        };

                SpinnerNumberModel quantityModel = new SpinnerNumberModel(1, 1, 100, 1);

                JTextField productIdTXT = new JTextField(20);
                JComboBox woodCMBO = new JComboBox(woodOptions);
                JSpinner quantitySPN = new JSpinner(quantityModel);

                JRadioButton yesBTN = new JRadioButton("Yes");
                JRadioButton noBTN = new JRadioButton("No");

                ButtonGroup group = new ButtonGroup();
                group.add(yesBTN);
                group.add(noBTN);

                JPanel addChairPNL = new JPanel();
                addChairPNL.setLayout(new BoxLayout(addChairPNL, BoxLayout.Y_AXIS));

                addChairPNL.add(new JLabel("Product ID"));
                addChairPNL.add(productIdTXT);
                addChairPNL.add(Box.createVerticalStrut(15));

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

                do {
                    result = JOptionPane.showConfirmDialog(null, addChairPNL, "Enter Chair Details", JOptionPane.DEFAULT_OPTION);

                    if (result == -1 || result == 2) {

                        return;
                    }

                } while (productIdTXT.getText().equals(""));

                //result = yes
                if (result == 0) {
                    boolean armrests = false;

                    if (yesBTN.isSelected()) {
                        armrests = true;
                    }

                    ImageIcon image = new ImageIcon("images/icons/chair.PNG");
                    String woodTypeStr = (String) woodCMBO.getSelectedItem();
                    System.out.println("woodTypeStr: " + woodTypeStr);
                    int woodType = woodTypeMap.get(woodTypeStr); //Integer.parseInt(woodTypeStr);
                    int quantity = (Integer) quantitySPN.getValue();

                    Chair newChair = new Chair(armrests, productIdTXT.getText(), woodType, quantity);
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

                String[] woodOptions
                        = {
                            "Oak", "Walnut"
                        };
                String[] baseOptions
                        = {
                            "Wooden", "Chrome"
                        };

                SpinnerNumberModel sizeModel = new SpinnerNumberModel(50, 50, 500, 1);
                SpinnerNumberModel quantityModel = new SpinnerNumberModel(1, 1, 100, 1);

                JTextField productIdTXT = new JTextField(20);
                JComboBox woodCMBO = new JComboBox(woodOptions);
                JComboBox baseCMBO = new JComboBox(baseOptions);
                JSpinner sizeSPN = new JSpinner(sizeModel);
                JSpinner quantitySPN = new JSpinner(quantityModel);

                JPanel addTablePNL = new JPanel();
                addTablePNL.setLayout(new BoxLayout(addTablePNL, BoxLayout.Y_AXIS));

                addTablePNL.add(new JLabel("Product ID"));
                addTablePNL.add(productIdTXT);
                addTablePNL.add(Box.createVerticalStrut(15));

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

                do {
                    result = JOptionPane.showConfirmDialog(null, addTablePNL, "Enter Table Details", JOptionPane.DEFAULT_OPTION);

                    if (result == -1 || result == 2) {
                        return;
                    }
                } while (productIdTXT.getText().equals(""));

                //result == yes
                if (result == 0) {
                    String woodTypeStr = (String) woodCMBO.getSelectedItem();
                    int woodType = woodTypeMap.get(woodTypeStr);
                    String baseTypeStr = (String) baseCMBO.getSelectedItem();
                    int baseType = baseTypeMap.get(baseTypeStr);
                    int quantity = (Integer) quantitySPN.getValue();
                    int size = (Integer) sizeSPN.getValue();

                    ImageIcon tableIMG = new ImageIcon("images/icons/table.PNG");

                    Table newTable = new Table(baseType, size, productIdTXT.getText(), woodType, quantity);
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

                String[] drawsOptions
                        = {
                            "1", "2", "3", "4"
                        };
                String[] woodOptions
                        = {
                            "Oak", "Walnut"
                        };

                SpinnerNumberModel widthModel = new SpinnerNumberModel(0, 0, 500, 1);
                SpinnerNumberModel depthModel = new SpinnerNumberModel(0, 0, 500, 1);
                SpinnerNumberModel quantityModel = new SpinnerNumberModel(1, 1, 100, 1);

                JTextField productIdTXT = new JTextField(20);
                JComboBox woodCMBO = new JComboBox(woodOptions);
                JComboBox drawsCMBO = new JComboBox(drawsOptions);
                JSpinner widthSPN = new JSpinner(widthModel);
                JSpinner depthSPN = new JSpinner(depthModel);
                JSpinner quantitySPN = new JSpinner(quantityModel);

                JPanel addDeskPNL = new JPanel();
                addDeskPNL.setLayout(new BoxLayout(addDeskPNL, BoxLayout.Y_AXIS));

                addDeskPNL.add(new JLabel("Product ID"));
                addDeskPNL.add(productIdTXT);
                addDeskPNL.add(Box.createVerticalStrut(15));

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

                do {
                    result = JOptionPane.showConfirmDialog(null, addDeskPNL, "Enter Table Details", JOptionPane.DEFAULT_OPTION);

                    if (result == -1 || result == 2) {
                        return;
                    }

                } while (productIdTXT.getText().equals(""));

                //result == yes
                if (result == 0) {
                    ImageIcon deskIMG = new ImageIcon("images/icons/desk.PNG");
                    String woodTypeStr = (String) woodCMBO.getSelectedItem();
                    int woodType = woodTypeMap.get(woodTypeStr);
                    int drawers = Integer.parseInt((String) drawsCMBO.getSelectedItem());
                    int quantity = (Integer) quantitySPN.getValue();
                    int width = (Integer) widthSPN.getValue();
                    int depth = (Integer) depthSPN.getValue();

                    Desk newDesk = new Desk(productIdTXT.getText(), woodType, quantity, drawers, width, depth);
                    addToList(newDesk);

                }
            }
        });

        /**
         * Displays a frame that shows the return value of the method sumPrice
         */
        totalPriceBTN.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Toalat order price: £" + String.format("%1$,.2f", sumPrice()));
            }
        });

        /**
         * Sorts the FurnitureList by item price and displays the items in the
         * console
         */
        summaryBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                FurnitureItem[] order = new FurnitureItem[FurnitureList.size()];
//
//                for (int i = 0; i < FurnitureList.size(); i++) {
//
//                    //System.out.println("price from arrayList: " + "\n" + String.valueOf(FurnitureList.get(i)));
//                    order[i] = FurnitureList.get(i);
//                }

                //Arrays.sort(order);
                
                
                JDialog summaryDialog = new JDialog((java.awt.Frame) null, true);
                summaryDialog.setPreferredSize(new Dimension(300, 300));
                
                JPanel content = new JPanel();
                content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
                content.setBackground(Color.yellow);
                content.setVisible(true);
                
                JScrollPane sumScrollPane = new JScrollPane(content);
                summaryDialog.add(sumScrollPane, BorderLayout.CENTER);

                //
                sumScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                sumScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                sumScrollPane.setPreferredSize(new Dimension(300, 300));


                double sum = 0;
                
                StringBuilder summaryMessage = new StringBuilder();
                
                summaryMessage.append("Summary\n\n");
                
                // Use a for .. in loop
                FurnitureList.sort((FurnitureItem a, FurnitureItem b) -> a.getIdNum().compareTo(b.getIdNum()));
                
                //for (int i = 0; i < FurnitureList.size(); i++) {
                for(FurnitureItem fi :  FurnitureList) {

                    sum = sum + fi.calcPrice();

                    summaryMessage.append("Furniture Type: ");
                    summaryMessage.append(fi.getClass().getSimpleName());
                    summaryMessage.append("\n");
                    summaryMessage.append(String.format("Furniture Price: %.2f", fi.calcPrice()));
                    summaryMessage.append("\n\n");
                }
                
                    //JDialog.setDefaultLookAndFeelDecorated(true);
                    //summaryDialog.setTitle("Order summary");
                    //summaryDialog.setFont(new Font("Lucida",Font.PLAIN,24));
                    //summaryDialog.setLayout(new BorderLayout());
                summaryMessage.append(String.format("Total Price: %.2f", sumPrice()));

                JTextArea sumTextArea = new JTextArea(summaryMessage.toString());

                sumTextArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
                sumTextArea.setEditable(false);
                sumTextArea.setColumns(40);
//                    sumTextArea.setRows(10);
                sumTextArea.setBackground(null);
                content.add(sumTextArea);


                summaryDialog.revalidate();
                summaryDialog.pack();
                
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
                        FurnitureList.clear();

                        for (int i = 0; i < items.length; i++) {
                            if (items[i] != null) {
                                items[i].setIcon(null);
                                itemsPNL.revalidate();
                                itemsPNL.repaint();
                            }
                        }
                    }

                }
            }
        });

        saveBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog("Enter a file name.");
                fileName = fileName + ".dat";

                try {

                    //Saving of object in a file
                    FileOutputStream file = new FileOutputStream(fileName);
                    ObjectOutputStream out = new ObjectOutputStream(file);

                    //Method for serialization of object
                    out.writeObject(FurnitureList);

                    out.close();
                    file.close();

                    System.out.println("Object has been serialized");
                } catch (IOException ioe) {
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
                fileName = fileName + ".dat";

                try {
                    FileInputStream file = new FileInputStream(fileName);
                    ObjectInputStream out = new ObjectInputStream(file);
                    FurnitureList = (ArrayList) out.readObject();
                    out.close();
                    file.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    return;
                } catch (ClassNotFoundException c) {
                    System.out.println("Class not found");
                    c.printStackTrace();
                    return;
                }
                for (int i = 0; i < items.length; i++) {
                    if (items[i] != null) {
                        items[i].setIcon(null);
                        itemsPNL.revalidate();
                        itemsPNL.repaint();
                    }
                }
                for (FurnitureItem f : FurnitureList) {
                    items[noElements].setIcon(f.getImage());
                    itemsPNL.revalidate();
                    itemsPNL.repaint();
                    noElements++;
                }
            }
        });
    }

    /**
     * Add an object to the first null spot in the array list if there is space.
     *
     * @param f an object supplied by the add furniture buttons
     */
    public void addToList(FurnitureItem f) {
        if (FurnitureList.size() <= 9) {
            if (!FurnitureList.contains(null)) {
                FurnitureList.add(f);
                items[noElements].setIcon(f.getImage());
                itemsPNL.add(items[noElements]);
                itemsPNL.revalidate();
                itemsPNL.repaint();
                noElements++;
            } else {
                for (int i = 0; i < FurnitureList.size(); i++) {
                    if (FurnitureList.get(i) == null) {
                        FurnitureList.set(i, f);
                        items[i].setIcon(f.getImage());
                        itemsPNL.add(items[i]);
                        itemsPNL.revalidate();
                        itemsPNL.repaint();
                        noElements++;
                        break;
                    }
                }
            }
        } else if (FurnitureList.size() == 9 && FurnitureList.contains(null)) {
            for (int i = 0; i < FurnitureList.size(); i++) {
                if (FurnitureList.get(i) == null) {
                    FurnitureList.set(i, f);
                    items[i].setIcon(f.getImage());
                    itemsPNL.revalidate();
                    itemsPNL.repaint();
                    noElements++;
                    break;
                }
            }
        }
    }

    public double sumPrice() {
        double sum = 0;

        for (FurnitureItem fi : FurnitureList) {
            double price = fi.calcPrice();
            sum += price;
        }

        System.out.println("Sum  = " + sum);

        return sum;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            //displayInformation(JLabel source);
        }

        if (e.getButton() == MouseEvent.BUTTON2) {

        }

        if (e.getButton() == MouseEvent.BUTTON3) {

        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
