package domain;

import repository.IRepository;

import java.security.KeyException;

public class AddOperation<T extends Entity> extends UndoableRedoableOperation<T> {

    private T addedEntity;


    public AddOperation(IRepository<T> repository, T addedEntity) {

        super(repository);
        this.addedEntity = addedEntity;
    }


    @Override
    public void undo() {

        try {

            this.repository.delete(addedEntity.getId());

        } catch(KeyException key) {

            key.printStackTrace();
        }
    }

    @Override
    public void redo() {

        try {

            this.repository.create(addedEntity);

        } catch(KeyException key) {

            key.printStackTrace();
        }
    }
}
