package com.github.smkozh.restaurantvoting.web.dish;

import com.github.smkozh.restaurantvoting.model.Dish;
import com.github.smkozh.restaurantvoting.repository.dish.DishRepository;
import com.github.smkozh.restaurantvoting.util.validation.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

import static com.github.smkozh.restaurantvoting.util.validation.ValidationUtil.*;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
//@CacheConfig
public class AdminDishController {
    static final String REST_URL = "api/admin/menus/{menuId}/dishes";

    @Autowired
    private DishRepository repository;

    @GetMapping
    public List<Dish> getAll(@PathVariable int menuId) {
        log.info("get all for menu {}", menuId);
        return repository.getAll(menuId);
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id, @PathVariable int menuId) {
        log.info("get dish {} for menu {}", id, menuId);
        return checkNotFoundWithId(repository.get(id, menuId), id);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int menuId) {
        log.info("delete dish {} for menu {}", id, menuId);
        ValidationUtil.checkNotFoundWithId(repository.delete(id, menuId), id);
    }

    @Transactional
    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish, @PathVariable int menuId) {
        log.info("create {}", dish);
        Assert.notNull(dish, "dish must not be null");
        checkNew(dish);
        Dish created = repository.save(dish, menuId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(menuId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id, @PathVariable int menuId) {
        log.info("update {} for menu {}", id, menuId);
        assureIdConsistent(dish, id);
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(repository.save(dish, menuId), dish.id());
    }
}
