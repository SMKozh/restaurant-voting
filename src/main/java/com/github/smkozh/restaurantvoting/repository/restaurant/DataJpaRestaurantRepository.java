package com.github.smkozh.restaurantvoting.repository.restaurant;

public class DataJpaRestaurantRepository implements RestaurantRepository {
    private CrudRestaurantRepository repository;

    public DataJpaRestaurantRepository(CrudRestaurantRepository repository) {
        this.repository = repository;
    }


}
