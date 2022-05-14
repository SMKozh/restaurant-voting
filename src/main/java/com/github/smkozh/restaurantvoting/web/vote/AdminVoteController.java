package com.github.smkozh.restaurantvoting.web.vote;

import com.github.smkozh.restaurantvoting.model.Vote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminVoteController extends AbstractVoteController {

    static final String REST_URL = "/api/admin/votes";

    @GetMapping("/{restaurantId}")
    public List<Vote> getAllForRestaurant(@PathVariable int restaurantId) {
        log.info("get all for restaurant {}", restaurantId);
        return repository.getAllByRestaurantIdOrderByDateAsc(restaurantId);
    }

    @GetMapping("/by-date/{restaurantId}")
    public List<Vote> getAllForRestaurantAndDate(@PathVariable int restaurantId, @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get all for restaurant {} and date {}", restaurantId, date);
        return repository.getAllByRestaurantIdAndDateOrderByDateAsc(restaurantId, date);
    }

    @GetMapping("/count/{restaurantId}")
    public int getCountForRestaurant(@PathVariable int restaurantId) {
        return getAllForRestaurant(restaurantId).size();
    }
}
