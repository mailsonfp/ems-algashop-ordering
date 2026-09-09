package com.ems.algaworks.algashop.ordering.domain.model.valueobject.product;

import com.ems.algaworks.algashop.ordering.domain.model.validator.FieldValidations;

public record ProductName(String value) {

    public ProductName {
        FieldValidations.requiresNonBlank(value);
    }

    @Override
    public String toString() {
        return value;
    }

}