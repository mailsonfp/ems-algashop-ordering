package com.ems.algaworks.algashop.ordering.domain.valueobject.order;

import com.ems.algaworks.algashop.ordering.domain.utility.IdGenerator;

import java.util.Objects;
import java.util.UUID;

public record OrderIItemId(UUID value) {

    public OrderIItemId() {
        this(IdGenerator.generateTimeBasedUUID());
    }

    public OrderIItemId(UUID value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
