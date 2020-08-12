package repository;

import domain.Entity;

import java.security.KeyException;
import java.util.List;

public interface IRepository<T extends Entity> {

    void create(T entity) throws KeyException;

    T read(int id);

    List<T> readAll();

    void update(T entity)throws KeyException;

    void delete(int id)throws KeyException;
}
