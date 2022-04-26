package com.github.smkozh.restaurantvoting.web.restaurant;

import com.github.smkozh.restaurantvoting.model.Menu;
import com.github.smkozh.restaurantvoting.model.Restaurant;
import com.github.smkozh.restaurantvoting.repository.menu.MenuRepository;
import com.github.smkozh.restaurantvoting.repository.restaurant.CrudRestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminRestaurantController {
    static final String REST_URL = "api/admin/restaurants";

    @Autowired
    private CrudRestaurantRepository repository;

    @Autowired
    private MenuRepository menuRepository;

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all");
        List<Restaurant> list = repository.findAll();
        return list;
//        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        return ResponseEntity.of(repository.findById(id));
    }

    @Transactional
    @GetMapping("/with-all-menus/{id}")
    public ResponseEntity<Restaurant> getWithMenus(@PathVariable int id) {
        Restaurant restaurant = repository.findById(id).orElse(null);
        List<Menu> menus = menuRepository.getAllByRestaurantIdOrderByDateDesc(restaurant.getId());
        restaurant.setMenus(menus);
        return ResponseEntity.ok().body(restaurant);
    }

    @Transactional
    @GetMapping("/with-today-menu/{id}")
    public ResponseEntity<Restaurant> getWithTodayMenu(@PathVariable int id) {
        Restaurant restaurant = repository.findById(id).orElse(null);
        List<Menu> menu = menuRepository.getAllByRestaurantIdAndDate(restaurant.getId(), LocalDate.of(2020, 9, 6));
        restaurant.setMenus(menu);
        return ResponseEntity.ok().body(restaurant);
    }

}
