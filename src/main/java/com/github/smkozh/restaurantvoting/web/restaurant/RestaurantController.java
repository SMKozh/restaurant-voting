package com.github.smkozh.restaurantvoting.web.restaurant;

import com.github.smkozh.restaurantvoting.model.Restaurant;
import com.github.smkozh.restaurantvoting.repository.RestaurantRepository;
import com.github.smkozh.restaurantvoting.util.validation.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RestaurantController {
    static final String REST_URL = "/api/restaurants";

    @Autowired
    protected RestaurantRepository repository;

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return ValidationUtil.checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @Cacheable(value = "restaurants")
    @GetMapping("/with-menuItems")
    public List<Restaurant> getAllWithMenuItems() {
        log.info("get all with today menuItems");
        return repository.getAllWithMenuItems(LocalDate.now());
    }

    @GetMapping("/{id}/with-menuItems")
    public Restaurant getWithMenuItems(@PathVariable int id) {
        log.info("get {} with today menuItems", id);
        return ValidationUtil.checkNotFoundWithId(repository.getWithMenuItemsByDate(id, LocalDate.now()).orElse(null), id);
    }
}
