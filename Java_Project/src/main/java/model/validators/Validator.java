package model.validators;

public abstract class Validator<T> {

     public void validate(T entity) {

          Error<T> error = createError();
          error.setEntity(entity);
          error.showErrorMessages();
     }

     protected abstract Error<T> createError();

     public abstract void validateList(Iterable<T> list);
}
