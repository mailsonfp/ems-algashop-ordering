package com.ems.algaworks.algashop.ordering.domain.model.valueobject.order;

import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.Document;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.FullName;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.Phone;
import lombok.Builder;

import java.util.Objects;

@Builder
public record Recipient(FullName fullName, Document document, Phone phone) {
    public Recipient {
        Objects.requireNonNull(fullName);
        Objects.requireNonNull(document);
        Objects.requireNonNull(phone);
    }
}
