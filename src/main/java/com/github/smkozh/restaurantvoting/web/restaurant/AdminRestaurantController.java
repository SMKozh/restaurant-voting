package com.github.smkozh.restaurantvoting.web.restaurant;

import com.github.smkozh.restaurantvoting.model.Menu;
import com.github.smkozh.restaurantvoting.model.Restaurant;
import com.github.smkozh.restaurantvoting.util.validation.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.github.smkozh.restaurantvoting.util.validation.ValidationUtil.*;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminRestaurantController extends AbstractRestaurantController {
    static final String REST_URL = "/api/admin/restaurants";

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return ValidationUtil.checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @Transactional
    @GetMapping("/with-menus")
    public List<Restaurant> getAllWithTodayMenus() {
        return super.getAllWithTodayMenus();
    }

    @Transactional
    @GetMapping("/with-all-menus/{id}")
    public Restaurant getWithMenus(@PathVariable int id) {
        log.info("get {} with menus", id);
        Restaurant restaurant = ValidationUtil.checkNotFoundWithId(repository.findById(id).orElse(null), id);
        List<Menu> menus = menuRepository.getAll(restaurant.getId());
        if (menus != null) {
            restaurant.setMenus(menus);
        }
        return restaurant;
    }

    @Transactional
    @GetMapping("/with-today-menu/{id}")
    public Restaurant getWithTodayMenu(@PathVariable int id) {
        log.info("get {} with today menu", id);
        Restaurant restaurant = ValidationUtil.checkNotFoundWithId(repository.findById(id).orElse(null), id);
        Menu menu = menuRepository.get(restaurant.getId(), LocalDate.now());
        if (menu != null) {
            restaurant.setMenus(List.of(menu));
        }
        return restaurant;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        ValidationUtil.checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Transactional
    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNew(restaurant);
        Restaurant created = repository.save(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(repository.save(restaurant), id);
    }
}
