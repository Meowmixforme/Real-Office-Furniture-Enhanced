package ica2;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

public class DragDropManager {
    
    // Set up drag and drop for a furniture item
    public static void setupDragSource(JLabel source, final FurnitureItem item) {
        DragSource dragSource = DragSource.getDefaultDragSource();
        
        dragSource.createDefaultDragGestureRecognizer(
                source, DnDConstants.ACTION_MOVE,
                new DragGestureListener() {
                    @Override
                    public void dragGestureRecognized(DragGestureEvent dge) {
                        Transferable transferable = new FurnitureTransferable(item);
                        dragSource.startDrag(dge, DragSource.DefaultMoveDrop, transferable, new DragSourceListener() {
                            @Override
                            public void dragEnter(DragSourceDragEvent dsde) {}
                            
                            @Override
                            public void dragOver(DragSourceDragEvent dsde) {}
                            
                            @Override
                            public void dropActionChanged(DragSourceDragEvent dsde) {}
                            
                            @Override
                            public void dragExit(DragSourceEvent dse) {}
                            
                            @Override
                            public void dragDropEnd(DragSourceDropEvent dsde) {}
                        });
                    }
                });
    }
    
    // Set up drop target for a diagram panel
    public static void setupDropTarget(Component target, final FurnitureDiagramPanel diagramPanel) {
        new DropTarget(target, new DropTargetListener() {
            @Override
            public void dragEnter(DropTargetDragEvent dtde) {
                if (dtde.isDataFlavorSupported(FurnitureTransferable.FURNITURE_FLAVOR)) {
                    dtde.acceptDrag(DnDConstants.ACTION_MOVE);
                } else {
                    dtde.rejectDrag();
                }
            }
            
            @Override
            public void dragOver(DropTargetDragEvent dtde) {
                if (dtde.isDataFlavorSupported(FurnitureTransferable.FURNITURE_FLAVOR)) {
                    dtde.acceptDrag(DnDConstants.ACTION_MOVE);
                } else {
                    dtde.rejectDrag();
                }
            }
            
            @Override
            public void dropActionChanged(DropTargetDragEvent dtde) {}
            
            @Override
            public void dragExit(DropTargetEvent dte) {}
            
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    Transferable transferable = dtde.getTransferable();
                    if (transferable.isDataFlavorSupported(FurnitureTransferable.FURNITURE_FLAVOR)) {
                        dtde.acceptDrop(DnDConstants.ACTION_MOVE);
                        FurnitureItem item = (FurnitureItem) transferable.getTransferData(FurnitureTransferable.FURNITURE_FLAVOR);
                        
                        // Add the item to the diagram at drop position
                        Point dropPoint = dtde.getLocation();
                        // Here you'd use the drop location to position the furniture
                        
                        diagramPanel.repaint();
                        dtde.dropComplete(true);
                    } else {
                        dtde.rejectDrop();
                    }
                } catch (UnsupportedFlavorException | IOException e) {
                    dtde.rejectDrop();
                }
            }
        });
    }
    
    // Custom Transferable for furniture items
    static class FurnitureTransferable implements Transferable {
        public static final DataFlavor FURNITURE_FLAVOR = new DataFlavor(FurnitureItem.class, "Furniture Item");
        private final FurnitureItem furnitureItem;
        
        public FurnitureTransferable(FurnitureItem item) {
            this.furnitureItem = item;
        }
        
        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[] { FURNITURE_FLAVOR };
        }
        
        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(FURNITURE_FLAVOR);
        }
        
        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (flavor.equals(FURNITURE_FLAVOR)) {
                return furnitureItem;
            }
            throw new UnsupportedFlavorException(flavor);
        }
    }
}