package com.github.smkozh.restaurantvoting.repository.menu;

import com.github.smkozh.restaurantvoting.model.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu, Integer> {

    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Menu> getAllByRestaurantIdOrderByDateDesc (int id);

    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Menu> getAllByRestaurantIdAndDate(int id, LocalDate localDate);
}
