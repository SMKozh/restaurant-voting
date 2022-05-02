package com.github.smkozh.restaurantvoting.web.vote;

import com.github.smkozh.restaurantvoting.model.Vote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
//@CacheConfig
public class AdminVoteController extends AbstractVoteController {

    static final String REST_URL = "api/admin/votes";

    @GetMapping("/{restaurantId}")
    public List<Vote> getAllForRestaurant(@PathVariable int restaurantId) {
        log.info("get all for restaurant {}", restaurantId);
        return repository.getAllByRestaurantId(restaurantId);
    }

    @GetMapping("/count/{restaurantId}")
    public int getCountForRestaurant(@PathVariable int restaurantId) {
        return getAllForRestaurant(restaurantId).size();
    }
}
