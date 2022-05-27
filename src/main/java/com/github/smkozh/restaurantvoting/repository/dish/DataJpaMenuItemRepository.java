package com.github.smkozh.restaurantvoting.repository.dish;

import com.github.smkozh.restaurantvoting.model.MenuItem;
import com.github.smkozh.restaurantvoting.repository.restaurant.CrudRestaurantRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaMenuItemRepository implements MenuItemRepository {

    private final CrudMenuItemRepository crudMenuItemRepository;

    private final CrudRestaurantRepository crudRestaurantRepository;

    public DataJpaMenuItemRepository(CrudMenuItemRepository crudMenuItemRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudMenuItemRepository = crudMenuItemRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public MenuItem save(MenuItem menuItem, int restaurantId) {
        if (!menuItem.isNew() && get(menuItem.id(), restaurantId) == null) {
            return null;
        }
        menuItem.setRestaurant(crudRestaurantRepository.getById(restaurantId));
        return crudMenuItemRepository.save(menuItem);
    }

    @Override
    public boolean delete(int id, int menuId) {
        return crudMenuItemRepository.delete(id, menuId) != 0;
    }

    @Override
    public MenuItem get(int id, int restaurantId) {
        return crudMenuItemRepository.findById(id)
                .filter(dish -> dish.getRestaurant().id() == restaurantId)
                .orElse(null);
    }

    @Override
    public List<MenuItem> getAll(int restaurantId) {
        return crudMenuItemRepository.getAllByRestaurant_Id(restaurantId);
    }
}
