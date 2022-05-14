package com.github.smkozh.restaurantvoting.web.vote;

import com.github.smkozh.restaurantvoting.model.Vote;
import com.github.smkozh.restaurantvoting.web.MatcherFactory;

import java.time.LocalDate;

import static com.github.smkozh.restaurantvoting.web.restaurant.RestaurantTestData.restaurant1;
import static com.github.smkozh.restaurantvoting.web.restaurant.RestaurantTestData.restaurant2;
import static com.github.smkozh.restaurantvoting.web.user.UserTestData.admin;
import static com.github.smkozh.restaurantvoting.web.user.UserTestData.user;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "restaurant", "user");
    public static final MatcherFactory.Matcher<Vote> VOTE_WITH_USER_AND_RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "restaurant.menus", "user.password");

    public static final Vote vote1 = new Vote(1, LocalDate.of(2020, 2, 15));
    public static final Vote vote2 = new Vote(2, LocalDate.of(2020, 2, 15));
    public static final Vote vote3 = new Vote(3, LocalDate.now());
    public static final Vote vote4 = new Vote(4, LocalDate.now());

    static {
        vote1.setUser(user);
        vote2.setUser(admin);
        vote3.setUser(user);
        vote4.setUser(admin);

        vote1.setRestaurant(restaurant2);
        vote2.setRestaurant(restaurant2);
        vote3.setRestaurant(restaurant1);
        vote4.setRestaurant(restaurant2);
    }

    public static Vote getNew() {
        return new Vote(null, LocalDate.now());
    }
}
