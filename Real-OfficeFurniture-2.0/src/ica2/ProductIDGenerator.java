package ica2;

import java.util.HashMap;
import java.util.Map;

/**
 * Generator for automatic product IDs
 */
public class ProductIDGenerator {
    private static Map<String, Integer> counters = new HashMap<>();
    
    static {
        // Initialize with starting values
        counters.put("Chair", 1000);
        counters.put("Table", 2000);
        counters.put("Desk", 3000);
    }
    
    /**
     * Generate a unique product ID for the specified furniture type
     * @param type The furniture type (Chair, Table, Desk)
     * @return A unique product ID 
     */
    public static String generateID(String type) {
        if (!counters.containsKey(type)) {
            // If the type doesn't exist, add it with a default starting value
            counters.put(type, 1000);
        }
        
        // Get the current counter and increment it
        int counter = counters.get(type);
        counters.put(type, counter + 1);
        
        // Build ID with prefix and counter
        String prefix = type.substring(0, 1).toUpperCase(); // First letter of type
        return prefix + counter;
    }
    
    /**
     * Reset all counters (primarily for testing)
     */
    public static void resetCounters() {
        counters.put("Chair", 1000);
        counters.put("Table", 2000);
        counters.put("Desk", 3000);
    }
    
    /**
     * Set a custom counter value for a type (used when loading saved data)
     * @param type The furniture type
     * @param value The new counter value
     */
    public static void setCounter(String type, int value) {
        counters.put(type, value);
    }
    
    /**
     * Get the current counter value for a type
     * @param type The furniture type
     * @return The current counter value
     */
    public static int getCounter(String type) {
        return counters.getOrDefault(type, 1000);
    }
}