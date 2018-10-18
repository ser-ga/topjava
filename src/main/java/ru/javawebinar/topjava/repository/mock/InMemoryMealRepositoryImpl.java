package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository("mealRepository")
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(e -> save(e, e.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            if (!repository.containsKey(userId)) repository.put(userId, new ConcurrentHashMap<>());
            getUserData(userId).put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        if (getUserData(userId) != null) {
            return getUserData(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        return getUserData(userId) != null  && getUserData(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return getUserData(userId) != null ? getUserData(userId).get(id) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getFiltered(getUserData(userId), meal -> true);
    }

    @Override
    public List<Meal> getAllBetweenDates(LocalDate startDate, LocalDate finishDate, int userId) {
        return getFiltered(getUserData(userId), meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, finishDate));
    }

    private List<Meal> getFiltered(Map<Integer, Meal> meals, Predicate<Meal> filter) {
        if (meals != null) return meals.values().stream()
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .filter(filter)
                .collect(Collectors.toList());
        return new ArrayList<>();
    }

    private Map<Integer, Meal> getUserData(int userId) {
        return repository.get(userId);
    }
}