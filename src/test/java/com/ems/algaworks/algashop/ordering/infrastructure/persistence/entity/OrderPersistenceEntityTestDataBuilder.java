package com.ems.algaworks.algashop.ordering.infrastructure.persistence.entity;

import com.ems.algaworks.algashop.ordering.domain.model.utility.IdGenerator;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.embeddable.BillingEmbeddable;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.embeddable.RecipientEmbeddable;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.embeddable.ShippingEmbeddable;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity.OrderPersistenceEntityBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public class OrderPersistenceEntityTestDataBuilder {

    private OrderPersistenceEntityTestDataBuilder() {
    }

    public static OrderPersistenceEntityBuilder existingOrder() {
        return OrderPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .customer(CustomerPersistenceEntityTestDataBuilder.aCustomer().build())
                .totalItems(3)
                .totalAmount(new BigDecimal(1250))
                .status("DRAFT")
                .paymentMethod("CREDIT_CARD")
                .placedAt(OffsetDateTime.now())
                .items(Set.of(
                        existingItem().build(),
                        existingItemAlt().build()
                ));
    }

    public static OrderPersistenceEntityBuilder existingOrderFull() {
        OffsetDateTime now = OffsetDateTime.now();
        return OrderPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .customer(CustomerPersistenceEntityTestDataBuilder.aCustomer().build())
                .totalAmount(new BigDecimal("1250.00"))
                .totalItems(3)
                .status("PAID")
                .paymentMethod("CREDIT_CARD")
                .placedAt(now.minusDays(3))
                .paidAt(now.minusDays(2))
                .canceledAt(now.minusDays(1))
                .readyAt(now)
                .createdByUserId(UUID.randomUUID())
                .lastModifiedAt(now)
                .lastModifiedByUserId(UUID.randomUUID())
                .version(1L)
                .billing(BillingEmbeddable.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .document("225-09-1992")
                        .phone("123-111-9911")
                        .email("john.doe@gmail.com")
                        .address(address("Bourbon Street", "1234", "apt. 11",
                                "North Ville", "Montfort", "South Carolina", "04321002"))
                        .build())
                .shipping(ShippingEmbeddable.builder()
                        .cost(new BigDecimal("10.00"))
                        .expectedDate(LocalDate.now().plusWeeks(1))
                        .address(address("Sansome Street", "875", null,
                                "Sansome", "San Francisco", "California", "04321002"))
                        .recipient(RecipientEmbeddable.builder()
                                .firstName("Mary")
                                .lastName("Jones")
                                .document("552-11-4333")
                                .phone("54-454-1144")
                                .build())
                        .build())
                .items(Set.of(
                        existingItem().build(),
                        existingItemAlt().build()
                ));
    }

    private static AddressEmbeddable address(String street, String number, String complement,
                                             String neighborhood, String city, String state, String zipCode) {
        return AddressEmbeddable.builder()
                .street(street)
                .number(number)
                .complement(complement)
                .neighborhood(neighborhood)
                .city(city)
                .state(state)
                .zipCode(zipCode)
                .build();
    }

    public static OrderItemPersistenceEntity.OrderItemPersistenceEntityBuilder existingItem() {
        return OrderItemPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .price(new BigDecimal(500))
                .quantity(2)
                .totalAmount(new BigDecimal(1000))
                .productName("Notebook")
                .productId(IdGenerator.generateTimeBasedUUID());
    }

    public static OrderItemPersistenceEntity.OrderItemPersistenceEntityBuilder existingItemAlt() {
        return OrderItemPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .price(new BigDecimal(250))
                .quantity(1)
                .totalAmount(new BigDecimal(250))
                .productName("Mouse pad")
                .productId(IdGenerator.generateTimeBasedUUID());
    }
}
