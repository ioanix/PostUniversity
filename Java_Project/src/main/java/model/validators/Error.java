package model.validators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Error<T> {

    private String message;
    private Map<String, Predicate<T>> predicateMap = new HashMap<>();

    private T entity;

    public Error(Predicate<T> predicate, String message) {
        this.message = message;
        this.predicateMap.put(message, predicate);

    }

    public Error(Predicate<T> predicate, String message, Map<String, Predicate<T>> predicateMap) {
        this.message = message;
        this.predicateMap = predicateMap;
        this.predicateMap.put(message, predicate);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
    public Map<String, Predicate<T>> getPredicateMap() {
        return predicateMap;
    }


    public static <T> Error<T> errorIf(Predicate<T> predicate, String message) {

        return new Error<>(predicate, message);
    }

    public Error<T> and(Error<T> error) {

        return new Error<>(error.getPredicateMap().get(error.getMessage()), error.getMessage(), this.predicateMap);
    }

    public void showErrorMessages() {

        List<String> errorMessages = this.predicateMap.keySet().stream()
                .filter(errorMessage -> this.predicateMap.get(errorMessage).test(this.entity))
                .collect(Collectors.toList());

        errorMessages.stream()
                .findAny()
                .ifPresent(errorMessage -> {
                    throw new ValidatorException(errorMessages.toString());
                });

    }
}
