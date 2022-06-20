package com.github.smkozh.restaurantvoting.web.vote;

import com.github.smkozh.restaurantvoting.model.Vote;
import com.github.smkozh.restaurantvoting.repository.VoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminVoteController {

    static final String REST_URL = "/api/admin/votes";

    @Autowired
    protected VoteRepository repository;

    @GetMapping("/by-date")
    public List<Vote> getAllForRestaurantAndDate(@RequestParam int restaurantId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get all for restaurant {} and date {}", restaurantId, date);
        return repository.getAllByRestaurantIdAndDateOrderByDateAsc(restaurantId, date);
    }
}
