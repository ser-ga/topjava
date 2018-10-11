package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        final Map<LocalDate, Integer> datesAndCalories = new HashMap<>();
        return mealList.stream()
                .peek(e -> datesAndCalories.merge(getDate(e), e.getCalories(), Integer::sum))
                .sorted(Comparator.comparing(UserMeal::getDateTime))
                .filter(e -> TimeUtil.isBetween(getTime(e), startTime, endTime))
                .map(e -> new UserMealWithExceed(
                        e.getDateTime(),
                        e.getDescription(),
                        e.getCalories(),
                        datesAndCalories.get(getDate(e)) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> alternativeGetFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> datesAndCalories = new HashMap<>();
        for (UserMeal userMeal : mealList) {
            datesAndCalories.merge(getDate(userMeal), userMeal.getCalories(), Integer::sum);
        }
        List<UserMealWithExceed> result = new ArrayList<>();
        for (UserMeal meal : mealList) {
            if (TimeUtil.isBetween(getTime(meal), startTime, endTime)) {
                result.add(new UserMealWithExceed(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        datesAndCalories.get(getDate(meal)) > caloriesPerDay));
            }
        }
        return result;
    }

    private static LocalDate getDate(UserMeal userMeal) {
        return userMeal.getDateTime().toLocalDate();
    }

    private static LocalTime getTime(UserMeal userMeal) {
        return userMeal.getDateTime().toLocalTime();
    }
}
