package ica2;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class FurnitureDiagramPanel extends JPanel implements DropTargetListener {
    private ArrayList<FurnitureItem> furnitureItems;
    private final int CELL_SIZE = 60;
    private final int GRID_ROWS = 8;
    private final int GRID_COLS = 8;
    
    // Store furniture positions on the grid
    private Map<FurnitureItem, Point> furniturePositions = new HashMap<>();
    
    // For dragging within the panel
    private FurnitureItem selectedItem = null;
    private Point dragStart = null;
    private Point currentDrag = null;
    
    public FurnitureDiagramPanel() {
        this.furnitureItems = new ArrayList<>();
        setPreferredSize(new Dimension(CELL_SIZE * GRID_COLS, CELL_SIZE * GRID_ROWS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        // Enable drop support
        new DropTarget(this, this);
        
        // Add mouse listeners for internal drag and drop
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Find if user clicked on a furniture item
                selectedItem = getFurnitureAtPoint(e.getPoint());
                if (selectedItem != null) {
                    dragStart = e.getPoint();
                    currentDrag = e.getPoint();
                    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (selectedItem != null) {
                    // Calculate grid position
                    int gridX = e.getX() / CELL_SIZE;
                    int gridY = e.getY() / CELL_SIZE;
                    
                    // Ensure within bounds
                    if (gridX >= 0 && gridX < GRID_COLS && gridY >= 0 && gridY < GRID_ROWS) {
                        // Update position
                        furniturePositions.put(selectedItem, new Point(gridX, gridY));
                    }
                    
                    selectedItem = null;
                    dragStart = null;
                    currentDrag = null;
                    setCursor(Cursor.getDefaultCursor());
                    repaint();
                }
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedItem != null) {
                    currentDrag = e.getPoint();
                    repaint();
                }
            }
        });
    }
    
    // Helper method to find furniture at a specific point
    private FurnitureItem getFurnitureAtPoint(Point p) {
        for (FurnitureItem item : furnitureItems) {
            Point pos = furniturePositions.getOrDefault(item, new Point(0, 0));
            int width = getFurnitureWidth(item);
            int height = getFurnitureHeight(item);
            
            Rectangle bounds = new Rectangle(
                    pos.x * CELL_SIZE, 
                    pos.y * CELL_SIZE, 
                    width * CELL_SIZE, 
                    height * CELL_SIZE);
            
            if (bounds.contains(p)) {
                return item;
            }
        }
        return null;
    }
    
    private int getFurnitureWidth(FurnitureItem item) {
        if (item instanceof Desk) {
            Desk desk = (Desk) item;
            return Math.min(3, Math.max(1, desk.getWidth() / 40));
        } else if (item instanceof Table) {
            Table table = (Table) item;
            return Math.min(2, Math.max(1, (int)table.getDiameter() / 50));
        }
        return 1; // Default for chair
    }
    
    private int getFurnitureHeight(FurnitureItem item) {
        if (item instanceof Desk) {
            Desk desk = (Desk) item;
            return Math.min(2, Math.max(1, desk.getDepth() / 40));
        } else if (item instanceof Table) {
            Table table = (Table) item;
            return getFurnitureWidth(item); // Tables are square
        }
        return 1; // Default for chair
    }
    
    public void setFurnitureItems(ArrayList<FurnitureItem> items) {
        // Create a deep copy to avoid reference issues
        this.furnitureItems = new ArrayList<>();
        if (items != null) {
            this.furnitureItems.addAll(items);
            
            // Initialize positions for new items
            for (FurnitureItem item : items) {
                if (!furniturePositions.containsKey(item)) {
                    // Find first available position
                    furniturePositions.put(item, findAvailablePosition(item));
                }
            }
        }
        repaint();
    }
    
    private Point findAvailablePosition(FurnitureItem item) {
        int width = getFurnitureWidth(item);
        int height = getFurnitureHeight(item);
        
        // Simple placement algorithm - find first empty spot that fits
        for (int row = 0; row <= GRID_ROWS - height; row++) {
            for (int col = 0; col <= GRID_COLS - width; col++) {
                boolean positionFree = true;
                
                // Check if position is already occupied
                for (Map.Entry<FurnitureItem, Point> entry : furniturePositions.entrySet()) {
                    Point pos = entry.getValue();
                    FurnitureItem existingItem = entry.getKey();
                    int existingWidth = getFurnitureWidth(existingItem);
                    int existingHeight = getFurnitureHeight(existingItem);
                    
                    // Check for overlap
                    if (!(col + width <= pos.x || pos.x + existingWidth <= col || 
                          row + height <= pos.y || pos.y + existingHeight <= row)) {
                        positionFree = false;
                        break;
                    }
                }
                
                if (positionFree) {
                    return new Point(col, row);
                }
            }
        }
        
        // Default to 0,0 if no space found
        return new Point(0, 0);
    }
    
    // Add a furniture item directly to the panel
    public void addFurnitureItem(FurnitureItem item) {
        if (item != null && !furnitureItems.contains(item)) {
            furnitureItems.add(item);
            furniturePositions.put(item, findAvailablePosition(item));
            repaint();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw grid
        g2d.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= GRID_ROWS; i++) {
            g2d.drawLine(0, i * CELL_SIZE, GRID_COLS * CELL_SIZE, i * CELL_SIZE);
        }
        for (int i = 0; i <= GRID_COLS; i++) {
            g2d.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, GRID_ROWS * CELL_SIZE);
        }
        
        // Draw furniture items if we have any
        if (furnitureItems == null || furnitureItems.isEmpty()) {
            // Draw a message if no furniture
            g2d.setColor(Color.GRAY);
            g2d.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            FontMetrics fm = g2d.getFontMetrics();
            String message = "Add furniture to view layout";
            int textWidth = fm.stringWidth(message);
            int textHeight = fm.getHeight();
            g2d.drawString(message, 
                    (getWidth() - textWidth) / 2, 
                    ((getHeight() - textHeight) / 2) + fm.getAscent());
            return;
        }
        
        // Draw all furniture items
        for (FurnitureItem item : furnitureItems) {
            // Skip the currently dragged item - we'll draw it separately
            if (item == selectedItem) continue;
            
            drawFurnitureItem(g2d, item, furniturePositions.get(item));
        }
        
        // Draw the currently dragged item last (so it appears on top)
        if (selectedItem != null && dragStart != null && currentDrag != null) {
            Point originalPos = furniturePositions.get(selectedItem);
            
            // Calculate offset for smooth dragging
            int offsetX = currentDrag.x - dragStart.x;
            int offsetY = currentDrag.y - dragStart.y;
            
            // Create a temporary point for drawing
            Point tempDrawPoint = new Point(
                    originalPos.x * CELL_SIZE + offsetX, 
                    originalPos.y * CELL_SIZE + offsetY
            );
            
            // Draw with semi-transparency to show it's being dragged
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            drawFurnitureItemAtPixel(g2d, selectedItem, tempDrawPoint);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }
    
    // Draw a furniture item at grid coordinates
    private void drawFurnitureItem(Graphics2D g2d, FurnitureItem item, Point position) {
        // Convert grid position to pixel position
        Point pixelPos = new Point(position.x * CELL_SIZE, position.y * CELL_SIZE);
        drawFurnitureItemAtPixel(g2d, item, pixelPos);
    }
    
    // Draw a furniture item at pixel coordinates
    private void drawFurnitureItemAtPixel(Graphics2D g2d, FurnitureItem item, Point pixelPos) {
        int width = getFurnitureWidth(item);
        int height = getFurnitureHeight(item);
        
        // Choose color based on furniture type
        Color itemColor;
        if (item instanceof Chair) {
            itemColor = new Color(252, 186, 3); // Gold for chairs
        } else if (item instanceof Table) {
            itemColor = new Color(3, 169, 252); // Blue for tables
        } else if (item instanceof Desk) {
            itemColor = new Color(3, 252, 111); // Green for desks
        } else {
            itemColor = new Color(200, 200, 200); // Gray default
        }
        
        // Draw shadow for 3D effect
        g2d.setColor(new Color(0, 0, 0, 40));
        g2d.fillRect(pixelPos.x + 4, pixelPos.y + 4, 
                width * CELL_SIZE - 4, height * CELL_SIZE - 4);
        
        // Draw filled rectangle
        g2d.setColor(itemColor);
        g2d.fillRect(pixelPos.x + 2, pixelPos.y + 2, 
                width * CELL_SIZE - 4, height * CELL_SIZE - 4);
        
        // Draw border
        g2d.setColor(Color.BLACK);
        g2d.drawRect(pixelPos.x + 2, pixelPos.y + 2, 
                width * CELL_SIZE - 4, height * CELL_SIZE - 4);
        
        // Draw label with white background for readability
        String label = item.getClass().getSimpleName();
        FontMetrics fm = g2d.getFontMetrics();
        int labelWidth = fm.stringWidth(label);
        
        g2d.setColor(new Color(255, 255, 255, 180));
        g2d.fillRect(pixelPos.x + 5, pixelPos.y + 5, 
                labelWidth + 6, fm.getHeight() + 2);
        
        g2d.setColor(Color.BLACK);
        g2d.drawString(label, pixelPos.x + 8, pixelPos.y + 5 + fm.getAscent());
        
        // Draw icon if available
        ImageIcon icon = item.getImage();
        if (icon != null) {
            int iconSize = Math.min(width, height) * CELL_SIZE / 2;
            g2d.drawImage(icon.getImage(), 
                    pixelPos.x + (width * CELL_SIZE - iconSize) / 2, 
                    pixelPos.y + 5 + fm.getHeight(), 
                    iconSize, iconSize, 
                    this);
        }
    }
    
    // DropTargetListener methods for drag and drop
    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        if (dtde.isDataFlavorSupported(FurnitureTransferable.FURNITURE_FLAVOR)) {
            dtde.acceptDrag(DnDConstants.ACTION_COPY);
        } else {
            dtde.rejectDrag();
        }
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
        if (dtde.isDataFlavorSupported(FurnitureTransferable.FURNITURE_FLAVOR)) {
            dtde.acceptDrag(DnDConstants.ACTION_COPY);
        } else {
            dtde.rejectDrag();
        }
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
        // Not used
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
        // Not used
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        try {
            Transferable transferable = dtde.getTransferable();
            
            if (transferable.isDataFlavorSupported(FurnitureTransferable.FURNITURE_FLAVOR)) {
                dtde.acceptDrop(DnDConstants.ACTION_COPY);
                
                // Get the dropped furniture item
                FurnitureItem item = (FurnitureItem) transferable.getTransferData(FurnitureTransferable.FURNITURE_FLAVOR);
                
                // Calculate grid position
                Point dropPoint = dtde.getLocation();
                int gridX = dropPoint.x / CELL_SIZE;
                int gridY = dropPoint.y / CELL_SIZE;
                
                // Ensure within bounds
                if (gridX >= 0 && gridX < GRID_COLS && gridY >= 0 && gridY < GRID_ROWS) {
                    // Add item if not already in the list
                    if (!furnitureItems.contains(item)) {
                        furnitureItems.add(item);
                    }
                    
                    // Update position
                    furniturePositions.put(item, new Point(gridX, gridY));
                    repaint();
                }
                
                dtde.dropComplete(true);
            } else {
                dtde.rejectDrop();
            }
        } catch (IOException | UnsupportedFlavorException e) {
            dtde.rejectDrop();
            e.printStackTrace();
        }
    }
}