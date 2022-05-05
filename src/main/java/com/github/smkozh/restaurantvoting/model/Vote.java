package com.github.smkozh.restaurantvoting.model;

import com.github.smkozh.restaurantvoting.HasId;
import com.github.smkozh.restaurantvoting.View;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "vote_date"}, name = "vote_unique_user_date_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(callSuper = true)
public class Vote extends AbstractBaseEntity implements HasId {

    @Column(name = "vote_date", nullable = false)
    @NotNull(groups = {View.Persist.class})
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(groups = {View.Persist.class})
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;
}
