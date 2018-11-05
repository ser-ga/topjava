package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    Meal getByIdAndUserIs(Integer id, User user);

    List<Meal> findAllByUserOrderByDateTimeDesc(User user);

    List<Meal> findAllByDateTimeIsBetweenAndUserIsOrderByDateTimeDesc(LocalDateTime startDate, LocalDateTime endDate, User user);
}
