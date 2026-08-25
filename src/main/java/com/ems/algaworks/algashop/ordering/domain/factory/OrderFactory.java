package com.ems.algaworks.algashop.ordering.domain.factory;

import com.ems.algaworks.algashop.ordering.domain.entity.Order;
import com.ems.algaworks.algashop.ordering.domain.entity.PaymentMethod;
import com.ems.algaworks.algashop.ordering.domain.valueobject.customer.CustomerId;
import com.ems.algaworks.algashop.ordering.domain.valueobject.order.Billing;
import com.ems.algaworks.algashop.ordering.domain.valueobject.order.Quantity;
import com.ems.algaworks.algashop.ordering.domain.valueobject.order.Shipping;
import com.ems.algaworks.algashop.ordering.domain.valueobject.product.Product;

import java.util.Objects;

public class OrderFactory {
    private OrderFactory() {

    }

    public static Order filled(
            CustomerId customerId,
            Shipping shipping,
            Billing billing,
            PaymentMethod paymentMethod,
            Product product,
            Quantity productQuantity
    ) {
        Objects.requireNonNull(customerId);
        Objects.requireNonNull(shipping);
        Objects.requireNonNull(billing);
        Objects.requireNonNull(paymentMethod);
        Objects.requireNonNull(product);
        Objects.requireNonNull(productQuantity);

        Order order = Order.draft(customerId);

        order.changeBilling(billing);
        order.changeShipping(shipping);
        order.changePaymentMethod(paymentMethod);
        order.addItem(product, productQuantity);

        return order;
    }
}
