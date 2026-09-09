package com.ems.algaworks.algashop.ordering.domain.model.exception;

import com.ems.algaworks.algashop.ordering.domain.model.valueobject.order.OrderId;

public class OrderInvalidShippingDeliveryDateException extends DomainException {
    public OrderInvalidShippingDeliveryDateException(OrderId id) {
        super(String.format(ErrorMessages.ERROR_ORDER_DELIVERY_DATE_INVALID, id));

    }
}
