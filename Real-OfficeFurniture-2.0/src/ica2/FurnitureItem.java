/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ica2;

import java.io.Serializable;
import javax.swing.*;
    
/**
 *
 * @author v8255920
 */
public abstract class FurnitureItem implements Serializable, Comparable<FurnitureItem>
{
    private String idNum;
    private String[] typeOfWood = {"wallnut","Oak"};
    protected String woodChoice;
    protected double itemPrice;
    protected double unitPrice;
    protected int quantity;
    protected ImageIcon image;
    
    
    /**
     * Default constructor, no parameters. 
     */
    
    FurnitureItem()
    {
        this.idNum = "999";
        this.quantity = 0;
        this.woodChoice = "Wallnut";
        this.unitPrice = 0.03;
    }        
            
    /**
     * Constructor, sets unit pice using choice of wood entered.
     * @param idNum
     * @param chooseWood
     * @param quantity
     */  
    public FurnitureItem(String idNum, int chooseWood, int quantity)
    {
        this.idNum = idNum;
        this.woodChoice = typeOfWood[chooseWood];
        this.quantity = quantity;
        
        if (woodChoice.equals(typeOfWood[0]))
        {
            unitPrice = 0.03;
        }    
        else
        {
            unitPrice = 0.04;
        }    
    }
    
    /**
     * 
     * @return ID number of an item as integer.
     */
    public String getIdNum()
    {
        return idNum;
    }
    
    /**
     * @return Item's type of wood as String
     */
    public String getTypeOfWood()
    {
        return woodChoice;
    }        
    
    /**
     * @return Item's price as double.
     */
    public double gerItemPrice()
    {
        return itemPrice;
    }        
    
    /**
     * @return Price per unit of item.
     */
    public double getUnitPrice()
    {
        return unitPrice;
    }        
    
    /**
     * @return Quantity of current item.
     */
    public int getQuantity()
    {
        return quantity;
    }        
    
    /**
     * @return ImageIcon for item.
     */
    public ImageIcon getImage()
    {
        return image;
    }   
    
    /**
     * Sets new ID Number for item as int passed.
     * @param idNum
     */
    public void setIdNum(String idNum)
    {
        this.idNum = idNum;
    }        
    
    /**
     * Sets new unit price for item as int passed.
     * @param unitPrice
     */
    public void setUnitPrice(double unitPrice)
    {
        this.unitPrice = unitPrice;
    }   
    
    /**
     * Set items new type of wood, also alters unit price according to type of wood.
     * @param wood item's wood type altered to String passed.
     */
    public void setTypeofWood(String wood)
    {
        this.woodChoice = wood;
        
        if(woodChoice == "Walnut")
        {
            unitPrice = 0.03;
        }
        else if(woodChoice == "Oak")
        {
            unitPrice =0.04;
        }    
    }   
    
    /**
     * Sets items price to new price passed.
     * @param itemPrice
     */
    public void setItemPrice(double itemPrice)
    {
        this.itemPrice = itemPrice;
    } 
    
    /**
     * Sets new quantity of item.
     * @param quantity
     */
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }   
    
    /**
     * Sets item's current image to new one passed.
     * @param image
     */
    public void getImage(ImageIcon Image)
    {
        this.image = image;
    }   
    
    @Override
    public abstract String toString();
    
    public abstract double calcPrice();
    
    
    
}

    
    

    

