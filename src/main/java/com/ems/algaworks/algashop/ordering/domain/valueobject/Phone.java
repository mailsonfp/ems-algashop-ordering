package com.ems.algaworks.algashop.ordering.domain.valueobject;

import java.util.Objects;

public record Phone(String value) {
    public Phone(String value) {
        Objects.requireNonNull(value);
        if (value.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be blank.");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
