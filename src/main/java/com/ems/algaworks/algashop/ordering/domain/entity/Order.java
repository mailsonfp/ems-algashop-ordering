package com.ems.algaworks.algashop.ordering.domain.entity;

import com.ems.algaworks.algashop.ordering.domain.valueobject.customer.CustomerId;
import com.ems.algaworks.algashop.ordering.domain.valueobject.order.Money;
import com.ems.algaworks.algashop.ordering.domain.valueobject.order.OrderId;
import com.ems.algaworks.algashop.ordering.domain.valueobject.order.Quantity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public class Order {
    private OrderId id;
    private CustomerId customerId;
    private Money totalAmount;
    private Money shippingCost;
    private Quantity totalItems;
    private OffsetDateTime placedAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime canceledAt;
    private OffsetDateTime readyAt;
    private LocalDate expectedDelivery;

    public Order(OrderId id, Money totalAmount, Money shippingCost, Quantity totalItems, OffsetDateTime placedAt, OffsetDateTime paidAt, OffsetDateTime canceledAt, OffsetDateTime readyAt, LocalDate expectedDelivery) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.shippingCost = shippingCost;
        this.totalItems = totalItems;
        this.placedAt = placedAt;
        this.paidAt = paidAt;
        this.canceledAt = canceledAt;
        this.readyAt = readyAt;
        this.expectedDelivery = expectedDelivery;
    }

}
