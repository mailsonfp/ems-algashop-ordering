package com.ems.algaworks.algashop.ordering.domain.model.exception;

import com.ems.algaworks.algashop.ordering.domain.model.entity.OrderStatus;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.order.OrderId;

public class OrderStatusCannotBeChangedException  extends DomainException {
    private static final long serialVersionUID = 1L;

    public OrderStatusCannotBeChangedException(OrderId id, OrderStatus status, OrderStatus newStatus) {
        super(String.format(ErrorMessages.ERROR_ORDER_STATUS_CANNOT_BE_CHANGED, id, status, newStatus));
    }
}
