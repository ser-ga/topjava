package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;

import static java.time.LocalDateTime.now;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.create(new Meal(authUserId(), now(), "Завтрак", 1000));
            System.out.println(mealRestController.getAll());
            mealRestController.delete(1);
            mealRestController.delete(6);
            System.out.println(mealRestController.getAll());
            mealRestController.update(new Meal(8, authUserId(), now(), "Чай", 50));
            System.out.println(mealRestController.getAll());
            mealRestController.get(5);
        }
    }
}
