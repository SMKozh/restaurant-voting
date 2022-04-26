package com.github.smkozh.restaurantvoting.repository.dish;

import org.springframework.stereotype.Repository;

@Repository
public class DataJpaDishRepository {
    private final DishRepository repository;

    public DataJpaDishRepository(DishRepository repository) {
        this.repository = repository;
    }

    //    @Override
    public boolean delete(int id, int userId) {
        return repository.delete(id) != 0;
    }
}
