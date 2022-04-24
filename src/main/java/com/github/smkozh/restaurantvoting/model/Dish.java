package com.github.smkozh.restaurantvoting.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Dish extends AbstractNamedEntity {
    private int price;

    private Restaurant restaurant;

}
