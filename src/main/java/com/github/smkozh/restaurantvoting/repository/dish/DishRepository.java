package com.github.smkozh.restaurantvoting.repository.dish;

import com.github.smkozh.restaurantvoting.model.Dish;

import java.util.List;

public interface DishRepository {
    // null if updated dish does not belong to menuId
    Dish save(Dish dish, int menuId);

    // false if dish does not belong to menuId
    boolean delete(int id, int menuId);

    // null if dish does not belong to menuId
    Dish get(int id, int menuId);

    // ORDERED dateTime desc
    List<Dish> getAll(int menuId);
}
