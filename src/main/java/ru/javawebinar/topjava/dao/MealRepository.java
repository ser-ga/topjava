package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    Meal add(Meal meal);
    Meal getById(int id);
    List<Meal> getAll();
    Meal update(Meal meal);
    void remove(int id);
}
