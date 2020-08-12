package domain;

import repository.IRepository;

public abstract class UndoableRedoableOperation<T extends Entity> {

    protected IRepository<T> repository;

    public UndoableRedoableOperation(IRepository<T> repository) {

        this.repository = repository;
    }


    public abstract void undo();
    public abstract void redo();

}
