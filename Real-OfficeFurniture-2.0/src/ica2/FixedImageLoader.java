package ica2;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;

public class FixedImageLoader {
    
    private static final String BASE_PATH = "images/icons/";
    
    public static ImageIcon loadFurnitureIcon(String imageName, int width, int height) {
        // Try multiple possible paths to find the icon
        String[] possiblePaths = {
            BASE_PATH + imageName,
            "src/" + BASE_PATH + imageName,
            "../" + BASE_PATH + imageName,
            imageName
        };
        
        for (String path : possiblePaths) {
            File file = new File(path);
            if (file.exists()) {
                ImageIcon icon = new ImageIcon(path);
                Image img = icon.getImage();
                Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(resizedImg);
            }
        }
        
        // If reaching here, we couldn't find the image - create a simple placeholder
        return createPlaceholderIcon(width, height);
    }
    
    public static ImageIcon getChairIcon() {
        return loadFurnitureIcon("chair.png", 50, 50);
    }
    
    public static ImageIcon getTableIcon() {
        return loadFurnitureIcon("table.png", 50, 50);
    }
    
    public static ImageIcon getDeskIcon() {
        return loadFurnitureIcon("desk.png", 50, 50);
    }
    
    private static ImageIcon createPlaceholderIcon(int width, int height) {
        // Create a blank icon of the specified size
        Image image = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        return new ImageIcon(image);
    }
}