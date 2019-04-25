package com.piedcard.model.dao.interfaces;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    T get(long id);

    List<T> getAll();

    List<T> getAllById(long id);

    int count(long id);

    boolean save(T t);

    boolean update(T t);

    boolean delete(T t);
}