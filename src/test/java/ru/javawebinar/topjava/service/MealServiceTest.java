package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL_ID1, ADMIN_ID);
        assertMatch(meal, MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void foreignGet() throws Exception {
        Meal meal = service.get(MEAL_ID3, ADMIN_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL_ID1, ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), MEAL2);
    }

    @Test(expected = NotFoundException.class)
    public void foreignDelete() throws Exception {
        service.delete(MEAL_ID3, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        List<Meal> meals = service.getBetweenDates(LocalDate.of(2015, 6, 1), LocalDate.MAX, ADMIN_ID);
        assertMatch(meals, MEAL2, MEAL1);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> meals = service.getBetweenDateTimes(LocalDateTime.MIN, LocalDateTime.of(2015, 6, 1, 14, 0),ADMIN_ID);
        assertMatch(meals, MEAL1);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(ADMIN_ID);
        assertMatch(all, MEAL2, MEAL1);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("Админский завтрак");
        updated.setCalories(450);
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(MEAL_ID1, ADMIN_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void foreignUpdate() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setId(MEAL_ID3);
        service.update(updated, ADMIN_ID);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.now(), "Админский обэд", 1555);
        Meal created = service.create(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(ADMIN_ID), newMeal, MEAL2, MEAL1);
    }
}