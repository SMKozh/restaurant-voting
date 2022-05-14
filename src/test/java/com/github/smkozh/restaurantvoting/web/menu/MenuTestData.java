package com.github.smkozh.restaurantvoting.web.menu;

import com.github.smkozh.restaurantvoting.model.Menu;
import com.github.smkozh.restaurantvoting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static com.github.smkozh.restaurantvoting.web.dish.DishTestData.*;

public class MenuTestData {
    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant", "dishes");
    public static final MatcherFactory.Matcher<Menu> MENU_WITH_DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant", "dishes.menu");

    public static final Menu menu1 = new Menu(1, LocalDate.now(), List.of(dish1, dish2));
    public static final Menu menu2 = new Menu(2, LocalDate.of(2020, 2, 15), List.of(dish3, dish4));
    public static final Menu menu3 = new Menu(3, LocalDate.now(), List.of(dish5, dish6));

    public static Menu getNew() {
        return new Menu(null, LocalDate.now().plusDays(5), null);
    }

    public static Menu getUpdated() {
        return new Menu(menu2.getId(), menu2.getDate().plusDays(2), List.of(dish3, dish4));
    }
}
