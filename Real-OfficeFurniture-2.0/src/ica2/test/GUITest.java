package ica2.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.lang.reflect.Field;
import java.util.ArrayList;

// Import your project-specific classes
import ica2.GUI;
import ica2.FurnitureItem;
import ica2.CommandManager;

public class GUITest {

    @Test
    public void testGUIInitialization() {
        GUI gui = new GUI();
        
        // Test that furniture list is initialized
        try {
            Field furnitureListField = GUI.class.getDeclaredField("FurnitureList");
            furnitureListField.setAccessible(true);
            ArrayList<FurnitureItem> list = (ArrayList<FurnitureItem>) furnitureListField.get(gui);
            assertNotNull(list);
            assertTrue(list.isEmpty());
        } catch (Exception e) {
            fail("Could not access FurnitureList field: " + e.getMessage());
        }
        
        // Test that command manager is initialized
        try {
            Field cmdManagerField = GUI.class.getDeclaredField("cmdManager");
            cmdManagerField.setAccessible(true);
            CommandManager manager = (CommandManager) cmdManagerField.get(gui);
            assertNotNull(manager);
        } catch (Exception e) {
            fail("Could not access cmdManager field: " + e.getMessage());
        }
        
        // Test panels are initialized
        try {
            Field itemsPNLField = GUI.class.getDeclaredField("itemsPNL");
            itemsPNLField.setAccessible(true);
            JPanel panel = (JPanel) itemsPNLField.get(gui);
            assertNotNull(panel);
        } catch (Exception e) {
            fail("Could not access itemsPNL field: " + e.getMessage());
        }
    }
}