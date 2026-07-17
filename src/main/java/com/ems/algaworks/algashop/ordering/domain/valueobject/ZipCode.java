package com.ems.algaworks.algashop.ordering.domain.valueobject;

import java.util.Objects;

public record ZipCode(String value) {
    public ZipCode {
        Objects.requireNonNull(value);
        if(value.isBlank()) {
            throw new IllegalArgumentException("Zip code cannot be null or blank.");
        }
        if(value.length() != 8) {
            throw new IllegalArgumentException("Zip code must be 8 characters long.");
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
