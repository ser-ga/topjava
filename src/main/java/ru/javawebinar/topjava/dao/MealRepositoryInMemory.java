package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepositoryInMemory implements MealRepository {
    private Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    private AtomicInteger idGenerator = new AtomicInteger(0);

    public MealRepositoryInMemory() {
        Meal meal1 = new Meal(generateId(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
        Meal meal2 = new Meal(generateId(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
        Meal meal3 = new Meal(generateId(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
        Meal meal4 = new Meal(generateId(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
        Meal meal5 = new Meal(generateId(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
        Meal meal6 = new Meal(generateId(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
        meals.put(meal1.getId(), meal1);
        meals.put(meal2.getId(), meal2);
        meals.put(meal3.getId(), meal3);
        meals.put(meal4.getId(), meal4);
        meals.put(meal5.getId(), meal5);
        meals.put(meal6.getId(), meal6);
    }

    @Override
    public Meal add(Meal meal) {

        if (meal == null) return null;
        if (meal.getId() == 0) {
            meal.setId(generateId());
        }
        return meals.put(meal.getId(), meal);
    }

    @Override
    public Meal getById(int id) {
        return meals.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal update(Meal meal) {
        return meals.replace(meal.getId(), meal);
    }

    @Override
    public void remove(int id) {
        meals.remove(id);
    }

    private int generateId() {
        return idGenerator.incrementAndGet();
    }
}
