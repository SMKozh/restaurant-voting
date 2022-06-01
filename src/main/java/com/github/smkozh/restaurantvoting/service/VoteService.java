package com.github.smkozh.restaurantvoting.service;

import com.github.smkozh.restaurantvoting.error.IllegalRequestDataException;
import com.github.smkozh.restaurantvoting.model.Vote;
import com.github.smkozh.restaurantvoting.repository.restaurant.CrudRestaurantRepository;
import com.github.smkozh.restaurantvoting.repository.user.UserRepository;
import com.github.smkozh.restaurantvoting.repository.vote.CrudVoteRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
@Slf4j
public class VoteService {

    @Autowired
    protected CrudVoteRepository repository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CrudRestaurantRepository restaurantRepository;

    @Setter
    protected static LocalTime deadLine = LocalTime.of(11, 0);

    @Transactional
    public Vote create(int restaurantId, int userId) {
        if (repository.getByUserIdAndDate(userId, LocalDate.now()).isPresent()) {
            throw new IllegalRequestDataException("Your today vote is already created");
        }
        log.info("create vote");
        Vote vote = new Vote();
        vote.setRestaurant(restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id = " + restaurantId + " not found")));
        vote.setDate(LocalDate.now());
        vote.setUser(userRepository.getById(userId));
        return repository.save(vote);
    }

    @Transactional
    public void update(int voteId, int restaurantId, int userId) {
        if (LocalTime.now().isAfter(deadLine)) {
            throw new IllegalRequestDataException("You can't change your vote after 11:00");
        } else {
            Vote oldVote = repository.findById(voteId)
                    .filter(v -> v.getDate().isEqual(LocalDate.now()))
                    .filter(v -> v.getUser().id() == userId)
                    .orElseThrow(() -> new EntityNotFoundException("Vote with id = " + voteId + "not found"));
            log.info("update vote {}", oldVote.getId());
            oldVote.setRestaurant(restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new EntityNotFoundException("Restaurant with id = " + restaurantId + " not found")));
            repository.save(oldVote);
        }
    }

    public Vote get(LocalDate date, int userId) {
        return repository.findByDateAndUserId(date, userId)
                .orElseThrow(() -> new EntityNotFoundException("Vote for " + date + " not found"));
    }
}
