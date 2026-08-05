package com.ems.algaworks.algashop.ordering.domain.valueobject.order;

import com.ems.algaworks.algashop.ordering.domain.valueobject.customer.Address;
import com.ems.algaworks.algashop.ordering.domain.valueobject.customer.Document;
import com.ems.algaworks.algashop.ordering.domain.valueobject.customer.FullName;
import com.ems.algaworks.algashop.ordering.domain.valueobject.customer.Phone;
import lombok.Builder;

import java.util.Objects;

@Builder
public record ShippingInfo(FullName fullName, Document document, Phone phone, Address address) {
    public ShippingInfo {
        Objects.requireNonNull(fullName);
        Objects.requireNonNull(document);
        Objects.requireNonNull(phone);
        Objects.requireNonNull(address);
    }
}
