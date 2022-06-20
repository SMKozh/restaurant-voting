package com.github.smkozh.restaurantvoting.repository;

import com.github.smkozh.restaurantvoting.model.Vote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @EntityGraph(attributePaths = "restaurant", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Vote> findByDateAndUserId(LocalDate date, int userId);

    List<Vote> getAllByRestaurantIdAndDateOrderByDateAsc(int restaurantId, LocalDate date);
}
