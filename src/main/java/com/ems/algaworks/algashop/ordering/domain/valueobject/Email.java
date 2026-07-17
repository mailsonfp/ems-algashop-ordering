package com.ems.algaworks.algashop.ordering.domain.valueobject;

import com.ems.algaworks.algashop.ordering.domain.validator.FieldValidations;

import java.util.Objects;

public record Email(String value) {

    public Email(String value) {
        FieldValidations.requiresValidEmail(value);
        this.value = value;
    }
}
