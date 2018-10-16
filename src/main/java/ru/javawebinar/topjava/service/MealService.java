package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealService {

    Meal create(Meal meal, int userId);

    Meal update(Meal meal, int userId);

    void delete(int id, int userId);

    Meal get(int id, int userId);

    List<MealWithExceed> getAll(int userId, int authUserCaloriesPerDay);

    public List<MealWithExceed> getFiltered(LocalDate startDate, LocalDate finishDate, LocalTime startTime, LocalTime finishTime, int userId, int authUserCaloriesPerDay);
}