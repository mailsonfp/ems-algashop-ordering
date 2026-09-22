package com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer;

import java.util.Objects;

public record Document(String value){
    public Document(String value) {
        Objects.requireNonNull(value);
        if (value.isBlank()) {
            throw new IllegalArgumentException("Document ID cannot be blank.");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
