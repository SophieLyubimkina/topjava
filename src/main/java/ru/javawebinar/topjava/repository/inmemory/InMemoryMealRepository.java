package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.MealsUtil.filterByPredicate;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        repository.put(1, new HashMap<Integer, Meal>());
        MealsUtil.meals.forEach(meal -> save(1, meal));
    }

    @Override
    public Meal save(Integer userId, Meal meal) {
        Map<Integer, Meal> userMeal = repository.get(userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMeal.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return userMeal.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(Integer userId, int id) {
        Map<Integer, Meal> userMeal = repository.get(userId);
        return userMeal != null && userMeal.remove(id) != null;
    }

    @Override
    public Meal get(Integer userId, int id) {
        Map<Integer, Meal> userMeal = repository.get(userId);
        return userMeal == null ? null : userMeal.get(id);
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        Map<Integer, Meal> userMeal = repository.get(userId);
        return CollectionUtils.isEmpty(userMeal) ? Collections.emptyList() :
                userMeal.values().stream()
                .sorted((meal1, meal2) -> meal2.getDateTime()
                        .compareTo(meal1.getDateTime())).collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer userId) {
        return filterByPredicate(userId, meal -> DateTimeUtil.isBetweenHalfOpen(
                meal.getDateTime(), startDateTime, endDateTime));
    }

    private List<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> meals = repository.get(userId);
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() :
                meals.values().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}

