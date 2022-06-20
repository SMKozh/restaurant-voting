package com.github.smkozh.restaurantvoting.web.menuitem;

import com.github.smkozh.restaurantvoting.model.MenuItem;
import com.github.smkozh.restaurantvoting.web.MatcherFactory;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MenuItemTestData {
    public static final MatcherFactory.Matcher<MenuItem> MENU_ITEM_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MenuItem.class, "restaurant");

    public static final MenuItem MENU_ITEM_1 = new MenuItem(1, "pepsi", new BigDecimal("100.00"), LocalDate.now());
    public static final MenuItem MENU_ITEM_2 = new MenuItem(2, "pizza", new BigDecimal("150.00"), LocalDate.now());
    public static final MenuItem MENU_ITEM_3 = new MenuItem(3, "beer", new BigDecimal("200.00"), LocalDate.of(2020, 2, 15));
    public static final MenuItem MENU_ITEM_4 = new MenuItem(4, "steak", new BigDecimal("500.00"), LocalDate.of(2020, 2, 15));
    public static final MenuItem MENU_ITEM_5 = new MenuItem(5, "dark beer", new BigDecimal("250.00"), LocalDate.now());
    public static final MenuItem MENU_ITEM_6 = new MenuItem(6, "chips", new BigDecimal("500.00"), LocalDate.now());

    public static MenuItem getNew() {
        return new MenuItem(null, "new Dish", new BigDecimal("1000.00"), LocalDate.now());
    }

    public static MenuItem getUpdated() {
        return new MenuItem(MENU_ITEM_3.id(), "updated " + MENU_ITEM_3.getName(), MENU_ITEM_3.getPrice().add(new BigDecimal("99.00")), MENU_ITEM_3.getDate());
    }
}
