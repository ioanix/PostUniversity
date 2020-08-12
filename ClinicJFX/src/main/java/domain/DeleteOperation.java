package domain;

import repository.IRepository;

import java.security.KeyException;

public class DeleteOperation<T extends Entity> extends UndoableRedoableOperation<T> {

    private T deletedEntity;

    public DeleteOperation(IRepository<T> repository, T deletedEntity) {

        super(repository);
        this.deletedEntity = deletedEntity;
    }

    @Override
    public void undo() {

        try {

            this.repository.create(deletedEntity);

        } catch (KeyException key) {

            System.out.println(key.getMessage());
        }
    }

    @Override
    public void redo() {

        try {

            this.repository.delete(deletedEntity.getId());

        } catch (KeyException key) {

            System.out.println(key.getMessage());
        }

    }
}
