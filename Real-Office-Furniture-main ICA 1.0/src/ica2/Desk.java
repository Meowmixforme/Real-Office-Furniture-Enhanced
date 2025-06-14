/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ica2;

import javax.swing.ImageIcon;

/**
 *
 * @author v8255920
 */
public class Desk extends FurnitureItem {
    
    private int width;
    private int depth;
    private int height = 80;
    private int numOfDrawers;
    private double charge = 8.50;

    /**
     * Default Constructor no parameters
     */
    
    public Desk()
    {
        super();
        width = 80;
        depth = 80;
        numOfDrawers = 2;
        image = new ImageIcon("images/icons/desk.PNG");
    }

    /**
     * 
     * @param idNum
     * @param w
     * @param d
     * @param drawers
     * @param chooseWood
     * @param quantity 
     */
    
    public Desk(String idNum, int chooseWood, int quantity, int drawers, 
            int w, int d)
    {
        super(idNum, chooseWood, quantity);
        this.numOfDrawers = drawers;
        this.width = w;
        this.depth = d;
        image = new ImageIcon("images/icons/desk.PNG");
    }

    
    
    public int getHeight() {
        return height;
    }
    
    
    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }

    public int getNumOfDrawers() {
        return numOfDrawers;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setNumOfDrawers(int numOfDrawers) {
        this.numOfDrawers = numOfDrawers;
    }

    
       
    @Override
    public double calcPrice()
       {
            itemPrice = (width * depth * unitPrice) + (numOfDrawers * charge) * quantity;
     return itemPrice;
        }

  
    
    @Override
    public String toString()
    {
        String desk = "DESK" + "\n" + "Wood: " + woodChoice + "\n"  + 
                "Width:" + width + "\n" + "Depth:" + depth + "\n" +
                "Number of drawers:" + numOfDrawers + "\n" +
                "Quantity:" + quantity;
        return desk;
    }

    @Override
    public int compareTo(FurnitureItem o) {
        return this.getIdNum().compareTo(o.getIdNum());
    }
    

}
