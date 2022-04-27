package com.github.smkozh.restaurantvoting.repository.dish;

import com.github.smkozh.restaurantvoting.model.Dish;
import com.github.smkozh.restaurantvoting.repository.menu.CrudMenuRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaDishRepository implements DishRepository {

    private final CrudDishRepository crudDishRepository;

    private final CrudMenuRepository crudMenuRepository;

    public DataJpaDishRepository(CrudDishRepository crudDishRepository, CrudMenuRepository crudMenuRepository) {
        this.crudDishRepository = crudDishRepository;
        this.crudMenuRepository = crudMenuRepository;
    }

    @Override
    public Dish save(Dish dish, int menuId) {
        if (!dish.isNew() && get(dish.getId(), menuId) == null) {
            return null;
        }
        dish.setMenu(crudMenuRepository.getById(menuId));
        return crudDishRepository.save(dish);
    }

    @Override
    public boolean delete(int id, int menuId) {
        return crudDishRepository.deleteByIdAndMenu_Id(id, menuId) != 0;
    }

    @Override
    public Dish get(int id, int menuId) {
        return crudDishRepository.findById(id)
                .filter(dish -> dish.getMenu().getId() == menuId)
                .orElse(null);
    }

    @Override
    public List<Dish> getAll(int menuId) {
        return crudDishRepository.getAllByMenu_Id(menuId);
    }
}
