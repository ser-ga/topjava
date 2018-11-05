package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    @Autowired
    private CrudMealRepository mealRepository;

    @Autowired
    private CrudUserRepository userRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }
        User user = userRepository.getOne(userId);
        meal.setUser(user);
        return mealRepository.save(meal);
    }


    @Override
    public boolean delete(int id, int userId) {
        if (get(id, userId) != null) {
            mealRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        User user = userRepository.getOne(userId);
        return mealRepository.getByIdAndUserIs(id, user);
    }

    @Override
    public List<Meal> getAll(int userId) {
        User user = userRepository.getOne(userId);
        return mealRepository.findAllByUserOrderByDateTimeDesc(user);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        User user = userRepository.getOne(userId);
        return mealRepository.findAllByDateTimeIsBetweenAndUserIsOrderByDateTimeDesc(startDate, endDate, user);
    }
}
