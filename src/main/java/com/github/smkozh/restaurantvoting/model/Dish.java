package com.github.smkozh.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.github.smkozh.restaurantvoting.HasId;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dish", uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_id", "name"}, name = "dishes_unique_menu_name_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Dish extends AbstractNamedEntity implements HasId {

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 10, max = 20000)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference(value = "dish")
    private Menu menu;

    public Dish(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }
}
