package com.github.smkozh.restaurantvoting.repository.dish;

import com.github.smkozh.restaurantvoting.model.MenuItem;

import java.util.List;

public interface MenuItemRepository {
    // null if updated dish does not belong to menuId
    MenuItem save(MenuItem menuItem, int menuId);

    // false if dish does not belong to menuId
    boolean delete(int id, int menuId);

    // null if dish does not belong to menuId
    MenuItem get(int id, int menuId);

    // ORDERED dateTime desc
    List<MenuItem> getAll(int menuId);
}
