package com.github.smkozh.restaurantvoting.web.restaurant;

import com.github.smkozh.restaurantvoting.model.Menu;
import com.github.smkozh.restaurantvoting.model.Restaurant;
import com.github.smkozh.restaurantvoting.repository.menu.MenuRepository;
import com.github.smkozh.restaurantvoting.repository.restaurant.CrudRestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@CacheConfig(cacheNames = "restaurants")
public abstract class AbstractRestaurantController {

    @Autowired
    protected CrudRestaurantRepository repository;

    @Autowired
    protected MenuRepository menuRepository;

    @Cacheable
    public List<Restaurant> getAllWithMenus() {
        log.info("get all with menus");
        List<Restaurant> restaurants = repository.findAll();
        for (Restaurant restaurant : restaurants) {
            Menu menu = menuRepository.get(restaurant.getId(), LocalDate.now());
            if (menu != null) {
                restaurant.setMenus(List.of(menu));
            }
        }
        return restaurants;
    }
}
