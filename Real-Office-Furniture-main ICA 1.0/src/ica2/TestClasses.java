/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ica2;

import javax.swing.ImageIcon;

/**
 *
 * @author v8255
 */
public class TestClasses {
    
    /**
     * Tests to ensure classes work as expected.
     * @param args command line arguments
     */
    
    public static void main(String[] args){
        
        //create chair objects to test
        Chair chair1 = new Chair();
        Chair chair2 = new Chair(true, "001", 1, 1);
        Chair chair3 = new Chair(false, "002", 0, 1);
        
        //create table objects to test
        Table table1 = new Table();
        Table table2 = new Table(0, 60, "101", 0, 1);
        Table table3 = new Table(1, 80, "102", 1, 2);
        
        //create desk objects to test
        Desk desk1 = new Desk();
        Desk desk2 = new Desk("201", 0, 1, 3, 80, 80);
        Desk desk3 = new Desk("202", 1, 3, 4, 60, 60);
        
        //create desk objects to test Drawers
        Desk desk4 = new Desk("200", 1, 1, 1, 80, 80);
        Desk desk5 = new Desk("201", 1, 1, 2, 80, 80);
        Desk desk6 = new Desk("202", 1, 1, 4, 80, 80);
        
        //Print chair's description and price, tests toString() and calcPrice() 
        System.out.println(chair1.toString());
        System.out.printf("The price is £%.2f\n\n", chair1.calcPrice());
        System.out.println(chair2.toString());
        System.out.printf("The price is £%.2f\n\n", chair2.calcPrice());
        System.out.println(chair3.toString());
        System.out.printf("The price is £%.2f\n\n", chair3.calcPrice());
        
         //Print table's description and price, tests toString() and calcPrice()
        System.out.println(table1.toString());
        System.out.printf("The price is £%.2f\n\n", table1.calcPrice());
        System.out.println(table2.toString());
        System.out.printf("The price is £%.2f\n\n", table2.calcPrice());
        System.out.println(table3.toString());
        System.out.printf("The price is £%.2f\n\n", table3.calcPrice());
        
        //Print desk's description and price, tests toString() and calcPrice()
        System.out.println(desk1.toString());
        System.out.printf("The price is £%.2f\n\n", desk1.calcPrice());
        System.out.println(desk2.toString());
        System.out.printf("The price is £%.2f\n\n", desk2.calcPrice());
        System.out.println(desk3.toString());
        System.out.printf("The price is £%.2f\n\n", desk3.calcPrice());
        
        //Test Desk Drawers
        System.out.println(desk4.toString());
        System.out.printf("The price is £%.2f\n\n", desk4.calcPrice());
        System.out.println(desk5.toString());
        System.out.printf("The price is £%.2f\n\n", desk5.calcPrice());
        System.out.println(desk6.toString());
        System.out.printf("The price is £%.2f\n\n", desk6.calcPrice());
        
    }
    
}
