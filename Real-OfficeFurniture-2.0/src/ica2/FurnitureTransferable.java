package ica2;

import java.awt.datatransfer.*;
import java.io.IOException;

public class FurnitureTransferable implements Transferable {
    // Custom flavor for furniture items
    public static final DataFlavor FURNITURE_FLAVOR = new DataFlavor(FurnitureItem.class, "Furniture Item");
    private static final DataFlavor[] SUPPORTED_FLAVORS = { FURNITURE_FLAVOR };
    
    private FurnitureItem furnitureItem;
    
    public FurnitureTransferable(FurnitureItem item) {
        this.furnitureItem = item;
    }
    
    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return SUPPORTED_FLAVORS;
    }
    
    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(FURNITURE_FLAVOR);
    }
    
    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (isDataFlavorSupported(flavor)) {
            return furnitureItem;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }
}