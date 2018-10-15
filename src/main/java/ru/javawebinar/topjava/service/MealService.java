package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.util.List;

public interface MealService {

    Meal create(Meal meal, int userId);

    Meal update(Meal meal, int userId);

    void delete(int id, int userId);

    Meal get(int id, int userId);

    List<MealWithExceed> getAll(int userId, int authUserCaloriesPerDay);
}