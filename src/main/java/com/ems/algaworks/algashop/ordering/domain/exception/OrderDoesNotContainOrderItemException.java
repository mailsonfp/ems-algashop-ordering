package com.ems.algaworks.algashop.ordering.domain.exception;

import com.ems.algaworks.algashop.ordering.domain.valueobject.order.OrderId;
import com.ems.algaworks.algashop.ordering.domain.valueobject.order.OrderItemId;

public class OrderDoesNotContainOrderItemException extends DomainException {
    public OrderDoesNotContainOrderItemException(OrderId id, OrderItemId orderItemId) {
        super(String.format(ErrorMessages.ERROR_ORDER_NOT_CONTAIN_ORDER_ITEM, id, orderItemId));
    }
}
