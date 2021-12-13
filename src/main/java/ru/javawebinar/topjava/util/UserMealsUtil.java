package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        /*List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);*/

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles

        List<UserMealWithExcess> filteredList = new ArrayList<>();
        Map<LocalDate, Integer> calloriesPerDayMap = new HashMap<>();

        for( UserMeal uMeal : meals) {
            LocalDate tempLocalDate = uMeal.getDateTime().toLocalDate();
            calloriesPerDayMap.computeIfPresent(tempLocalDate, (key, value) -> value +=uMeal.getCalories());
            calloriesPerDayMap.putIfAbsent(tempLocalDate, uMeal.getCalories());
        }

        for(UserMeal meal : meals){
            LocalTime lt = meal.getDateTime().toLocalTime();
            if(TimeUtil.isBetweenHalfOpen(lt, startTime, endTime)){
                filteredList.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(),
                        meal.getCalories(), (calloriesPerDayMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay)));
            }

        }
        return filteredList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        Map<LocalDate, Integer> calloriesPerDayMap = meals.stream().collect(Collectors.toMap(m -> m.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));

        /*meals.stream().forEach(m -> calloriesPerDayMap.merge(m.getDateTime().toLocalDate(),
                m.getCalories(),
                (old_value, new_value) -> old_value + new_value));*/

        return meals.stream().filter(m -> TimeUtil.isBetweenHalfOpen(m.getDateTime().toLocalTime(), startTime, endTime))
                .map(m -> new UserMealWithExcess(m.getDateTime(), m.getDescription(),
                        m.getCalories(), calloriesPerDayMap.get(m.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());

    }
}
