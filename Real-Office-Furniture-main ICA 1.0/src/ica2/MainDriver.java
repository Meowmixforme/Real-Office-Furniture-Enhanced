/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ica2;

import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author v8255920
 */
public class MainDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
       JFrame main = new JFrame("Real Office Furniture");
       GUI guiPNL = new GUI();
       main.add(guiPNL);
      
       main.setSize(750, 660);
       main.setVisible(true);
       main.setDefaultCloseOperation(3);
        
       

    }
    
}
