package com.github.smkozh.restaurantvoting.web.dish;

import com.github.smkozh.restaurantvoting.model.Dish;
import com.github.smkozh.restaurantvoting.repository.dish.DishRepository;
import com.github.smkozh.restaurantvoting.util.validation.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.github.smkozh.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
//@CacheConfig
public class AdminDishController {
    static final String REST_URL = "api/admin/dishes";

    @Autowired
    private DishRepository repository;

    @GetMapping
    public List<Dish> getAll() {
        log.info("get all");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        log.info("get dish {}", id);
        return repository.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete dish {}", id);
        ValidationUtil.checkNotFoundWithId(repository.delete(id) != 0, id);
        repository.deleteById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish) {
        log.info("create {}", dish);
        checkNew(dish);
        Dish created = repository.save(dish);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id) {
        repository.save(dish);
    }
}
