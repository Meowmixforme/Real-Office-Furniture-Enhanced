package ica2;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Class to export furniture list and layout diagram to PDF
 */
public class PDFExporter {
    
    /**
     * Export furniture list and diagram to PDF
     * @param furnitureList The list of furniture items
     * @param diagramPanel The diagram panel to export
     */
    public static void exportToPDF(ArrayList<FurnitureItem> furnitureList, JPanel diagramPanel) {
        System.out.println("Starting PDF export process...");
        
        if (furnitureList == null || furnitureList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No furniture items to export.", 
                    "Export Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Ask user for file location
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save PDF File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));
        
        if (fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        if (!filePath.toLowerCase().endsWith(".pdf")) {
            filePath += ".pdf";
        }
        
        System.out.println("Saving PDF to: " + filePath);
        
        try {
            // Create PDF document
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            
            // Add title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Real Office Furniture - Order Summary", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            
            // Add date
            Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Paragraph date = new Paragraph("Date: " + 
                    new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()), dateFont);
            date.setAlignment(Element.ALIGN_CENTER);
            document.add(date);
            
            document.add(new Paragraph(" ")); // Add some space
            
            // Add furniture table
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            
            // Set column widths
            float[] columnWidths = {1.5f, 1f, 3f, 0.8f, 1.2f};
            table.setWidths(columnWidths);
            
            // Add table headers
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            addTableHeader(table, headerFont, "Item Type", "Wood Type", "Details", "Quantity", "Price");
            
            // Add table rows
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            double totalPrice = 0;
            
            for (FurnitureItem item : furnitureList) {
                String details = getItemDetails(item);
                double price = item.calcPrice();
                totalPrice += price;
                
                addTableRow(table, cellFont, 
                        item.getClass().getSimpleName(),
                        item.getTypeOfWood(),
                        details,
                        String.valueOf(item.getQuantity()),
                        String.format("£%.2f", price));
            }
            
            document.add(table);
            
            // Add total price
            Font totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Paragraph totalPara = new Paragraph("Total Price: £" + 
                    String.format("%.2f", totalPrice), totalFont);
            totalPara.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalPara);
            
            document.add(new Paragraph(" ")); // Add some space
            
            // Add diagram if available
            if (diagramPanel != null) {
                document.add(new Paragraph("Office Layout:", headerFont));
                document.add(new Paragraph(" ")); // Add some space
                
                System.out.println("Capturing diagram image...");
                
                // Create temp image file for diagram
                File tempFile = File.createTempFile("diagram", ".png");
                BufferedImage image = new BufferedImage(
                        diagramPanel.getWidth(), diagramPanel.getHeight(), 
                        BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = image.createGraphics();
                diagramPanel.paint(g2);
                g2.dispose();
                ImageIO.write(image, "png", tempFile);
                
                // Add image to PDF
                Image pdfImage = Image.getInstance(tempFile.getAbsolutePath());
                float docWidth = document.getPageSize().getWidth() - 40;
                pdfImage.scaleToFit(docWidth, pdfImage.getHeight() * (docWidth / pdfImage.getWidth()));
                document.add(pdfImage);
                
                // Clean up temp file
                tempFile.delete();
            }
            
            document.close();
            
            System.out.println("PDF export completed successfully");
            
            JOptionPane.showMessageDialog(null, "PDF exported successfully to:\n" + filePath, 
                    "Export Complete", JOptionPane.INFORMATION_MESSAGE);
            
            // Try to open the PDF file
            try {
                File file = new File(filePath);
                if (file.exists()) {
                    java.awt.Desktop.getDesktop().open(file);
                }
            } catch (Exception e) {
                // Silently ignore if we can't open the file
            }
            
        } catch (Exception e) {
            System.err.println("Error in PDF export: " + e.getMessage());
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(null, 
                    "Error exporting to PDF: " + e.getMessage(),
                    "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Add header row to the table
     * @param table The table to add headers to
     * @param font Font to use for headers
     * @param headers The header text
     */
    private static void addTableHeader(PdfPTable table, Font font, String... headers) {
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);
        }
    }
    
    /**
     * Add a row to the table
     * @param table The table to add a row to
     * @param font Font to use for cells
     * @param cells The cell text
     */
    private static void addTableRow(PdfPTable table, Font font, String... cells) {
        for (String cell : cells) {
            PdfPCell pdfCell = new PdfPCell(new Phrase(cell, font));
            pdfCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfCell.setPadding(5);
            table.addCell(pdfCell);
        }
    }
    
    /**
     * Get details string for a furniture item
     * @param item The furniture item
     * @return Details as string
     */
    private static String getItemDetails(FurnitureItem item) {
        StringBuilder details = new StringBuilder();
        
        if (item instanceof Chair) {
            Chair chair = (Chair) item;
            details.append("Armrests: ").append(chair.getArmrests() ? "Yes" : "No");
            details.append("\nUnits: ").append(chair.getNumOfUnits());
        } else if (item instanceof Table) {
            Table table = (Table) item;
            details.append("Diameter: ").append(table.getDiameter());
            details.append("\nBase: ").append(table.getBaseChoice());
        } else if (item instanceof Desk) {
            Desk desk = (Desk) item;
            details.append("Width: ").append(desk.getWidth());
            details.append("\nDepth: ").append(desk.getDepth());
            details.append("\nDrawers: ").append(desk.getNumOfDrawers());
        }
        
        return details.toString();
    }
}