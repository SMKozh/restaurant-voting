package com.github.smkozh.restaurantvoting.repository.menu;

import com.github.smkozh.restaurantvoting.model.Menu;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepository {
    // null if updated menu does not belong to restaurantId
    Menu save(Menu menu, int restaurantId);

    // false if menu does not belong to restaurantId
    boolean delete(int id, int restaurantId);

    // null if menu does not belong to restaurantId
    Menu get(int id, int restaurantId);

    // ORDERED dateTime desc
    List<Menu> getAll(int restaurantId);

    // null if restaurant does not have menu with this date
    Menu get(int restaurantId, LocalDate localDate);
}
