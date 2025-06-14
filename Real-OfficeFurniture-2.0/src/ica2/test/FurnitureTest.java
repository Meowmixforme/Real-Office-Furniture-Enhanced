package ica2.test;

import ica2.Chair;
import ica2.Desk;
import ica2.Table;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FurnitureTest {
    
    @Test
    public void testChairCreation() {
        // Default constructor
        Chair chair1 = new Chair();
        assertEquals("Chair", chair1.getClass().getSimpleName());
        
        // Parameterized constructor
        Chair chair2 = new Chair(true, "C1001", 1, 1);
        assertTrue(chair2.getArmrests());
        assertEquals("C1001", chair2.getIdNum());
        assertEquals("Oak", chair2.getTypeOfWood());
        assertEquals(1, chair2.getQuantity());
    }
    
    @Test
    public void testChairPricing() {
        // Test oak chair without armrests
        Chair oakNoArms = new Chair(false, "C1001", 1, 1);
        // Oak (index 1), unitPrice 0.04, no armrests numOfUnits 1625
        // Price = 1625 * 0.04 * 1 = 65.0
        assertEquals(65.0, oakNoArms.calcPrice(), 0.01);
        
        // Test walnut chair with armrests
        Chair walnutWithArms = new Chair(true, "C1002", 0, 1);
        // Walnut (index 0), unitPrice 0.03, with armrests numOfUnits 1875
        // Price = 1875 * 0.03 * 1 = 56.25
        assertEquals(56.25, walnutWithArms.calcPrice(), 0.01);
        
        // Test quantity effects
        Chair multipleChairs = new Chair(false, "C1003", 1, 3);
        // Oak, no armrests, 3 quantity = 1625 * 0.04 * 3 = 195.0
        assertEquals(195.0, multipleChairs.calcPrice(), 0.01);
    }
    
    @Test
    public void testTableCreation() {
        // Default constructor
        Table table1 = new Table();
        assertEquals("Table", table1.getClass().getSimpleName());
        
        // Parameterized constructor - FIXED to match your implementation
        // Parameters: (int chooseBase, int diameter, String idNum, int chooseWood, int quantity)
        // chooseBase: 0 for Wooden, 1 for Chrome
        Table table2 = new Table(0, 90, "T1001", 0, 1);
        assertEquals(90, table2.getDiameter(), 0.01); // Changed to double comparison
        assertEquals("T1001", table2.getIdNum());
        assertEquals("walnut", table2.getTypeOfWood());
        assertEquals(1, table2.getQuantity());
    }
    
    @Test
    public void testTablePricing() {
        // Test small walnut table with wooden base
        // Parameters: (int chooseBase, int diameter, String idNum, int chooseWood, int quantity)
        Table smallWalnut = new Table(0, 60, "T1001", 0, 1);
        // Small diameter (60), unitPrice 0.03, wooden base price 45.00
        // Price = (60*60) * 0.03 + 45.00 = 108 + 45 = 153.0
        assertEquals(153.0, smallWalnut.calcPrice(), 0.01);
        
        // Test large oak table with chrome base
        Table largeOak = new Table(1, 120, "T1002", 1, 1);
        // Large diameter (120), unitPrice 0.04, chrome base price 35.00
        // Price = (120*120) * 0.04 + 35.00 = 576.0 + 35 = 611.0
        assertEquals(611.0, largeOak.calcPrice(), 0.01);
        
        // Test quantity effects - wooden base
        Table multipleTables = new Table(0, 90, "T1003", 0, 2);
        // Medium diameter (90), unitPrice 0.03, wooden base price 45.00
        // Price = ((90*90) * 0.03 + 45.00) * 2 = (243 + 45) * 2 = 576.0
        assertEquals(576.0, multipleTables.calcPrice(), 0.01);
    }
    
    @Test
    public void testDeskCreation() {
        // Default constructor
        Desk desk1 = new Desk();
        assertEquals("Desk", desk1.getClass().getSimpleName());
        
        // Parameterized constructor - FIXED to match your implementation
        // Parameters: (String idNum, int chooseWood, int quantity, int drawers, int w, int d)
        // Using standard width and depth values (80,80) from default constructor
        Desk desk2 = new Desk("D1001", 1, 1, 2, 80, 80);
        assertEquals(2, desk2.getNumOfDrawers());
        assertEquals("D1001", desk2.getIdNum());
        assertEquals("Oak", desk2.getTypeOfWood());
        assertEquals(1, desk2.getQuantity());
    }
    
    @Test
    public void testDeskPricing() {
        // Test walnut desk with no drawers
        // Parameters: (String idNum, int chooseWood, int quantity, int drawers, int w, int d)
        Desk walnutNoDrawers = new Desk("D1001", 0, 1, 0, 80, 80);
        // Walnut (index 0), unitPrice 0.03, no drawers
        // Price = (80*80) * 0.03 + (0 * 8.50) = 192.0
        assertEquals(192.0, walnutNoDrawers.calcPrice(), 0.01);
        
        // Test oak desk with drawers
        Desk oakWithDrawers = new Desk("D1002", 1, 1, 3, 80, 80);
        // Oak (index 1), unitPrice 0.04, 3 drawers at 8.50 each
        // Price = (80*80) * 0.04 + (3 * 8.50) = 256.0 + 25.5 = 281.5
        assertEquals(281.5, oakWithDrawers.calcPrice(), 0.01);
        
        // Test quantity effects
        Desk multipleDesks = new Desk("D1003", 0, 2, 1, 80, 80);
        // Walnut (index 0), unitPrice 0.03, 1 drawer at 8.50
        // Price = ((80*80) * 0.03 + (1 * 8.50)) * 2 = (192.0 + 8.5) * 2 = 401.0
        assertEquals(401.0, multipleDesks.calcPrice(), 0.01);
    }
}