package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service("mealService")
public class MealServiceImpl implements MealService {

    @Autowired
    @Qualifier("mealRepository")
    private MealRepository repository;

    @Override
    public Meal create(Meal meal, int userId) {
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
        return MealsUtil.getFilteredWithExceeded(repository.getAllBetweenDates(startDate, finishDate, userId), authUserCaloriesPerDay, startTime, finishTime);
    }
}