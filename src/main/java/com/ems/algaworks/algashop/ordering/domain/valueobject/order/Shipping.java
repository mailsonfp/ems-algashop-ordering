package com.ems.algaworks.algashop.ordering.domain.valueobject.order;

import com.ems.algaworks.algashop.ordering.domain.valueobject.customer.Address;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;

@Builder(toBuilder = true)
public record Shipping(
        Money cost,
        LocalDate expectedDate,
        Recipient recipient,
        Address address) {
    public Shipping {
        Objects.requireNonNull(cost);
        Objects.requireNonNull(expectedDate);
        Objects.requireNonNull(recipient);
        Objects.requireNonNull(address);
    }
}
