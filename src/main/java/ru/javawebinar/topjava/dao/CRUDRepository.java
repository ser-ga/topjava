package ru.javawebinar.topjava.dao;

import java.util.List;

public interface CRUDRepository<T> {
    void add(T t);
    T getById(int id);
    List<T> getAll();
    void update(T t);
    void remove(int id);
}
