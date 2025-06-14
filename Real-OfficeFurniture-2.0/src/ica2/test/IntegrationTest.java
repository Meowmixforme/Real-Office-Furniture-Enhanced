package ica2.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import ica2.Chair;
import ica2.Desk;
import ica2.Table;
import ica2.FurnitureItem;
import ica2.CommandManager;

public class IntegrationTest {

    @Test
    public void testCommandManagerWithFurniture() {
        // Setup
        ArrayList<FurnitureItem> list = new ArrayList<>();
        CommandManager manager = new CommandManager();
        Chair chair = new Chair(true, "C1001", 0, 1);
        
        // Execute add command
        CommandManager.AddFurnitureCommand addCmd = 
            new CommandManager.AddFurnitureCommand(list, chair);
        manager.executeCommand(addCmd);
        
        // Verify item was added
        assertEquals(1, list.size());
        assertEquals(chair, list.get(0));
        assertTrue(manager.canUndo());
        assertFalse(manager.canRedo());
        
        // Test undo
        manager.undo();
        assertTrue(list.isEmpty());
        assertFalse(manager.canUndo());
        assertTrue(manager.canRedo());
        
        // Test redo
        manager.redo();
        assertEquals(1, list.size());
        assertEquals(chair, list.get(0));
    }
    
    @Test
    public void testMultipleFurnitureItems() {
        // Create a mixed list of furniture
        ArrayList<FurnitureItem> list = new ArrayList<>();
        list.add(new Chair(true, "C1001", 0, 2));
        list.add(new Table(0, 80, "T2001", 1, 1));
        list.add(new Desk("D3001", 1, 1, 2, 120, 60));
        
        // Calculate total price
        double totalPrice = 0;
        for (FurnitureItem item : list) {
            totalPrice += item.calcPrice();
        }
        
        // The total should be the sum of individual prices
        double expectedTotal = 
            new Chair(true, "C1001", 0, 2).calcPrice() +
            new Table(0, 80, "T2001", 1, 1).calcPrice() +
            new Desk("D3001", 1, 1, 2, 120, 60).calcPrice();
        
        assertEquals(expectedTotal, totalPrice, 0.01);
    }
}