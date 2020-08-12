package service;

import domain.Entity;
import domain.UndoableRedoableOperation;

import java.util.Stack;

public class UndoRedoService {


    private Stack<UndoableRedoableOperation<? extends Entity>> undoStack = new Stack<>();
    private Stack<UndoableRedoableOperation> redoStack = new Stack<>();


    public Stack<UndoableRedoableOperation<? extends Entity>> getUndoStack() {
        return undoStack;
    }

    public Stack<UndoableRedoableOperation> getRedoStack() {
        return redoStack;
    }

    public void addToUndo(UndoableRedoableOperation<? extends Entity> undoableRedoableOperation) {

        this.undoStack.push(undoableRedoableOperation);
        this.redoStack.clear();
    }


    public boolean undo() {

        if(!this.undoStack.isEmpty()) {

            UndoableRedoableOperation<? extends Entity> entityUndoableReadoableOperation = this.undoStack.pop();
            entityUndoableReadoableOperation.undo();

            this.redoStack.push(entityUndoableReadoableOperation);
            return true;
        }

        return false;

    }


    public boolean redo() {

        if(!this.redoStack.isEmpty()) {

            UndoableRedoableOperation<? extends Entity> entityUndoableReadoableOperation = this.redoStack.pop();
            entityUndoableReadoableOperation.redo();

            this.undoStack.push(entityUndoableReadoableOperation);
            return true;
        }

        return false;
    }


}
