package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service("mealService")
public class MealServiceImpl implements MealService {

    @Autowired
    @Qualifier("mealRepository")
    private MealRepository repository;

    @Override
    public Meal create(Meal meal, int userId) {
        checkNew(meal);
        return repository.save(meal, userId);
    }

    @Override
    public Meal update(Meal meal, int userId) {
        return checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    @Override
    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public List<MealWithExceed> getAll(int userId, int authUserCaloriesPerDay) {
        return MealsUtil.getWithExceeded(repository.getAll(userId), authUserCaloriesPerDay);
    }

    @Override
    public List<MealWithExceed> getFiltered(LocalDate startDate, LocalDate finishDate, LocalTime startTime, LocalTime finishTime, int userId, int authUserCaloriesPerDay) {
        boolean needDateFilter = (startDate != null) || (finishDate != null);
        boolean needTimeFilter = (startTime != null) || (finishTime != null);
        List<MealWithExceed> result;
        if (needDateFilter) result = MealsUtil.getWithExceeded(
                repository.getAllBetweenDates(
                        startDate == null ? LocalDate.MIN : startDate,
                        finishDate == null ? LocalDate.MAX : finishDate,
                        userId),
                authUserCaloriesPerDay);
        else result = getAll(userId, authUserCaloriesPerDay);
        if (result.isEmpty() || !needTimeFilter) return result;
        return result.stream()
                .filter(e -> DateTimeUtil.isBetween(
                        e.getDateTime().toLocalTime(),
                        startTime == null ? LocalTime.MIN : startTime,
                        finishTime == null ? LocalTime.MAX : finishTime
                ))
                .collect(Collectors.toList());
    }
}