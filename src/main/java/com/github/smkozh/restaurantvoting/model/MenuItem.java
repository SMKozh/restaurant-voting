package com.github.smkozh.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.github.smkozh.restaurantvoting.HasId;
import com.github.smkozh.restaurantvoting.View;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "menu_item", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "name", "menu_item_date"}, name = "menu_item_unique_restaurant_name_date_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = "restaurant")
public class MenuItem extends AbstractNamedEntity implements HasId {

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 10, max = 20000)
    private Double price;

    @Column(name = "menu_item_date", nullable = false)
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(groups = {View.Persist.class})
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference(value = "menu_item")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Restaurant restaurant;

    public MenuItem(Integer id, String name, Double price, LocalDate date) {
        super(id, name);
        this.price = price;
        this.date = date;
    }
}
