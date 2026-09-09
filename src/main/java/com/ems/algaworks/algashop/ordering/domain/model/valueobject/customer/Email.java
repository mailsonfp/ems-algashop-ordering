package com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer;

import com.ems.algaworks.algashop.ordering.domain.model.validator.FieldValidations;

public record Email(String value) {

    public Email(String value) {
        FieldValidations.requiresValidEmail(value);
        this.value = value;
    }
}
