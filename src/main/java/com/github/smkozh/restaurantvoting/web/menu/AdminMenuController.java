package com.github.smkozh.restaurantvoting.web.menu;

import com.github.smkozh.restaurantvoting.model.Menu;
import com.github.smkozh.restaurantvoting.repository.menu.MenuRepository;
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
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminMenuController {
    static final String REST_URL = "api/admin/restaurants/{restaurantId}/menus";

    @Autowired
    private MenuRepository repository;

    @GetMapping
    public List<Menu> getAll(@PathVariable int restaurantId) {
        log.info("get all for restaurant {}", restaurantId);
        return repository.getAll(restaurantId);
    }

    @GetMapping("/{id}")
    public Menu get(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("get menu {} for restaurant {}", id, restaurantId);
        return checkNotFoundWithId(repository.get(id, restaurantId), id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("delete menu {} for restaurant {}", id, restaurantId);
        ValidationUtil.checkNotFoundWithId(repository.delete(id, restaurantId), id);
    }

    @Transactional
    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody Menu menu, @PathVariable int restaurantId) {
        log.info("create {} for restaurant {}", menu, restaurantId);
        Assert.notNull(menu, "menu must not be null");
        checkNew(menu);
        Menu created = repository.save(menu, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Menu menu, @PathVariable int id, @PathVariable int restaurantId) {
        log.info("update {} for restaurant {}", id, restaurantId);
        assureIdConsistent(menu, id);
        Assert.notNull(menu, "menu must not be null");
        checkNotFoundWithId(repository.save(menu, restaurantId), menu.id());
    }
}
