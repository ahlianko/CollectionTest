package ua.kpi.tef.util;

import com.sun.xml.internal.xsom.impl.scd.Iterators;
import ua.kpi.tef.model.UserMeal;
import ua.kpi.tef.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
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
        List<UserMealWithExceed> res = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(21, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
        for (UserMealWithExceed um : res
                ) {
            System.out.println(um.toString());
        }
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        HashMap<LocalDate, Integer> map = new HashMap<>();
        ArrayList<UserMealWithExceed> resultHelp = new ArrayList<>();
        ArrayList<LocalDate> help = new ArrayList<>();
        for (int i = 0; i < mealList.size(); i++) {
            if (mealList.get(i).getDateTime().toLocalTime().isAfter(startTime) && mealList.get(i).getDateTime().toLocalTime().isBefore(endTime)) {
                map.put(mealList.get(i).getDateTime().toLocalDate(),
                        map.getOrDefault(mealList.get(i).getDateTime().toLocalDate(), 0) +
                                mealList.get(i).getCalories());
            }
        }

        for (UserMeal um : mealList
                ) {
            if (map.keySet().contains(um.getDateTime().toLocalDate()) && (map.get(um.getDateTime().toLocalDate()) > caloriesPerDay)) {
                resultHelp.add(new UserMealWithExceed(um.getDateTime(), um.getDescription(), um.getCalories(), true));
            }
        }
        return resultHelp;

    }
}