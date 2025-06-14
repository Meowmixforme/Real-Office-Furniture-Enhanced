/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ica2;

/**
 *
 * @author v8255920
 */

import java.io.Serializable;
import javax.swing.ImageIcon;


public class Chair extends FurnitureItem implements Serializable
{
    private boolean armrests;
    private int numOfUnits;
    
    
    /**
     * Default Constructor, no parameters.
     */
    public Chair() {
        super();
        this.armrests = false;
        image = FixedImageLoader.getChairIcon();
    }
    
    /**
     * Constructor
     * @param armrests - boolean to set whether item will have arm rests.
     * @param idNum - to be passed to super (Furniture) class
     * @param typeOfWood - to be passed to super (Furniture) class
     * @param quantity - to be passed to super (Furniture) class
     */

    public Chair(boolean armrests, String idNum, int typeOfWood, int quantity) {
    super(idNum, typeOfWood, quantity);
    this.armrests = armrests;
    calcNumOfUnits();
    image = FixedImageLoader.getChairIcon();
    }
    
    
    /**
     * 
     * @return Returns whether this chair has arm rests using boolean
     */
   
    public boolean getArmrests()
    {
        return armrests;        
    }
    
    /**
     * 
     * @return The number of units that the chair has, used to calculate price. 
     */
    public int getNumOfUnits()
    {    
        return numOfUnits;
        
    }
    
    private void calcNumOfUnits() {
        if (armrests == true)
        {
            numOfUnits = 1875;
        }
        else
        {
            numOfUnits = 1625;
        }
    }
    
    /**
     * Boolean passed in sets true if has arm rests, false if not. Also changes number of units.
     * @param armrests 
     */
    public void setArmrests(boolean armrests )
    {
        this.armrests = armrests;
                   
    }
   
    /**
     * Used to set the number of units if user wishes to change.
     * @param numOfUnits
     */
    public void setNumOfUnits(int numOfUnits)
    {
        this.numOfUnits = numOfUnits;
    
    }
    
        /**
     * Calculates price of object
     * @return Item's price as a double
     */
    @Override
    public double calcPrice()
    {        
        itemPrice = (getNumOfUnits() * unitPrice) * quantity;
        return itemPrice;
    }
    
    /**
     * 
     * return Description of object as a String.
     */
    @Override
    public String toString()
    {
        String hasArmrests;
        if (armrests == true)
        {
            hasArmrests = "Yes";
        }
        else
        {
            hasArmrests = "No";
        }
        
        String chair = "CHAIR" + "\n" + "Wood: " + woodChoice + "\n" +"Armrests: "
                + hasArmrests + "\n"+ "Number of Units per chair: " + getNumOfUnits() + 
                "\n" + "Quantity: " + quantity;
        return chair;
    }

    @Override
    public int compareTo(FurnitureItem o) {
        return this.getIdNum().compareTo(o.getIdNum());
    }
    


    
}
