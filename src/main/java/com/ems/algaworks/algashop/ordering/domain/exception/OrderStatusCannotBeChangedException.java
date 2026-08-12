package com.ems.algaworks.algashop.ordering.domain.exception;

import com.ems.algaworks.algashop.ordering.domain.entity.OrderStatus;
import com.ems.algaworks.algashop.ordering.domain.valueobject.order.OrderId;

public class OrderStatusCannotBeChangedException  extends DomainException {
    private static final long serialVersionUID = 1L;

    public OrderStatusCannotBeChangedException(OrderId id, OrderStatus status, OrderStatus newStatus) {
        super(String.format(ErrorMessages.ERROR_ORDER_STATUS_CANNOT_BE_CHANGED, id, status, newStatus));
    }
}
