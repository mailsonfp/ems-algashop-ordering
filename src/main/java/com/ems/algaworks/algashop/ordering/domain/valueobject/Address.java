package com.ems.algaworks.algashop.ordering.domain.valueobject;

import com.ems.algaworks.algashop.ordering.domain.validator.FieldValidations;
import lombok.Builder;

import java.util.Objects;

public record Address(
        String street,
        String complement,
        String neighborhood,
        String number,
        String city,
        String state,
        ZipCode zipCode
) {

    @Builder(toBuilder = true)
    public Address {
        FieldValidations.requiresNonBlank(street, "Street cannot be null or blank.");
        FieldValidations.requiresNonBlank(neighborhood, "Neighborhood cannot be null or blank.");
        FieldValidations.requiresNonBlank(number, "Neighborhood cannot be null or blank.");
        FieldValidations.requiresNonBlank(city, "City cannot be null or blank.");
        FieldValidations.requiresNonBlank(state, "State cannot be null or blank.");
        Objects.requireNonNull(zipCode, "Zip code cannot be null.");
    }
}
