package com.ems.algaworks.algashop.ordering.domain.valueobject.customer;

import com.ems.algaworks.algashop.ordering.domain.validator.FieldValidations;

public record Email(String value) {

    public Email(String value) {
        FieldValidations.requiresValidEmail(value);
        this.value = value;
    }
}
