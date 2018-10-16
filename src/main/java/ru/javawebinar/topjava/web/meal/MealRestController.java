package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.getAuthUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("mealService")
    private MealService service;

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        return service.create(meal, getAuthUserId());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, getAuthUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, getAuthUserId());
    }

    public void update(Meal meal) {
        log.info("update {}", meal);
        service.update(meal, getAuthUserId());
    }

    public List<MealWithExceed> getAll() {
        log.info("list all by userId={}", getAuthUserId());
        return service.getAll(getAuthUserId(), authUserCaloriesPerDay());
    }

    public List<MealWithExceed> getFiltered(LocalDate startDate, LocalDate finishDate, LocalTime startTime, LocalTime finishTime) {
        log.info("list filtered by userId={}", getAuthUserId());
        return service.getFiltered(startDate, finishDate, startTime, finishTime, getAuthUserId(), authUserCaloriesPerDay());
    }

}