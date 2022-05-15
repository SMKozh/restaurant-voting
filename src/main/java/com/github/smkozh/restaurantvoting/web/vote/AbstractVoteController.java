package com.github.smkozh.restaurantvoting.web.vote;

import com.github.smkozh.restaurantvoting.error.IllegalRequestDataException;
import com.github.smkozh.restaurantvoting.model.Vote;
import com.github.smkozh.restaurantvoting.repository.user.UserRepository;
import com.github.smkozh.restaurantvoting.repository.restaurant.CrudRestaurantRepository;
import com.github.smkozh.restaurantvoting.repository.vote.CrudVoteRepository;
import com.github.smkozh.restaurantvoting.web.SecurityUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
public abstract class AbstractVoteController {

    @Autowired
    protected CrudVoteRepository repository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CrudRestaurantRepository restaurantRepository;

    @Setter
    protected static LocalTime deadLine = LocalTime.of(11, 0);

    public Vote create(int restaurantId) {
        int userId = SecurityUtil.authId();
        Vote oldVote = repository.getByUserIdAndDate(userId, LocalDate.now());
        Vote created;
        if (oldVote == null) {
            log.info("create vote");
            Vote vote = new Vote();
            vote.setRestaurant(restaurantRepository.getById(restaurantId));
            vote.setDate(LocalDate.now());
            vote.setUser(userRepository.getById(userId));
            created = repository.save(vote);
        } else {
            if (LocalTime.now().isAfter(deadLine)) {
                throw new IllegalRequestDataException("You have already voted");
            } else {
                log.info("update vote {}", oldVote.getId());
                oldVote.setRestaurant(restaurantRepository.getById(restaurantId));
                created = repository.save(oldVote);
            }
        }
        return created;
    }
}
