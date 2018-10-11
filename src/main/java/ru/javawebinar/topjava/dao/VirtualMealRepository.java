package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

public class VirtualMealRepository implements CRUDRepository<Meal> {
    private static VirtualMealRepository instance;
    private Map<Integer, Meal> meals = new ConcurrentSkipListMap<>();
    private volatile AtomicInteger id = new AtomicInteger(0);

    private VirtualMealRepository() {
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

    public static synchronized CRUDRepository<Meal> getInstance() {
        if (instance == null) {
            instance = new VirtualMealRepository();
        }
        return instance;
    }

    @Override
    public void add(Meal meal) {
        if (meal == null) return;
        if (meal.getId() == 0) {
            Meal newMeal = new Meal(generateId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
            meals.put(newMeal.getId(), newMeal);
        }
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
    public void update(Meal meal) {
        if (meals.containsKey(meal.getId())) {
            meals.put(meal.getId(), meal);
        }
    }

    @Override
    public void remove(int id) {
        if (meals.containsKey(id)) meals.remove(id);
    }

    private int generateId() {
        return id.incrementAndGet();
    }
}
