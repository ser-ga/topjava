package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

;


public class MealTestData {

    public static final int ADMIN_MEAL_ID1 = START_SEQ + 2;
    public static final int ADMIN_MEAL_ID2 = START_SEQ + 3;
    public static final int ADMIN_MEAL_ID3 = START_SEQ + 4;
    public static final int ADMIN_MEAL_ID4 = START_SEQ + 5;
    public static final int USER_MEAL_ID1 = START_SEQ + 6;
    public static final int USER_MEAL_ID2 = START_SEQ + 7;
    public static final int USER_MEAL_ID3 = START_SEQ + 8;
    public static final int USER_MEAL_ID4 = START_SEQ + 9;

    public static final Meal ADMIN_MEAL1 = new Meal(ADMIN_MEAL_ID1, LocalDateTime.of(2015, Month.JUNE, 1, 7, 0), "Админ утренний кофе с пирожком", 310);
    public static final Meal ADMIN_MEAL2 = new Meal(ADMIN_MEAL_ID2, LocalDateTime.of(2015, Month.JUNE, 20, 12, 0), "Админ обед", 1110);
    public static final Meal ADMIN_MEAL3 = new Meal(ADMIN_MEAL_ID3, LocalDateTime.of(2015, Month.JUNE, 20, 14, 0), "Админ ланч", 510);
    public static final Meal ADMIN_MEAL4 = new Meal(ADMIN_MEAL_ID4, LocalDateTime.of(2015, Month.JUNE, 20, 21, 0), "Админ ужин", 1510);
    public static final Meal USER_MEAL1 = new Meal(USER_MEAL_ID1, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Юзер ночной кофе с таком", 120);
    public static final Meal USER_MEAL2 = new Meal(USER_MEAL_ID2, LocalDateTime.of(2015, Month.JUNE, 20, 21, 0), "Юзер завтрак", 820);
    public static final Meal USER_MEAL3 = new Meal(USER_MEAL_ID3, LocalDateTime.of(2015, Month.JUNE, 20, 21, 0), "Юзер ланч", 520);
    public static final Meal USER_MEAL4 = new Meal(USER_MEAL_ID4, LocalDateTime.of(2015, Month.JUNE, 20, 21, 0), "Юзер ужин", 1400);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
