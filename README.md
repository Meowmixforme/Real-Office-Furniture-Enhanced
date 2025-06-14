# Real Office Furniture - Java Swing App 2.0

![Java](https://img.shields.io/badge/Java-SE_8+-orange)
![Swing](https://img.shields.io/badge/UI-Swing-blue)
![iText](https://img.shields.io/badge/PDF-iText-green)

A comprehensive furniture management and layout planning application built with Java Swing. This application helps users create, manage, and visualize office furniture arrangements, generate reports, and export layouts to PDF.

## 🌟 Features

### Core Functionality

- **Furniture Management**: Add, view, and remove three types of furniture:
  - Chairs (with/without armrests)
  - Tables (with adjustable diameter and base type)
  - Desks (with customizable dimensions and drawers)

- **Automated Product IDs**: Automatic generation of unique product IDs for each furniture type:
  - Chairs: C1000, C1001, etc.
  - Tables: T2000, T2001, etc.
  - Desks: D3000, D3001, etc.

- **Interactive Visual Layout**: Drag and drop interface for arranging furniture on a grid layout:
  - Visual representation of furniture with type-specific colors
  - Automatic sizing based on furniture dimensions
  - Grid-based snapping for precise positioning

- **Data Persistence**: Save and load furniture arrangements:
  - Complete serialization of furniture items
  - Preservation of IDs, properties, and positions

### Advanced Features

- **Undo/Redo Support**: Full command pattern implementation for:
  - Adding furniture items
  - Removing furniture items
  - Clearing the list
  - Loading saved layouts

- **PDF Export**: Generate professional PDF reports with:
  - Complete furniture inventory with details
  - Visual representation of the layout
  - Price calculations and summaries
  - Automatic opening of the generated PDF

- **Detailed Item Information**:
  - Click on any furniture item to view detailed specifications
  - Edit or remove items directly from the details dialog
  - Visual confirmation of selected items

- **Advanced Pricing System**:
  - Dynamic price calculation based on furniture properties
  - Support for quantity adjustments
  - Total price calculation for the entire order

## 📸 Screenshots


![Screenshot 2025-06-14 145922](https://github.com/user-attachments/assets/b144682a-6a24-4962-b55b-1f7d1032ffba)

![Screenshot 2025-06-14 145937](https://github.com/user-attachments/assets/0c8d998b-42e5-42e4-aa73-deb8545b9588)

![Screenshot 2025-06-14 145941](https://github.com/user-attachments/assets/d5c5f5ca-9016-43ac-80d7-8d832a8a899c)

![Screenshot 2025-06-14 145958](https://github.com/user-attachments/assets/a60544b1-2ce4-49a7-a22e-f771a4b9310f)


## 🛠️ Technical Implementation

- **Design Patterns**:
  - Command Pattern for undo/redo functionality
  - Factory Pattern for furniture creation
  - Observer Pattern for UI updates

- **UI Components**:
  - Custom drag and drop implementation
  - Split pane interface for furniture list and layout
  - Toolbar with action buttons
  - Custom styled components

- **Libraries**:
  - iText PDF for generating PDF reports
  - Java Serialization API for data persistence
  - Java Swing for the user interface

## 🚀 Improvements in Version 2.0

- **Added Drag and Drop**: Completely reimplemented furniture placement using drag and drop
- **Automatic ID Generation**: Eliminated manual product ID entry
- **Undo/Redo System**: Added comprehensive action history management
- **PDF Export**: Implemented professional report generation
- **Enhanced Visualization**: Improved furniture representation on the grid
- **Improved UI**: Better component styling and interaction feedback
- **Code Refactoring**: Improved architecture and code organization
- **Bug Fixes**: Resolved issues from previous version

## 🔧 Installation

1. Ensure Java 8 or higher is installed
2. Clone the repository
3. Import the project into your preferred Java IDE
4. Add the required libraries:
   - iText PDF (included in the lib folder)
5. Run the `MainDriver` class

## 📚 Usage

1. Launch the application
2. Add furniture items using the buttons on the left panel
3. Drag furniture items from the list to the grid layout
4. Rearrange items by dragging them on the grid
5. View item details by clicking on them
6. Use the toolbar for undo, redo, and PDF export functions
7. Save your layout using the Save button
8. Load previously saved layouts using the Load button

## 📝 Notes

This application was originally developed for a first-year university module and has been significantly enhanced with additional features and improvements for version 2.0.

