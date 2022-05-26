package com.github.smkozh.restaurantvoting.web.dish;

import com.github.smkozh.restaurantvoting.model.Dish;
import com.github.smkozh.restaurantvoting.web.MatcherFactory;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "menu");

    public static final Dish dish1 = new Dish(1, "pepsi", 100.0);
    public static final Dish dish2 = new Dish(2, "pizza", 150.0);
    public static final Dish dish3 = new Dish(3, "beer", 200.0);
    public static final Dish dish4 = new Dish(4, "steak", 500.0);
    public static final Dish dish5 = new Dish(5, "dark beer", 250.0);
    public static final Dish dish6 = new Dish(6, "chips", 500.0);

    public static Dish getNew() {
        return new Dish(null, "new Dish", 1000.0);
    }

    public static Dish getUpdated() {
        return new Dish(dish3.id(), "updated " + dish3.getName(), dish3.getPrice() + 99);
    }
}
