package ru.javawebinar.topjava.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Integer userId, Meal meal) {
        return repository.save(userId, meal);
    }

    public void delete(Integer userId, int id) {
        checkNotFoundWithId(repository.delete(userId, id), id);
    }

    public Meal get(Integer userId, int id) {
        return checkNotFoundWithId(repository.get(userId, id), id);
    }

    public List<Meal> getAll(Integer userId) {

        return repository.getAll(userId);
    }

    public void update(Integer userId, Meal meal) {

        checkNotFoundWithId(repository.save(userId, meal), meal.getId());
    }

    public List<Meal> getBetweenInclusive(Integer userId, @Nullable LocalDate startDate,
                                          @Nullable LocalDate endDate){
        return repository.getBetweenHalfOpen(DateTimeUtil.atStartOfDayOrMin(startDate),
                DateTimeUtil.atStartOfNextDayOrMax(endDate), userId);
    }

}