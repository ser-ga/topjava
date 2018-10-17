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

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
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
        checkNew(meal);
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

    public void update(Meal meal, int id) {
        log.info("update {}", meal);
        assureIdConsistent(meal, id);
        service.update(meal,  getAuthUserId());
    }

    public List<MealWithExceed> getAll() {
        log.info("list all by userId={}", getAuthUserId());
        return service.getAll(getAuthUserId(), authUserCaloriesPerDay());
    }

    public List<MealWithExceed> getFiltered(LocalDate startDate, LocalDate finishDate, LocalTime startTime, LocalTime finishTime) {
        log.info("list filtered by userId={}", getAuthUserId());
        return service.getFiltered(
                startDate == null ? LocalDate.MIN : startDate,
                finishDate == null ? LocalDate.MAX : finishDate,
                startTime == null ? LocalTime.MIN : startTime,
                finishTime == null ? LocalTime.MAX : finishTime,
                getAuthUserId(),
                authUserCaloriesPerDay()
        );
    }

}