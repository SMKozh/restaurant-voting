package com.github.smkozh.restaurantvoting.repository.vote;

import com.github.smkozh.restaurantvoting.model.Vote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @EntityGraph(attributePaths = {"user", "restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Vote> getAllByRestaurantIdOrderByDateAsc(int restaurantId);

    @EntityGraph(attributePaths = {"user", "restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Vote> getAllByRestaurantIdAndDateOrderByDateAsc(int restaurantId, LocalDate date);

    Vote getByUserIdAndDate(int userId, LocalDate date);
}
