package com.github.smkozh.restaurantvoting.web.menuitem;

import com.github.smkozh.restaurantvoting.model.MenuItem;
import com.github.smkozh.restaurantvoting.repository.MenuItemRepository;
import com.github.smkozh.restaurantvoting.repository.RestaurantRepository;
import com.github.smkozh.restaurantvoting.util.validation.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.github.smkozh.restaurantvoting.util.validation.ValidationUtil.*;

@RestController
@RequestMapping(value = AdminMenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminMenuItemController {
    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/menuItems";

    @Autowired
    private MenuItemRepository repository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping
    public List<MenuItem> getAll(@PathVariable int restaurantId) {
        log.info("get all for restaurant {}", restaurantId);
        return repository.getAllByRestaurant_Id(restaurantId);
    }

    @GetMapping("/{id}")
    public MenuItem get(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("get menuItem {} for restaurant {}", id, restaurantId);
        return checkNotFoundWithId(repository.findByIdAndRestaurantId(id, restaurantId)
                .orElse(null), id);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("delete menuItem {} for restaurant {}", id, restaurantId);
        ValidationUtil.checkNotFoundWithId(repository.delete(id, restaurantId) != 0, id);
    }

    @Transactional
    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> createWithLocation(@Valid @RequestBody MenuItem menuItem, @PathVariable int restaurantId) {
        log.info("create {}", menuItem);
        checkNew(menuItem);
        MenuItem created = save(menuItem, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuItem menuItem, @PathVariable int id, @PathVariable int restaurantId) {
        log.info("update {} for restaurant {}", id, restaurantId);
        assureIdConsistent(menuItem, id);
        checkNotFoundWithId(save(menuItem, restaurantId), menuItem.id());
    }

    @Transactional
    protected MenuItem save(MenuItem menuItem, int restaurantId) {
        if (!menuItem.isNew() && get(menuItem.id(), restaurantId) == null) {
            return null;
        }
        menuItem.setRestaurant(restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id = " + restaurantId + " not found")));
        return repository.save(menuItem);
    }
}
