package ica2;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Manages undo/redo operations using the Command pattern
 */
public class CommandManager {
    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();
    private ArrayList<CommandListener> listeners = new ArrayList<>();
    
    /**
     * Execute a command and add it to the undo stack
     * @param command The command to execute
     */
    public void executeCommand(Command command) {
        System.out.println("Executing command: " + command.getClass().getSimpleName());
        command.execute();
        undoStack.push(command);
        redoStack.clear(); // Clear redo stack after a new command
        notifyListeners();
    }
    
    /**
     * Check if undo is available
     * @return true if undo is available
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }
    
    /**
     * Check if redo is available
     * @return true if redo is available
     */
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
    
    /**
     * Perform undo operation
     */
    public void undo() {
        if (canUndo()) {
            Command command = undoStack.pop();
            System.out.println("Undoing command: " + command.getClass().getSimpleName());
            command.undo();
            redoStack.push(command);
            notifyListeners();
        }
    }
    
    /**
     * Perform redo operation
     */
    public void redo() {
        if (canRedo()) {
            Command command = redoStack.pop();
            System.out.println("Redoing command: " + command.getClass().getSimpleName());
            command.execute();
            undoStack.push(command);
            notifyListeners();
        }
    }
    
    /**
     * Add a listener to be notified when commands are executed
     * @param listener The listener to add
     */
    public void addCommandListener(CommandListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    
    /**
     * Remove a command listener
     * @param listener The listener to remove
     */
    public void removeCommandListener(CommandListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Notify all listeners that a command has been executed
     */
    private void notifyListeners() {
        for (CommandListener listener : listeners) {
            listener.commandExecuted();
        }
    }
    
    /**
     * Listener interface for command execution notifications
     */
    public interface CommandListener {
        void commandExecuted();
    }
    
    /**
     * Interface for commands that can be executed and undone
     */
    public interface Command {
        void execute();
        void undo();
    }
    
    /**
     * Command for adding furniture
     */
    public static class AddFurnitureCommand implements Command {
        private final ArrayList<FurnitureItem> furnitureList;
        private final FurnitureItem furnitureItem;
        
        public AddFurnitureCommand(ArrayList<FurnitureItem> list, FurnitureItem item) {
            this.furnitureList = list;
            this.furnitureItem = item;
        }
        
        @Override
        public void execute() {
            if (!furnitureList.contains(furnitureItem)) {
                System.out.println("Adding item: " + furnitureItem.getIdNum());
                furnitureList.add(furnitureItem);
            }
        }
        
        @Override
        public void undo() {
            System.out.println("Removing item: " + furnitureItem.getIdNum());
            furnitureList.remove(furnitureItem);
        }
    }
    
    /**
     * Command for removing furniture
     */
    public static class RemoveFurnitureCommand implements Command {
        private final ArrayList<FurnitureItem> furnitureList;
        private final FurnitureItem furnitureItem;
        private final int originalIndex;
        
        public RemoveFurnitureCommand(ArrayList<FurnitureItem> list, FurnitureItem item, int index) {
            this.furnitureList = list;
            this.furnitureItem = item;
            this.originalIndex = index;
        }
        
        @Override
        public void execute() {
            System.out.println("Removing item: " + furnitureItem.getIdNum());
            furnitureList.remove(furnitureItem);
        }
        
        @Override
        public void undo() {
            System.out.println("Restoring item: " + furnitureItem.getIdNum() + " at index " + originalIndex);
            if (originalIndex >= 0 && originalIndex <= furnitureList.size()) {
                furnitureList.add(originalIndex, furnitureItem);
            } else {
                furnitureList.add(furnitureItem);
            }
        }
    }
    
    /**
     * Clear command stack (for testing)
     */
    public void clearStacks() {
        undoStack.clear();
        redoStack.clear();
        notifyListeners();
    }
}