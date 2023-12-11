package org.example.db;

public interface Dao<T> {
    Long save(T t);

    T getById( Long id);

    void update(T t , Long id);

    void deleteById(Long id);

}
