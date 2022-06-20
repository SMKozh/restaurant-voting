package com.github.smkozh.restaurantvoting.repository;

import com.github.smkozh.restaurantvoting.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM MenuItem mi WHERE mi.id=:id AND mi.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    List<MenuItem> getAllByRestaurant_Id(int restaurantId);

    Optional<MenuItem> findByIdAndRestaurantId(int id, int restaurantId);
}
