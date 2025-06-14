package ica2;

import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import javax.swing.*;

public class DraggableFurnitureLabel extends JLabel implements DragSourceListener, DragGestureListener {
    private FurnitureItem furnitureItem;
    private DragSource dragSource;
    
    public DraggableFurnitureLabel(FurnitureItem item) {
        super();
        this.furnitureItem = item;
        if (item != null && item.getImage() != null) {
            setIcon(item.getImage());
        }
        
        // Set up drag capability
        dragSource = new DragSource();
        dragSource.createDefaultDragGestureRecognizer(
                this, DnDConstants.ACTION_COPY, this);
        
        // Styling
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        setToolTipText("Drag to place on grid");
        
        // Mouse hover effect
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
                setCursor(Cursor.getDefaultCursor());
            }
        });
    }
    
    public FurnitureItem getFurnitureItem() {
        return furnitureItem;
    }
    
    // DragGestureListener implementation
    @Override
    public void dragGestureRecognized(DragGestureEvent dge) {
        // Create transferable object
        Transferable transferable = new FurnitureTransferable(furnitureItem);
        
        // Start drag
        dragSource.startDrag(dge, DragSource.DefaultCopyDrop, transferable, this);
    }
    
    // DragSourceListener implementation
    @Override
    public void dragEnter(DragSourceDragEvent dsde) {}
    
    @Override
    public void dragOver(DragSourceDragEvent dsde) {}
    
    @Override
    public void dropActionChanged(DragSourceDragEvent dsde) {}
    
    @Override
    public void dragExit(DragSourceEvent dse) {}
    
    @Override
    public void dragDropEnd(DragSourceDropEvent dsde) {
        // Reset border after drag ends
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    }
}