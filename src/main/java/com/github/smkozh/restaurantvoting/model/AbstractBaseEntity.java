package com.github.smkozh.restaurantvoting.model;

import lombok.Data;

@Data
public class AbstractBaseEntity {
    protected Integer id;

    protected AbstractBaseEntity() {
    }

    protected AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
