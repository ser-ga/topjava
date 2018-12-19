package ru.javawebinar.topjava.util.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

public class MealDatetimeValidator implements Validator {

    @Autowired
    private MealRepository mealRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Meal.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AuthorizedUser authorizedUser = SecurityUtil.safeGet();
        Meal meal = (Meal) target;
        List<Meal> meals = mealRepository.getBetween(meal.getDateTime(), meal.getDateTime(), authorizedUser.getId());
        if (meals.size() == 1) {
            errors.rejectValue("dateTime", "validate.datetime", "Meal with this dateTime already exists");
        }
    }
}
