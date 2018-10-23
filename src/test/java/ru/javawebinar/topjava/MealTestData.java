package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

;


public class MealTestData {

    public static final int MEAL_ID1 = START_SEQ;
    public static final int MEAL_ID2 = START_SEQ + 1;
    public static final int MEAL_ID3 = START_SEQ + 2;
    public static final int MEAL_ID4 = START_SEQ + 3;

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final Meal MEAL1 = new Meal(MEAL_ID1, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final Meal MEAL2 = new Meal(MEAL_ID2, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1510);
    public static final Meal MEAL3 = new Meal(MEAL_ID3, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Юзер ланч", 520);
    public static final Meal MEAL4 = new Meal(MEAL_ID4, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Юзер ужин", 1400);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
