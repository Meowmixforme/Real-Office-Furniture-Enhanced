/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ica2;

import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author v8255920
 */
public class Table extends FurnitureItem implements Serializable {
    
    private int diameter;
    private final String[] baseType = {"Wooden","Chrome"};
    private double basePrice;
    private String baseChoice;
    
     /**
     * Default Constructor, no parameters.
     */
    public Table() {   
        super();
        this.diameter = 50;
        this.basePrice = 45.00;
        this.baseChoice = "Wooden";
        image = FixedImageLoader.getTableIcon();
    }
    
    /**
     * Constructor, also sets price of base according to choice of base.
     * @param diameter -  to set the table diameter
     * @param chooseBase - to set base choice
     * @param idNum - to be passed to super (Furniture) class
     * @param chooseWood - to be passed to super (Furniture) class
     * @param quantity - to be passed to super (Furniture) class
     */
    public Table(int chooseBase, int diameter, String idNum, int chooseWood, int quantity) {
        super(idNum, chooseWood, quantity);
        this.baseChoice = baseType[chooseBase];
        this.diameter = diameter;
        image = FixedImageLoader.getTableIcon();
        
        if (baseChoice.equals(baseType[0]))
        {
            basePrice = 45.00;
        }
        else
        {
            basePrice = 35.00;
        }    
                
    }
    
    /**
     * 
     * @return Diameter of item
     */

    public double getDiameter() {
        
        return diameter;
    }
    
    /**
     * 
     * @return Choice of base
     */

    public String getBaseChoice() {
        return baseChoice;
    }

    /**
     * 
     * @return Base price of item as a double
     */

    public double getBasePrice() {
        return basePrice;
    }

    
   

    public void setBaseType(String baseChoice) {

        this.baseChoice = baseChoice;
        
        if (baseChoice == "Wooden")
        {
            basePrice = 45.00;
        }    
        else if (baseChoice == "Chrome")
        {
            basePrice = 35.00;
        }  
        
            
    }

    
    /**
     * Used to set the diameter that user wishes to change.
     * @param diameter
     */
    public void setDiameter(int diameter) {
        
        this.diameter = diameter;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

     /**
     * Calculates price of object
     * @return Item's price as a double
     */
    
    @Override
    public double calcPrice()
    {
        
        itemPrice = (((diameter * diameter) * unitPrice) + basePrice) * quantity;
        return itemPrice;
    }
    
    /**
     * 
     * @return Description of item as a String
     */
    
    @Override
    public String toString()
    {
     String table = "TABLE" + "\n" + "Wood: " + woodChoice + "\n"  + 
             "Diameter: " + diameter + "\n" + "Base: " + baseChoice + "\n" +
             "Quantity: " + quantity;  
     return table;   
    }

    @Override
    public int compareTo(FurnitureItem o) {
        return this.getIdNum().compareTo(o.getIdNum());
    }

    
}
