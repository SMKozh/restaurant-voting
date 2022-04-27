package com.github.smkozh.restaurantvoting.repository.menu;

import com.github.smkozh.restaurantvoting.model.Menu;

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
}
