package model.validators;

public interface Validator<T> {

     void validate(T entity);
     void validateList(Iterable<T> list);

}
