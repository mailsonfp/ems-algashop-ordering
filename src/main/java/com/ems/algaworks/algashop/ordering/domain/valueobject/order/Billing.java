package com.ems.algaworks.algashop.ordering.domain.valueobject.order;

import com.ems.algaworks.algashop.ordering.domain.valueobject.customer.Address;
import com.ems.algaworks.algashop.ordering.domain.valueobject.customer.Document;
import com.ems.algaworks.algashop.ordering.domain.valueobject.customer.Email;
import com.ems.algaworks.algashop.ordering.domain.valueobject.customer.FullName;
import com.ems.algaworks.algashop.ordering.domain.valueobject.customer.Phone;
import lombok.Builder;

import java.util.Objects;

@Builder
public record Billing(FullName fullName, Document document, Phone phone, Email email, Address address) {
    public Billing {
        Objects.requireNonNull(fullName);
        Objects.requireNonNull(document);
        Objects.requireNonNull(phone);
        Objects.requireNonNull(email);
        Objects.requireNonNull(address);
    }
}