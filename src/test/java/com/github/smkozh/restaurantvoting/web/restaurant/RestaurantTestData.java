package com.github.smkozh.restaurantvoting.web.restaurant;

import com.github.smkozh.restaurantvoting.model.Restaurant;
import com.github.smkozh.restaurantvoting.web.MatcherFactory;

import java.util.List;

import static com.github.smkozh.restaurantvoting.web.menu.MenuTestData.*;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menus");
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menus.restaurant", "menus.dishes.menu");

    public static final Restaurant restaurant1 = new Restaurant(1, "new york pizza", List.of(menu1));
    public static final Restaurant restaurant2 = new Restaurant(2, "clever irish pub", List.of(menu3, menu2));
    public static final Restaurant restaurant2WithTodayMenu = new Restaurant(2, "clever irish pub", List.of(menu3));
    public static final Restaurant restaurant3 = new Restaurant(3, "sushi make", null);

    public static Restaurant getNew() {
        return new Restaurant(null, "new Restaurant", null);
    }

    public static Restaurant getUpdated() {
        return new Restaurant(restaurant2.id(), "updated " + restaurant2.getName(), restaurant2.getMenus());
    }
}
