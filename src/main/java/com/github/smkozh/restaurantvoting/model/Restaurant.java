package com.github.smkozh.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.smkozh.restaurantvoting.HasId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "restaurants_unique_name_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = "menuItems")
public class Restaurant extends AbstractNamedEntity implements HasId {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference(value = "menu_item")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<MenuItem> menuItems;

    public Restaurant(Integer id, String name, List<MenuItem> menuItems) {
        super(id, name);
        this.menuItems = menuItems;
    }
}
